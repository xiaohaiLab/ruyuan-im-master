package com.ruyuan2020.im.common.web.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruyuan2020.im.common.persistent.dao.BaseDAO;
import com.ruyuan2020.im.common.persistent.domain.BaseDO;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.persistent.domain.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Optional;

public class MybatisPlusDAOImpl<M extends BaseMapper<D>, D extends BaseDO> implements BaseDAO<D> {

//    private Class<D> doClass;

    @Autowired
    protected M mapper;

//    @SuppressWarnings("unchecked")
//    public CommonDAOImpl() {
//        this.doClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
//    }

    @Override
    public Long save(D d) {
        d.preSave();
        this.mapper.insert(d);
        return d.getId();
    }

    @Override
    public void remove(Serializable id) {
        this.mapper.deleteById(id);
    }

    @Override
    public void update(D d) {
        d.preUpdate();
        this.mapper.updateById(d);
    }

    @Override
    public Optional<D> getById(Serializable id) {
        return Optional.ofNullable(this.mapper.selectById(id));
    }

    public BasePage<D> listPage(BaseQuery query, Wrapper<D> wrapper) {
        IPage<D> page = new Page<>(query.getCurrent(), query.getPageSize());
        this.mapper.selectPage(page, wrapper);
        return new BasePage<>(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent());
    }
}
