package com.heihei.management.system.dao;

import com.heihei.management.system.entity.PrivilegeDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PrivilegeDao {

    @Select("select * from privilege")
    List<PrivilegeDO> listPrivilege();

    @Select("SELECT * FROM privilege WHERE id IN (SELECT privilege_id id FROM role_privilege WHERE role_id = #{roleId})")
    List<PrivilegeDO> listPrivilegeByRoleId(@Param("roleId") int roleId);

    @Select("select * from privilege where name like concat('%',#{selectTip},'%')")
    List<PrivilegeDO> selectPostByTip(@Param("selectTip") String selectTip);

    @Select("select * from privilege where name = #{name}")
    PrivilegeDO getPrvgByPrvgName(@Param("name") String name);

    @Insert("INSERT INTO `privilege`(`name`,`url`) VALUES(#{name},#{url})")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false,resultType = int.class,statement="select last_insert_id()")
    int addPost(PrivilegeDO privilegeDO);

    @Delete("delete from role_privilege where privilege_id = #{prvgId}")
    int deletePrvgRoleByPrvgId(@Param("prvgId") int prvgId);

    @Delete("delete from privilege where id = #{prvgId}")
    int deletePrvgByPrvgId(@Param("prvgId") int prvgId);

    @Select("select * from privilege where id = #{prvgId}")
    PrivilegeDO selectPrvgByPrvgId(@Param("prvgId") int prvgId);

    @Update("update privilege set name = #{name},`url` = #{url} where id = #{id}")
    int updateDept(PrivilegeDO privilegeDO);

    @Delete("delete from role_privilege where role_id = #{roleId}")
    int deletePrvgRoleByRoleId(@Param("roleId") int roleId);
}
