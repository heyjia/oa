package com.heihei.management.system.controller;

import com.heihei.management.system.entity.ImportResult;
import com.heihei.management.system.entity.PrivilegeDO;
import com.heihei.management.system.entity.RoleDO;
import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.entity.form.AddRoleForm;
import com.heihei.management.system.entity.vo.RolePrvgVO;
import com.heihei.management.system.entity.vo.RoleVO;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.PrivilegeService;
import com.heihei.management.system.service.RoleService;
import com.heihei.management.system.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
    @RequestMapping (value = "/downModal")
    public void downModal(HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        head.add("角色名");
        head.add("角色权限");
        data.add(head);
        List<String> row = new ArrayList<>();
        row.add("XXX");
        row.add("xxx|xxx(用‘|’字符隔开)");
        data.add(row);
        String sheetName = "岗位列表";
        String fileName = "role.xls";
        ExcelUtil.exportExcel(response,data,sheetName,fileName,40);
    }

    @RequestMapping("/importData")
    @ResponseBody
    public Result<ImportResult> importData(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        logger.info("进入批量导入角色的方法");
        HSSFWorkbook workbook = null;
        int addCount = 0;
        int updateCount = 0;
        try {
            InputStream is = file.getInputStream();
            workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            int columns = 0;
            for (int r = 0; r < rows; r++) {
                if (r == 0) {
                    columns = sheet.getRow(r).getLastCellNum();
                    continue;
                }
                String name = "";
                String prvgStr = "";
                Row row = sheet.getRow(r);
                RoleDO role = null;
                if (row != null) {
                    Cell cellName = row.getCell(0);
                    cellName.setCellType(CellType.STRING);
                    name = cellName.getStringCellValue();
                    Cell cellPrvg = row.getCell(1);
                    cellPrvg.setCellType(CellType.STRING);
                    prvgStr = cellPrvg.getStringCellValue();
                    //根据岗位名查询岗位是否存在
                    role = roleService.getRoleByName(name);
                    String[] strs = prvgStr.split("\\|");
                    logger.info("长度"+strs.length+"");
                    if (role != null) {
                        privilegeService.deletePrvgRoleByRoleId(role.getId());
                        updateCount++;
                    }else{
                        role = new RoleDO();
                        role.setName(name);
                        roleService.addRole(role);
                        addCount++;
                    }
                    for (int i = 0; i < strs.length;i++) {
                        logger.info(strs[i]+"");
                        if (strs[i].equals("")){
                            continue;
                        }
                        PrivilegeDO prvg = privilegeService.getPrvgByPrvgName(strs[i]);
                        if (prvg == null) {
                            continue;
                        }
                        roleService.addPrvgToRole(role.getId(),prvg.getId());
                    }
                }
            }
        } catch (IOException e) {
            Result.success(CodeMsg.READ_FILE_ERRPE);
        }
        return Result.success(new ImportResult(addCount,updateCount));
    }
}
