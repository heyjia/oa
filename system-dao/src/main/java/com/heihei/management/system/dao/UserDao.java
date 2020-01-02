package com.heihei.management.system.dao;

import com.heihei.management.system.entity.database.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * UserDao
 * @Description    对应数据库的user表 dao层
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Mapper
public interface UserDao {
    // 根据用户名查询用户
    @Select("select * from user where name = #{name}")
    UserDO selectUserByUserName(@Param("name") String userName);
    //查询所有的用户信息
    @Select("select * from user")
    List<UserDO> listUser();
    //根据用户id查找用户
    @Select("select * from user where id = #{userId}")
    UserDO getUserByUserId(@Param("userId")int userId);

    @Update("update user u set u.telephone = #{telephone},u.sex = #{sex},u.email = #{email},u.updt_time = #{updtTime},u.sex = #{sex} where u.id = #{id}")
    int updateUser(UserDO userDO);
    @Update("update user u set u.password = #{newPassword} where u.id = #{userId}")
    public int updatePassword(@Param("newPassword") String newPassword, @Param("userId") int userId);

    @Insert("insert into user(name,password,salt,telephone,email,sex,crt_time,updt_time) values (#{name},#{password},#{salt},#{telephone},#{email},#{sex},#{crtTime},#{updtTime})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=int.class, before=false, statement="select last_insert_id()")
    public int addUser(UserDO userDO);

    @Delete("delete from user where id = #{userId}")
    int deleteUser(@Param("userId") int userId);

    @Select("select * from user where name like concat('%',#{selectTip},'%')")
     List<UserDO> selectUserBySelectTip(@Param("selectTip") String selectTip);

    @Select("select count(*) from user")
    int countUser();
}
