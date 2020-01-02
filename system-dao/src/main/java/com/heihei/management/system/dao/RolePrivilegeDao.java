package com.heihei.management.system.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RolePrivilegeDao {

    @Delete("delete from role_privilege where role_id = #{roleId}")
    int deleteRolePrivilegeByRoleId(@Param("roleId") int roleId);
}
