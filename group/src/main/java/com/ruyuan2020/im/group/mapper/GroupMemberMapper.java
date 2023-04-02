package com.ruyuan2020.im.group.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruyuan2020.im.group.domain.GroupMemberDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author case
 */
@Mapper
public interface GroupMemberMapper extends BaseMapper<GroupMemberDO> {
    List<Long> list(IPage<Long> page, @Param("memberId") Long memberId);
}
