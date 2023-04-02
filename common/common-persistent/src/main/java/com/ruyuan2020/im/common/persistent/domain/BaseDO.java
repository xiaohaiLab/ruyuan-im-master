package com.ruyuan2020.im.common.persistent.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruyuan2020.im.common.core.domain.BaseDomain;
import com.ruyuan2020.im.common.core.util.DateTimeUtils;

import java.time.LocalDateTime;

/**
 * DO基类
 */
public abstract class BaseDO extends BaseDomain {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    public BaseDO() {
    }

    public BaseDO(Long id, LocalDateTime gmtCreate, LocalDateTime gmtModified) {
        this.id = id;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    /**
     * 保存前的操作
     *
     * @return 当前DO对象
     */
    public BaseDO preSave() {
        LocalDateTime currentDateTime = DateTimeUtils.currentDateTime();
        this.gmtCreate = currentDateTime;
        this.gmtModified = currentDateTime;
        return this;
    }

    /**
     * 更新前的操作
     *
     * @return 当前DO对象
     */
    public BaseDO preUpdate() {
        this.gmtModified = DateTimeUtils.currentDateTime();
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
