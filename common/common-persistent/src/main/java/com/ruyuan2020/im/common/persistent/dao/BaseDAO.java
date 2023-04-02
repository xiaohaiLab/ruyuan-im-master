package com.ruyuan2020.im.common.persistent.dao;

import java.io.Serializable;
import java.util.Optional;

public interface BaseDAO<D> {

    Long save(D d);

    void remove(Serializable id);

    void update(D d);

    Optional<D> getById(Serializable id);
}
