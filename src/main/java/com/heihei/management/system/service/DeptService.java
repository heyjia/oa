package com.heihei.management.system.service;

import com.heihei.management.system.entity.DepartmentDO;
import com.heihei.management.system.entity.vo.DeptVO;
import com.heihei.management.system.entity.vo.TreeVO;

import java.util.List;

public interface DeptService {
    //添加部门
    int addDept(DepartmentDO department);
    //根据部门id删除部门
    int deleteDept(int deptId);
    //根据部门id查询部门
    DepartmentDO selectDeptByDeptId(int deptId);
    //修改部门的信息
    int updateDept(DepartmentDO department);
    //查询所有的部门
    List<DepartmentDO> listDept();
    //根据部门名模糊查询部门
    List<DepartmentDO> listDeptByTip(String selectTip);
    //查询部门的id pid 和 部门名称 构造出树
    List<TreeVO> listDeptAsTree();
    //查询所有部门和父部门的name
    List<DeptVO> listDeptWithPDept();
    //根据部门名模糊查询部门
    List<DeptVO> selectDeptByTip(String selectTip);
    //增加部门的岗位
    int addPostToDept(int postId,int deptId);
    //获取部门根据部门的id
    DeptVO getDeptByDeptId(int deptId);
    //根据部门名称查询部门
    DepartmentDO getDeptByDeptName(String deptName);
}
