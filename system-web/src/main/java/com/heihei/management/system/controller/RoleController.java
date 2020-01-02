package com.heihei.management.system.controller;

import com.heihei.management.system.entity.database.PrivilegeDO;
import com.heihei.management.system.entity.database.RoleDO;
import com.heihei.management.system.entity.database.UserDO;
import com.heihei.management.system.entity.form.AddRoleForm;
import com.heihei.management.system.entity.vo.RolePrvgVO;
import com.heihei.management.system.entity.vo.RoleVO;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.PrivilegeService;
import com.heihei.management.system.service.RoleService;
import com.heihei.management.system.utils.ExcelUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/25 11:29
 **/
@Controller
@RequestMapping(value = "/role")
public class RoleController {
    Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    RoleService roleService;
    @Autowired
    PrivilegeService privilegeService;
    //查询角色列表
    @RequestMapping(value = "/toRole")
    public String toRole(Model model){
        List<RoleDO> roles = roleService.selectRoles();
        List<RoleVO> roleVos = new ArrayList<>();
        for (RoleDO role : roles) {
            RoleVO v = new RoleVO();
            v.setRole(role);
            List<PrivilegeDO> privileges = privilegeService.listPrivilegeByRoleId(role.getId());
            v.setPrvgs(privileges);
            roleVos.add(v);
        }
        model.addAttribute("roleVos",roleVos);
        UserDO user = (UserDO) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        List<PrivilegeDO> prvgs = privilegeService.listPrivilege();
        for (int i = 0;i < prvgs.size();i++) {
            logger.info(prvgs.get(i).toString());
        }
        model.addAttribute("prvgs",prvgs);
        return "demo/roleManagement";
    }

    //模糊查询角色

    //模糊查询岗位
    @RequestMapping(value = "/selectRoleByTip/{selectTip}")
    public String selectRoleByTip(Model model , @PathVariable("selectTip") String selectTip) {
        logger.info("模糊查询角色" + selectTip);
        List<RoleDO> roles = roleService.selectRoleByTip(selectTip);
        List<RoleVO> roleVos = new ArrayList<>();
        for (RoleDO role : roles) {
            RoleVO v = new RoleVO();
            v.setRole(role);
            List<PrivilegeDO> privileges = privilegeService.listPrivilegeByRoleId(role.getId());
            v.setPrvgs(privileges);
            roleVos.add(v);
        }
        model.addAttribute("roleVos",roleVos);
        UserDO user = (UserDO) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        List<PrivilegeDO> prvgs = privilegeService.listPrivilege();
        for (int i = 0;i < prvgs.size();i++) {
            logger.info(prvgs.get(i).toString());
        }
        model.addAttribute("prvgs",prvgs);
       return "demo/roleManagement";
    }
    //删除角色
    @RequestMapping(value="/delete/{roleId}")
    @ResponseBody
    public Result<Boolean> deletePrvg(@PathVariable("roleId") int roleId){
        logger.info("删除角色");
        //删除权限和角色的联系
        privilegeService.deletePrvgRoleByRoleId(roleId);
        //删除角色
        int item = roleService.deleteRoleByRoleId(roleId);
        if (item <= 0) {
            return Result.error(CodeMsg.DELETE_ROLE_ERROR);
        }
        return Result.success(true);
    }

    //批量删除角色
    @RequestMapping(value="/batchDelete")
    @ResponseBody
    public Result<Boolean> batchDelete(String ids) {
        logger.info("批量删除角色，为"+ids);
        String[] strs = ids.split(",");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int id = Integer.parseInt(strs[i]);
            //删除角色和权限的联系
            privilegeService.deletePrvgRoleByRoleId(id);
            //删除角色
            roleService.deleteRoleByRoleId(id);
        }
        return Result.success(true);
    }

    // 添加角色
    //添加部门请求
    @RequestMapping(value = "/addRole")
    @ResponseBody
    public Result<Boolean> addRole(AddRoleForm addRoleForm){
        logger.info("添加角色请求addRole");
        logger.info("AddRoleForm:"+addRoleForm.toString());
        //添加角色
        RoleDO role = null;
        role = roleService.getRoleByName(addRoleForm.getRoleName());
        if (role != null) {
            return Result.error(CodeMsg.ROLE_EXISTED);
        }
        role = new RoleDO();
        role.setName(addRoleForm.getRoleName());
        int item = roleService.addRole(role);
        if (item <= 0) {
            return Result.error(CodeMsg.ADD_ROLE_ERROR);
        }
        //添加角色的权限
        String[] strs = addRoleForm.getPrvgIds().split(",");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int prvgId = Integer.parseInt(strs[i]);
            roleService.addPrvgToRole(role.getId(),prvgId);
        }
        return Result.success(true);
    }

    //获取用户及其权限的信息selectRoleByRoleId
    //根据岗位id查询岗位为了回显
    //获取单个部门信息的请求
    @RequestMapping(value = "/selectRoleByRoleId/{roleId}")
    @ResponseBody
    public RolePrvgVO selectRoleByRoleId(@PathVariable("roleId") int roleId){
        RolePrvgVO rolePrvgVO = new RolePrvgVO();
        rolePrvgVO.setRole(roleService.getRoleById(roleId));
        rolePrvgVO.setPrvgs(privilegeService.listPrivilegeByRoleId(roleId));
        return rolePrvgVO;
    }

    //编辑角色editRole
    @RequestMapping(value = "/editRole")
    @ResponseBody
    public Result<Boolean> editRole(AddRoleForm addRoleForm){
        logger.info("编辑角色请求updateDept");
        logger.info("addRoleForm:"+addRoleForm.toString());
        RoleDO role = roleService.getRoleByName(addRoleForm.getRoleName());
        if (role != null && role.getId() != addRoleForm.getId()) {
            return Result.error(CodeMsg.ROLE_EXISTED);
        }
        role = new RoleDO();
        role.setId(addRoleForm.getId());
        role.setName(addRoleForm.getRoleName());
        int item = roleService.updateRole(role);
        if (item <= 0) {
            return Result.error(CodeMsg.UPDATE_ROLE_ERROR);
        }
        privilegeService.deletePrvgRoleByRoleId(role.getId());
        logger.info("item:" + item);
        logger.info("roleId:" + role.getId());
        String[] strs = addRoleForm.getPrvgIds().split(",");
        logger.info(strs.length+"");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int prvgId = Integer.parseInt(strs[i]);
            roleService.addPrvgToRole(role.getId(),prvgId);
        }
        return Result.success(true);
    }

    @RequestMapping(value = "/download")
    public void excelDownload(HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        head.add("角色名");
        head.add("角色权限");
        data.add(head);
        List<RoleDO> roles = roleService.selectRoles();
        List<RoleVO> roleVos = new ArrayList<>();
        for (RoleDO role : roles) {
            RoleVO v = new RoleVO();
            v.setRole(role);
            List<PrivilegeDO> privileges = privilegeService.listPrivilegeByRoleId(role.getId());
            v.setPrvgs(privileges);
            roleVos.add(v);
        }
        for (RoleVO role :roleVos) {
            List<String> row = new ArrayList<>();
            row.add(role.getRole().getName());
            String prvgs = "";
            for (PrivilegeDO privilege : role.getPrvgs()) {
                prvgs += privilege.getName() + " ";
            }
            row.add(prvgs);
            data.add(row);
        }
        String sheetName = "角色列表";
        String fileName = "role.xls";
        ExcelUtil.exportExcel(response,data,sheetName,fileName,50);
    }
}
