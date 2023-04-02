package com.ruyuan2020.im.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruyuan2020.im.account.util.NicknameUtils;
import com.ruyuan2020.im.common.core.domain.BaseDomain;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.common.core.exception.NotFoundException;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.account.dao.UserAccountDAO;
import com.ruyuan2020.im.account.domain.RegisterDTO;
import com.ruyuan2020.im.account.domain.UserAccountDO;
import com.ruyuan2020.im.account.domain.UserAccountDTO;
import com.ruyuan2020.im.account.domain.UserAccountQuery;
import com.ruyuan2020.im.account.service.UserAccountService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final String USER_KEY = "UserAccount::";

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BasePage<UserAccountDTO> listByPage(UserAccountQuery query) {
        return BeanCopierUtils.convert(userAccountDAO.listByPage(query), (it) -> {
            UserAccountDTO userAccountDTO = it.clone(UserAccountDTO.class);
            userAccountDTO.setPassword(null);
            return userAccountDTO;
        });
    }

    @Override
    @Transactional
    public void update(UserAccountDTO userAccountDTO) {
        redisTemplate.delete(USER_KEY + userAccountDTO.getId());
        if (StringUtils.isNotBlank(userAccountDTO.getPassword())) {
            checkPassword(userAccountDTO.getPassword(), userAccountDTO.getUsername());
            userAccountDTO.setPassword(passwordEncoder.encode(userAccountDTO.getPassword()));
        }
        UserAccountDO userAccountDO = userAccountDTO.clone(UserAccountDO.class);
        userAccountDAO.update(userAccountDO);
    }

    @Override
    @Transactional
    public void modifyPassword(Long id, String password) {
        UserAccountDO userAccountDO = userAccountDAO.getById(id).orElseThrow(NotFoundException::new);
        checkPassword(userAccountDO.getUsername(), userAccountDO.getPassword());
        userAccountDO.setPassword(passwordEncoder.encode(userAccountDO.getPassword()));
        userAccountDAO.update(userAccountDO);
    }

    @Override
    @Transactional
    public UserAccountDO register(RegisterDTO registerDTO) {
        return register(registerDTO, StringUtils.isNotBlank(registerDTO.getPassword()));
    }

    @Override
    public UserAccountDTO getById(Long id) {
        UserAccountDO userAccountDO = userAccountDAO.getById(id).orElseThrow(NotFoundException::new);
        UserAccountDTO userAccountDTO = userAccountDO.clone(UserAccountDTO.class);
        userAccountDTO.setPassword(null);
        return userAccountDTO;
    }

    @Override
    @Transactional
    public void modifyNickname(Long id, String nickname) {
        redisTemplate.delete(USER_KEY + id);
        UserAccountDO userAccountDO = userAccountDAO.getById(id).orElseThrow(NotFoundException::new);
        userAccountDO.setNickname(nickname);
        userAccountDAO.update(userAccountDO);
    }

    @Override
    public List<UserAccountDTO> getByIds(List<Long> ids) {
        List<Long> tempIds = new ArrayList<>(ids);
        List<String> list = redisTemplate.opsForValue().multiGet(tempIds.stream().distinct().map(it -> USER_KEY + it).collect(Collectors.toList()));
        List<UserAccountDTO> userDTOS = new ArrayList<>(tempIds.size());
        if (list != null) {
            userDTOS.addAll(list.stream().filter(Objects::nonNull).map(it -> JSON.parseObject(it, UserAccountDTO.class)).collect(toList()));
        }
        if (tempIds.size() != userDTOS.size()) {
            if (!userDTOS.isEmpty()) {
                List<Long> idsInRedis = userDTOS.stream().map(UserAccountDTO::getId).collect(toList());
                tempIds = tempIds.stream().filter(it -> !idsInRedis.contains(it)).collect(toList());
            }
            // idsInDB
            if (CollectionUtils.isNotEmpty(tempIds)) {
                List<UserAccountDTO> userDTOSInDB = BeanCopierUtils.convert(userAccountDAO.getByIds(tempIds), UserAccountDTO.class);
                userDTOS.addAll(userDTOSInDB);
                redisTemplate.opsForValue().multiSet(userDTOSInDB.stream().collect(toMap(it -> USER_KEY + it.getId(), BaseDomain::toJsonStr)));
            }
        }
        return userDTOS;
    }

    private UserAccountDO register(RegisterDTO registerDTO, boolean requirePassword) {
//        long count = userAccountDAO.countByNickname(registerDTO.getNickname());
//        if (count > 0) {
//            throw new BusinessException("昵称已被使用");
//        }
        long count = userAccountDAO.countByMobile(registerDTO.getMobile());
        if (count > 0) {
            throw new BusinessException("手机号已被使用");
        }

        UserAccountDO userAccountDO = new UserAccountDO();
        if (requirePassword) {
            checkPassword(registerDTO.getPassword(), registerDTO.getMobile());
            userAccountDO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        }
        userAccountDO.setUsername(registerDTO.getMobile());
        userAccountDO.setMobile(registerDTO.getMobile());
        if (StringUtils.isBlank(registerDTO.getNickname())) {
            userAccountDO.setNickname(NicknameUtils.generateNickname());
        } else {
            userAccountDO.setNickname(registerDTO.getNickname());
        }
        userAccountDO.setStatus("00");

        userAccountDAO.save(userAccountDO);
        return userAccountDO;
    }

    private void checkUsername(String username) {
        long count = userAccountDAO.countByUsername(username);
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }
    }

    public void checkPassword(String password, String username) {
        //判断密码是否包含数字：包含返回1，不包含返回0
        int i = password.matches(".*\\d+.*") ? 1 : 0;
        //判断密码是否包含字母：包含返回1，不包含返回0
        int j = password.matches(".*[a-zA-Z]+.*") ? 1 : 0;
        //判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
        int k = password.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;
        //判断密码中是否包含用户名
        boolean contains = password.contains(username);
        if (i + j + k < 2 || contains) {
            throw new BusinessException("密码不符合规则");
        }
    }
}
