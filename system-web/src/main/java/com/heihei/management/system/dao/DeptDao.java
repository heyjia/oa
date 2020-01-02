package com.heihei.management.system.dao;

import com.heihei.management.system.entity.DepartmentDO;
import com.heihei.management.system.entity.vo.DeptVO;
import com.heihei.management.system.entity.vo.TreeVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptDao {

    @Insert("insert into department (`name`,address,`describe`,crt_time,updt_time,pid) values(#{name},#{address},#{describe},#{crtTime},#{updtTime},#{pid})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=int.class, before=false, statement="select last_insert_id()")
    int addDept(DepartmentDO department);

    @Delete("DELETE FROM post_dept WHERE dept_id = #{deptId}")
    int deleteDeptPost(@Param("deptId") int deptId);

    @Delete("DELETE FROM department WHERE id = #{deptId}")
    int deleteDeptByDeptId(int deptId);

    @Select("select * from department where id = #{deptId}")
    DepartmentDO selectDeptByDeptId(@Param("deptId") int deptId);

    @Update("UPDATE department SET `name` = #{name},address = #{address},`describe` = #{describe},pid = #{pid},updt_time = #{updtTime} WHERE id = #{id}")
    int updateDept(DepartmentDO department);

    @Select("SELECT * from department")
    List<DepartmentDO> listDept();

    @Select("SELECT d.*,dd.name p_name FROM department d LEFT JOIN  department dd ON d.pid = dd.id")
    List<DeptVO> listDeptWithPDept();

    @Select("select * from department where name like concat('%',#{selectTip},'%')")
    List<DepartmentDO> listDeptByTip(String selectTip);

    @Select("SELECT id,pid,`name` FROM department")
    List<TreeVO> listDeptAsTree();

    @Select("SELECT d.*,dd.name p_name FROM department d LEFT JOIN  department dd ON d.pid = dd.id where d.name like concat('%',#{selectTip},'%')")
    List<DeptVO> selectDeptByTip(String selectTip);

    @Insert("INSERT INTO post_dept (dept_id,post_id) VALUES(#{deptId},${postId})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=int.class, before=false, statement="select last_insert_id()")
    int addPostToDept(@Param("postId") int postId, @Param("deptId")int deptId);

    @Select("SELECT d.*,dd.name p_name FROM department d LEFT JOIN  department dd ON d.pid = dd.id where d.id = #{deptId}")
    DeptVO getDeptByDeptId(@Param("deptId")int deptId);

    @Select("select * from department where name = #{deptName}")
    DepartmentDO getDeptByDeptName(@Param("deptName") String deptName);
}
