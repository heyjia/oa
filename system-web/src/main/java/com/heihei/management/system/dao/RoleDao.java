package com.heihei.management.system.dao;

import com.heihei.management.system.entity.RoleDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * RoleDao
 * @Description  对应数据库的role表 dao层
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Mapper
public interface RoleDao {
    @Select("select * from role where id = #{roleId}")
    RoleDO selectRoleByRoleId(@Param("roleId") int roleId);

    @Select("SELECT r.id id,r.name name FROM role r INNER JOIN user_role ur ON r.id = ur.role_id WHERE user_id = #{userId}")
    List<RoleDO> selectRoleByUserId(@Param("userId") int userId);

    @Select("select * from role")
    List<RoleDO> selectRoles();

    @Select("select * from role where id = #{roleId}")
    RoleDO getRoleById(@Param("roleId") int roleId);
    @Select("select * from role where name like concat('%',#{selectTip},'%')")
    List<RoleDO> selectRolesByTip(String selectTip);

    @Delete("delete from role where id = #{roleId}")
    int deleteRoleByRoleId(@Param("roleId") int roleId);

    @Select("select * from role where name like concat('%',#{selectTip},'%')")
    List<RoleDO> selectRoleByTip(String selectTip);

    @Select("select * from role where name = #{roleName}")
    RoleDO getRoleByName(@Param("roleName") String roleName);

    @Insert("INSERT INTO role (name) VALUES(#{name})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = int.class, before = false, statement = "select last_insert_id()")
    int addRole(RoleDO role);

    @Insert("INSERT INTO role_privilege (role_id,privilege_id) VALUES(#{roleId},#{prvgId})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = int.class, before = false, statement = "select last_insert_id()")
    int addPrvgToRole(@Param("roleId") int roleId, @Param("prvgId") int prvgId);

    @Update("update role set name = #{name} where id = #{id}")
    int updateRole(RoleDO role);
}
