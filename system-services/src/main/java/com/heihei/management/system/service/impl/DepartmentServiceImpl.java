package com.heihei.management.system.service.impl;



import com.heihei.management.system.dao.DeptDao;
import com.heihei.management.system.entity.database.DepartmentDO;
import com.heihei.management.system.entity.vo.DeptVO;
import com.heihei.management.system.entity.vo.TreeVO;
import com.heihei.management.system.service.DeptService;
import com.heihei.management.system.service.PostService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @ClassName DepartmentServiceImpl
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/21 19:53
 **/
@Service
public class DepartmentServiceImpl implements DeptService {
    Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Autowired
    DeptDao deptDao;
    @Autowired
    PostService postService;
    @Override
    public int addDept(DepartmentDO department) {
        int id = deptDao.addDept(department);
        return id;
    }

    @Override
    public int deleteDept(int deptId) {
        //先删除在用户在此部门的记录
        postService.deleteUserPostByDeptId(deptId);
        //在删除岗位在此部门的记录
        deptDao.deleteDeptPost(deptId);
        //在删除部门信息
       int item =  deptDao.deleteDeptByDeptId(deptId);
       logger.info("deleteDept: item :" + item);
       return item;
    }

    @Override
    public DepartmentDO selectDeptByDeptId(int deptId) {
        DepartmentDO dept = deptDao.selectDeptByDeptId(deptId);
        return dept;
    }

    @Override
    public int updateDept(DepartmentDO department) {
        int item = deptDao.updateDept(department);
        return item;
    }

    @Override
    public List<DepartmentDO> listDept() {
        List<DepartmentDO> listDept = deptDao.listDept();
        for (int i = 0;i < listDept.size();i++) {
            logger.info(listDept.get(i).toString());
        }
        return listDept;
    }

    @Override
    public List<DepartmentDO> listDeptByTip(String selectTip) {
        List<DepartmentDO> listDept = deptDao.listDeptByTip(selectTip);
        for (int i = 0;i < listDept.size();i++) {
            logger.info(listDept.get(i).toString());
        }
        return listDept;
    }

    @Override
    public List<TreeVO> listDeptAsTree() {
        List<TreeVO> trees = deptDao.listDeptAsTree();
        return trees;
    }

    @Override
    public List<DeptVO> listDeptWithPDept() {
        List<DeptVO> depts = deptDao.listDeptWithPDept();
        return depts;
    }

    @Override
    public List<DeptVO> selectDeptByTip(String selectTip) {
        List<DeptVO> depts = deptDao.selectDeptByTip(selectTip);
        return depts;
    }

    @Override
    public int addPostToDept(int postId, int deptId) {
        int item = deptDao.addPostToDept(postId,deptId);
        return item;
    }

    @Override
    public DeptVO getDeptByDeptId(int deptId) {
        DeptVO dept = deptDao.getDeptByDeptId(deptId);
        return dept;
    }

    @Override
    public DepartmentDO getDeptByDeptName(String deptName) {
        DepartmentDO department = deptDao.getDeptByDeptName(deptName);
        return department;
    }
}
