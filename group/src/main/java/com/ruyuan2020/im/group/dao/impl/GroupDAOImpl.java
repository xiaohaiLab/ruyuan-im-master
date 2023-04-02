package com.ruyuan2020.im.group.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.group.dao.GroupDAO;
import com.ruyuan2020.im.group.domain.GroupDO;
import com.ruyuan2020.im.group.domain.GroupQuery;
import com.ruyuan2020.im.group.mapper.GroupMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @author case
 */
@Repository
public class GroupDAOImpl extends MybatisPlusDAOImpl<GroupMapper, GroupDO> implements GroupDAO {

    @Override
    public BasePage<GroupDO> listByPage(GroupQuery query) {
        LambdaQueryWrapper<GroupDO> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())){
            queryWrapper.likeLeft(GroupDO::getName, query.getName());
        }
        return listPage(query, queryWrapper);
    }
}
