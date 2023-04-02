package com.ruyuan2020.im.group.dao;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.persistent.dao.BaseDAO;
import com.ruyuan2020.im.group.domain.GroupDO;
import com.ruyuan2020.im.group.domain.GroupQuery;

/**
 * @author case
 */
public interface GroupDAO extends BaseDAO<GroupDO> {
    BasePage<GroupDO> listByPage(GroupQuery query);
}
