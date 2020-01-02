package com.heihei.management.system.dao;

import com.heihei.management.system.entity.database.UserRoleDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * UserRoleDao
 * @Description   对应数据库的user_role表 dao层
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Mapper
public interface UserRoleDao {
    @Select("select * from user_role where user_id = #{userId}")
    public List<UserRoleDO> selectUserRolesByUserId(@Param("userId") int userId);

    @Insert("insert into user_role(user_id,role_id) values (#{userId},#{roleId})")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false,resultType = int.class,statement="select last_insert_id()")
    public int addUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    @Delete("delete from user_role where user_id = #{userId}")
    int deleteUserRole(@Param("userId") int userId);
    @Update("UPDATE user_role SET role_id = #{newRoleId} WHERE user_id = #{userId} AND role_id = #{oldRoleId};")
    int updateUserRole(@Param("oldRoleId") int oldRoleId, @Param("newRoleId") int newRoleId,@Param("userId") int userId);

    @Delete("delete from user_role where role_Id = #{roleId}")
    int deleteUserRoleByRoleId(@Param("roleId") int roleId);
}
