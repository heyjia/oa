package com.heihei.management.system.dao;


import com.heihei.management.system.entity.PositionDO;
import com.heihei.management.system.entity.UserPostDO;
import com.heihei.management.system.entity.form.UpdateUserForm;
import com.heihei.management.system.entity.vo.DeptPosiVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName UserPostDao
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 11:28
 **/
@Mapper
public interface PostDao {
    @Select("select * from user_post where user_id = #{userId}")
    List<UserPostDO> listUserPost(@Param("userId")int userId);
    @Select("SELECT pudp.dept_id dept_id,pudp.dept_name dept_name,pudp.salary,p.id post_id,p.name post_name FROM `position` p INNER JOIN (SELECT d.id dept_id,d.name dept_name,udp.post_id,udp.salary \n" +
            "   FROM  department d\n" +
            "     INNER JOIN (\n" +
            "        SELECT dept_id,post_id , salary FROM user_post WHERE user_id = #{userId})\n" +
            "         AS udp ON d.id = udp.dept_id) AS pudp ON p.id = pudp.post_id")
    List<DeptPosiVO> listUserDeptPostByUserId(@Param("userId") int userId);
    @Delete("delete from user_post where user_id = #{userId}")
    int deleteUserPost(@Param("userId") int userId);
    @Insert("insert into user_post(user_id,post_id,dept_id,salary) values (#{userId},#{postId},#{deptId},#{salary})")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false,resultType = int.class,statement="select last_insert_id()")
    int addPostDept(@Param("userId") int id, @Param("postId") int postId, @Param("deptId") int deptId,@Param("salary") float salary);
    @Update("UPDATE user_post SET post_id = #{newPostId},dept_id = #{newDeptId},salary = #{newSalary} WHERE post_id = #{oldPostId} AND dept_id = #{oldDeptId} AND user_id = #{id}")
    int updatePost(UpdateUserForm updateUserForm);

    @Insert("INSERT INTO `position`(`name`,`describe`,crt_time,updt_time) VALUES(#{name},#{describe},#{crtTime},#{updtTime})")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false,resultType = int.class,statement="select last_insert_id()")
    int addPost(PositionDO positionDO);

    @Delete("DELETE FROM user_post WHERE dept_id = #{deptId}")
    int deleteUserPostByDeptId(@Param("deptId") int deptId);

    @Select("SELECT * FROM `position` WHERE id IN (SELECT post_id FROM post_dept WHERE dept_id = #{deptId})")
    List<PositionDO> listPostByDeptId(@Param("deptId") int deptId);

    @Select("select * from position")
    List<PositionDO> listPost();

    @Delete("delete from post_dept where dept_id = #{deptId}")
    int deletePostByDeptId(@Param("deptId") int deptId);

    @Select("select * from position where name like concat('%',#{selectTip},'%')")
    List<PositionDO> selectPostByTip(String selectTip);

    @Select("select * from position where name = #{postName}")
    PositionDO getPostByPostName(@Param("postName") String postName);

    @Delete("delete from post_dept where post_id = #{postId}")
    int deleteDeptPostByPostId(@Param("postId") int postId);

    @Delete("delete from user_post where post_id = #{postId}")
    int deleteUserPostByPostId(@Param("postId")int postId);

    @Delete("delete from position where id = #{postId}")
    int deletePostByPostId(@Param("postId")int postId);

    @Select("select * from position where id = #{postId}")
    PositionDO getPostByPostId(@Param("postId") int postId);

    @Update("update position set name = #{name},`describe` = #{describe},crt_time = #{crtTime} where id = #{id}")
    int updatePostByForm(PositionDO toUpdatePost);
}
