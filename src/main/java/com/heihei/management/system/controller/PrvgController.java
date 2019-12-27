package com.heihei.management.system.controller;

import com.heihei.management.system.entity.PositionDO;
import com.heihei.management.system.entity.PrivilegeDO;
import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.PrivilegeService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName PrvgController
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/24 20:54
 **/
@Controller
@RequestMapping(value = "/prvg")
public class PrvgController {
    Logger logger = LoggerFactory.getLogger(PrvgController.class);
    @Autowired
    PrivilegeService privilegeService;
    @RequestMapping(value = "toPrvg")
    public String toPrvg(Model model) {
        List<PrivilegeDO> privileges = privilegeService.listPrivilege();
        for (int i = 0;i < privileges.size();i++){
            logger.info(privileges.get(i).toString());
        }
        model.addAttribute("prvgs",privileges);
        UserDO user = (UserDO) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        return "demo/prvgManagement";
    }

    //模糊查询岗位
    @RequestMapping(value = "/selectPrvgByTip/{selectTip}")
    public String selectPrvgByTip(Model model , @PathVariable("selectTip") String selectTip) {
        logger.info("模糊查询权限" + selectTip);
        List<PrivilegeDO> privileges = privilegeService.selectPostByTip(selectTip);
        for (int i = 0;i < privileges.size();i++) {
            logger.info(privileges.get(i).toString());
        }
        model.addAttribute("prvgs",privileges);
        UserDO user = (UserDO) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        return "demo/prvgManagement";
    }
    //添加部门
    @RequestMapping(value = "/addPrvg")
    @ResponseBody
    public Result<Boolean> addPrvg(PrivilegeDO privilegeDO) {
        PrivilegeDO privilege = privilegeService.getPrvgByPrvgName(privilegeDO.getName());
        if (privilege != null) {
            return  Result.error(CodeMsg.PRIVILEGE_EXISTED);
        }
        int item = privilegeService.addPost(privilegeDO);
        if (item <= 0) {
            return  Result.error(CodeMsg.ADD_PRIVILEGE_ERROR);
        }
        return Result.success(true);
    }
    //删除权限
    @RequestMapping(value="/delete/{prvgId}")
    @ResponseBody
    public Result<Boolean> deletePrvg(@PathVariable("prvgId") int prvgId){
        logger.info("删除权限");
        //删除权限和角色的联系
        privilegeService.deletePrvgRoleByPrvgId(prvgId);
        //删除权限
        int item = privilegeService.deletePrvgByPrvgId(prvgId);
        if (item <= 0) {
            return Result.error(CodeMsg.DELETE_PRIVILEGE_ERROR);
        }
        return Result.success(true);
    }
    //查询权限selectPrvgByPrvgId
    //根据权限id查询权限为了回显
    @RequestMapping(value="/selectPrvgByPrvgId/{prvgId}")
    @ResponseBody
    public PrivilegeDO selectPrvgByPrvgId(@PathVariable("prvgId") int prvgId){
        logger.info("查询权限");
        PrivilegeDO prvg = privilegeService.selectPrvgByPrvgId(prvgId);
        return prvg;
    }

    //修改权限
    @RequestMapping(value = "/editPrvg")
    @ResponseBody
    public Result<Boolean> editPrvg(PrivilegeDO privilegeDO) {
        logger.info("editPrvg");
        PrivilegeDO privilege = privilegeService.getPrvgByPrvgName(privilegeDO.getName());
        if (privilege != null && privilege.getId() != privilegeDO.getId()) {
            return  Result.error(CodeMsg.PRIVILEGE_EXISTED);
        }
        int item = privilegeService.updateDept(privilegeDO);
        if (item <= 0) {
            return  Result.error(CodeMsg.UPDATE_PRIVILEGE_ERROR);
        }
        return Result.success(true);
    }
    //批量删除权限batchBatch
    @RequestMapping(value="/batchBatch")
    @ResponseBody
    public Result<Boolean> batchBatch(String ids) {
        logger.info("批量删除权限，为"+ids);
        String[] strs = ids.split(",");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int id = Integer.parseInt(strs[i]);
            privilegeService.deletePrvgByPrvgId(id);
        }
        return Result.success(true);
    }
}
