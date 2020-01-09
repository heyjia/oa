package com.heihei.management.system.controller;

import com.heihei.management.system.entity.DepartmentDO;
import com.heihei.management.system.entity.PositionDO;
import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.entity.form.AddDeptForm;
import com.heihei.management.system.entity.vo.DeptAndPostVO;
import com.heihei.management.system.entity.vo.DeptVO;
import com.heihei.management.system.entity.vo.TreeVO;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.DeptService;
import com.heihei.management.system.service.PostService;
import com.heihei.management.system.util.ExcelUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName DeptController
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/22 21:52
 **/
@Controller
@RequestMapping(value = "/dept")
public class DeptController {
    Logger logger = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    DeptService deptService;
    @Autowired
    PostService postService;
    @RequestMapping(value = "/getTree")
    @ResponseBody
    public List<TreeVO> getTree(Model model,UserDO user){
        List<TreeVO> trees = deptService.listDeptAsTree();
        model.addAttribute("tree",trees);
        return trees;
    }
    //查询所有的部门,并且跳转到所有部门的列表
    @RequestMapping(value = "/toDeptTree")
    public String toDeptTree(Model model,UserDO user){
        logger.info("查询所有部门");
        //查询所有的部门
        List<DeptVO> depts = deptService.listDeptWithPDept();
        for (int i = 0;i < depts.size();i++) {
            logger.info(depts.get(i).toString());
        }
        List<PositionDO> posts = postService.listPost();
        model.addAttribute("depts",depts);
        model.addAttribute("posts",posts);
        model.addAttribute("u",user);
        return "demo/deptManagement";
    }
    //模糊查询部门
    @RequestMapping(value = "/selectDeptByTip/{selectTip}")
    public String selectDeptByTip(Model model , @PathVariable("selectTip") String selectTip,UserDO user) {
        logger.info("模糊查询树" + selectTip);
        List<DeptVO> depts = deptService.selectDeptByTip(selectTip);
        for (int i = 0;i < depts.size();i++) {
            logger.info(depts.get(i).toString());
        }
        List<PositionDO> posts = postService.listPost();
        model.addAttribute("depts",depts);
        model.addAttribute("posts",posts);
        model.addAttribute("u",user);
        return "demo/deptManagement";
    }

    //删除部门的请求
    @RequestMapping(value="/delete/{deptId}")
    @ResponseBody
    public Result<Boolean> deleteDept(@PathVariable("deptId") int deptId,UserDO user){
        logger.info("删除部门deleteDept");
        int item = deptService.deleteDept(deptId);
        logger.info(item+"");
        if (item <= 0) {
            return Result.error(CodeMsg.DELETEUSER_ERROR);
        }else{
            return Result.success(true);
        }
    }

