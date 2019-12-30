package com.heihei.management.system.controller;

import com.heihei.management.system.entity.DepartmentDO;
import com.heihei.management.system.entity.PositionDO;
import com.heihei.management.system.entity.RoleDO;
import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.entity.form.AddUserForm;
import com.heihei.management.system.entity.form.UpdateUserForm;
import com.heihei.management.system.entity.util.ExcelData;
import com.heihei.management.system.entity.vo.UserListVO;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.PostService;
import com.heihei.management.system.service.RoleService;
import com.heihei.management.system.service.UserService;
import com.heihei.management.system.service.impl.DepartmentServiceImpl;
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
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UserController
 * @Description  用户的controller，主要是处理show页面的相关请求
 * @author CHENZEJIA
 * @date 2019/12/17
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    UserService userService;
    @Autowired
    DepartmentServiceImpl departmentService;
    @Autowired
    RoleService roleService;
    @Autowired
    PostService postService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/toUserManagement")
    public String toUserManagement(Model model){
        logger.info("进入toUserManagement方法");
        List<UserListVO> userList = userService.listUser();
        List<RoleDO> roles = roleService.selectRoles();
        List<DepartmentDO> depts = departmentService.listDept();
        model.addAttribute("users",userList);
        model.addAttribute("roles",roles);
        model.addAttribute("depts",depts);
        UserDO user = (UserDO)SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        return "demo/userManagement";
    }
    //删除用户的请求
    @RequestMapping(value="/delete/{userId}")
    @ResponseBody
    public Result<Boolean> deleteUser(@PathVariable("userId") int userId){
        logger.info("删除用户");
        boolean result = userService.deleteUser(userId);
        if (result == false) {
            return Result.error(CodeMsg.DELETEUSER_ERROR);
        }else{
            return Result.success(true);
        }
    }

    //用户管理，根据输入的信息，模糊用户名查询相关用户信息
    @RequestMapping(value = "/selectUserByTip/{selectTip}")
    public String selectUserByTip(Model model , @PathVariable("selectTip") String selectTip) {
        logger.info("模糊查询树");
        List<UserListVO> userList = userService.selectAllUserByTip(selectTip);
        List<RoleDO> roles = roleService.selectRoles();
        List<DepartmentDO> depts = departmentService.listDept();
        model.addAttribute("users",userList);
        model.addAttribute("roles",roles);
        model.addAttribute("depts",depts);
        UserDO user = (UserDO)SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        return "demo/userManagement";
    }

    @ResponseBody
    @RequestMapping(value = "/getPost")
    public List<PositionDO> getPost(@RequestParam int deptId){
        List<PositionDO> positions = postService.listPostByDeptId(deptId);
        return positions;
    }

    //注册请求，获取表单中的注册信息，将表单的信息封装成一个user，判断用户是否存在，然后事务添加用户以及其角色 默认User角色
    @RequestMapping (value = "/addUser")
    @ResponseBody
    public Result<Boolean> addUSer(AddUserForm addUserForm){
        logger.info("注册");
        logger.info(addUserForm.toString());
        UserDO user = userService.getOnlyUserByUserName(addUserForm.getName());
        if (user != null) {
            return Result.error(CodeMsg.USER_EXISTED);
        }
        boolean result = userService.addUser(addUserForm);
        if (result == false) {
            return Result.error(CodeMsg.ADDUSER_ERROR);
        }
        return Result.success(true);
    }

    @RequestMapping(value = "/selectUserByUserId/{userId}")
    @ResponseBody
    public UserListVO selectUserByUserId(@PathVariable("userId") int userId) {
        UserListVO user = userService.getUserByUserId(userId);
        return user;
    }

    @RequestMapping(value = "/alertUser")
    @ResponseBody
    public Result<Boolean> alertUser(UpdateUserForm updateUserForm){
        logger.info("进入编辑用户方法");
        logger.info(updateUserForm.toString());
        boolean result = userService.updateUser(updateUserForm);
        if (result == false) {
            return Result.error(CodeMsg.UPDATEUSER_ERROR);
        }
        return Result.success(true);
    }

    @RequestMapping(value = "/getCount")
    @ResponseBody
    public Result<Integer> getCount(){
        logger.info("获取所有用户的条数");
        int num = userService.countUser();
        logger.info("用户的条数："+num);
        return Result.success(num);
    }

    //删除用户的请求
    @RequestMapping(value="/batchBatch")
    @ResponseBody
    public Result<Boolean> deleteUser(String ids) {
        logger.info("批量删除用户，为"+ids);
        String[] strs = ids.split(",");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int id = Integer.parseInt(strs[i]);
            userService.deleteUser(id);
        }
        return Result.success(true);
    }

    //导出所有的数据
    @RequestMapping (value = "/download")
    public void excelDownload(HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        //ExcelData excel = new ExcelData();
        head.add("用户名");
        head.add("电话号码");
        head.add("邮箱");
        head.add("性别");
        head.add("部门");
        head.add("岗位");
        head.add("薪资");
        head.add("角色");
        head.add("创建时间");
        head.add("更新时间");
        data.add(head);
        List<UserListVO> userList = userService.listUser();
        for (UserListVO userVO : userList) {
            List<String> row = new ArrayList<>();
            UserDO user = userVO.getUser();
            row.add(user.getName());
            row.add(user.getTelephone());
            row.add(user.getEmail());
            row.add(user.getSex() == 0 ? "男" : "女");
            if (userVO.getDeptPosis().size() > 0) {
                row.add(userVO.getDeptPosis().get(0).getDeptName());
                row.add(userVO.getDeptPosis().get(0).getPostName());
                row.add(userVO.getDeptPosis().get(0).getSalary()+"");
            }else{
                row.add("");
                row.add("");
                row.add("");
            }
            if (userVO.getRoles().size() > 0) {
                row.add(userVO.getRoles().get(0).getName());
            }else{
                row.add("");
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            row.add(dateFormat.format(user.getCrtTime()));
            row.add(dateFormat.format(user.getUpdtTime()));
            data.add(row);
        }
        String sheetName = "用户列表";
        String fileName = "user.xls";
        ExcelUtil.exportExcel(response,data,sheetName,fileName,15);
    }
}