    //添加部门请求
    @RequestMapping(value = "/addDept")
    @ResponseBody
    public Result<Boolean> addDept(AddDeptForm addDeptForm,UserDO user){
        logger.info("添加部门请求addDept");
        logger.info("postIds:"+addDeptForm.toString());
        logger.info("postId:" + addDeptForm.getPostIds());
        DepartmentDO dept = null;
        dept = deptService.getDeptByDeptName(addDeptForm.getDeptName());
        if (dept != null) {
            return  Result.error(CodeMsg.DEPT_EXISTED);
        }
        dept = new DepartmentDO();
        dept.setName(addDeptForm.getDeptName());
        dept.setDescribe(addDeptForm.getDescribe());
        dept.setAddress(addDeptForm.getAddress());
        dept.setPid(addDeptForm.getSelectDept());
        dept.setUpdtTime(new Date());
        dept.setCrtTime(new Date());
        int item = deptService.addDept(dept);
        if (item <= 0) {
            return Result.error(CodeMsg.ADDUSER_ERROR);
        }
        logger.info("item:" + item);
        logger.info("deptId:" + dept.getId());
        String[] strs = addDeptForm.getPostIds().split(",");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int postId = Integer.parseInt(strs[i]);
            deptService.addPostToDept(postId,dept.getId());
        }
        logger.info(strs.length+"");
        return Result.success(true);
    }

    //获取单个部门信息的请求
    @RequestMapping(value = "/selectDeptByDeptId/{deptId}")
    @ResponseBody
    public DeptAndPostVO selectDeptByDeptId(@PathVariable("deptId") int deptId,UserDO user){
        DeptAndPostVO deptAndPst = new DeptAndPostVO();
        deptAndPst.setDept(deptService.selectDeptByDeptId(deptId));
        deptAndPst.setPosts(postService.listPostByDeptId(deptId));
        return deptAndPst;
    }

    //树点击获取单个部门信息的请求
    @RequestMapping(value = "/getDeptByDeptId/{deptId}")
    public String getDeptByDeptId(@PathVariable("deptId") int deptId,Model model,UserDO u){
        logger.info("获取单个部门信息的请求selectDeptByDeptId");
        DeptVO dept = deptService.getDeptByDeptId(deptId);
        logger.info("dept:" + dept.toString());
        model.addAttribute("depts",dept);
        List<PositionDO> posts = postService.listPost();
        model.addAttribute("posts",posts);
        model.addAttribute("u",u);
        return "demo/deptManagement";
    }

    //编辑部门请求
    @RequestMapping(value = "/updateDept")
    @ResponseBody
    public Result<Boolean> updateDept(AddDeptForm addDeptForm,UserDO user){
        logger.info("编辑部门请求updateDept");
        logger.info("postIds:"+addDeptForm.toString());
        DepartmentDO dept = new DepartmentDO();
        dept.setId(addDeptForm.getId());
        dept.setName(addDeptForm.getDeptName());
        dept.setDescribe(addDeptForm.getDescribe());
        dept.setAddress(addDeptForm.getAddress());
        dept.setPid(addDeptForm.getSelectDept());
        dept.setUpdtTime(new Date());
        dept.setCrtTime(new Date());
        int item = deptService.updateDept(dept);
        if (item <= 0) {
            return Result.error(CodeMsg.ADDUSER_ERROR);
        }
        postService.deletePostByDeptId(dept.getId());
        logger.info("item:" + item);
        logger.info("deptId:" + dept.getId());
        String[] strs = addDeptForm.getPostIds().split(",");
        logger.info(strs.length+"");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int postId = Integer.parseInt(strs[i]);
            deptService.addPostToDept(postId,dept.getId());
        }
        return Result.success(true);
    }

    //batchBatch批量删除部门
    @RequestMapping(value="/batchBatch")
    @ResponseBody
    public Result<Boolean> batchBatch(String ids,UserDO user) {
        logger.info("批量删除部门，为"+ids);
        String[] strs = ids.split(",");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int id = Integer.parseInt(strs[i]);
            deptService.deleteDept(id);
        }
        return Result.success(true);
    }

    @RequestMapping(value = "/download")
    public void excelDownload(HttpServletResponse response,UserDO user) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        head.add("部门名");
        head.add("上级部门");
        head.add("地址");
        head.add("描述");
        head.add("创建时间");
        head.add("更新时间");
        data.add(head);
        List<DeptVO> depts = deptService.listDeptWithPDept();
        for (DeptVO deptVO : depts) {
            List<String> row = new ArrayList<>();
            row.add(deptVO.getName());
            row.add(deptVO.getPName());
            row.add(deptVO.getAddress());
            row.add(deptVO.getDescribe());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            row.add(formatter.format(deptVO.getCrtTime()));
            row.add(formatter.format(deptVO.getUpdtTime()));
            data.add(row);
        }
        String sheetName = "部门列表";
        String fileName = "post.xls";
        ExcelUtil.exportExcel(response,data,sheetName,fileName,20);
    }
}
