package com.heihei.management.system.controller;

import com.heihei.management.system.entity.ImportResult;
import com.heihei.management.system.entity.PositionDO;
import com.heihei.management.system.entity.PrivilegeDO;
import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.PrivilegeService;
import com.heihei.management.system.util.ExcelUtil;
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
    public String toPrvg(Model model,UserDO user) {
        List<PrivilegeDO> privileges = privilegeService.listPrivilege();
            for (int i = 0;i < privileges.size();i++){
            logger.info(privileges.get(i).toString());
        }
        model.addAttribute("prvgs",privileges);
        model.addAttribute("u",user);
        return "demo/prvgManagement";
    }

    //模糊查询岗位
    @RequestMapping(value = "/selectPrvgByTip/{selectTip}")
    public String selectPrvgByTip(Model model , @PathVariable("selectTip") String selectTip,UserDO user) {
        logger.info("模糊查询权限" + selectTip);
        List<PrivilegeDO> privileges = privilegeService.selectPostByTip(selectTip);
        for (int i = 0;i < privileges.size();i++) {
            logger.info(privileges.get(i).toString());
        }
        model.addAttribute("prvgs",privileges);
        model.addAttribute("u",user);
        return "demo/prvgManagement";
    }
    //添加权限
    @RequestMapping(value = "/addPrvg")
    @ResponseBody
    public Result<Boolean> addPrvg(PrivilegeDO privilegeDO,UserDO user) {
        PrivilegeDO privilege = privilegeService.getPrvgByPrvgName(privilegeDO.getName());
        if (privilege != null) {
            return  Result.error(CodeMsg.PRIVILEGE_EXISTED);
        }
        int item = privilegeService.addPrvg(privilegeDO);
        if (item <= 0) {
            return  Result.error(CodeMsg.ADD_PRIVILEGE_ERROR);
        }
        return Result.success(true);
    }
    //删除权限
    @RequestMapping(value="/delete/{prvgId}")
    @ResponseBody
    public Result<Boolean> deletePrvg(@PathVariable("prvgId") int prvgId,UserDO user){
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
    public PrivilegeDO selectPrvgByPrvgId(@PathVariable("prvgId") int prvgId,UserDO user){
        logger.info("查询权限");
        PrivilegeDO prvg = privilegeService.selectPrvgByPrvgId(prvgId);
        return prvg;
    }

    //修改权限
    @RequestMapping(value = "/editPrvg")
    @ResponseBody
    public Result<Boolean> editPrvg(PrivilegeDO privilegeDO,UserDO user) {
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
    public Result<Boolean> batchBatch(String ids,UserDO user) {
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

    @RequestMapping("/download")
    public void ExcelDownload(HttpServletResponse response,UserDO user) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        head.add("权限名");
        head.add("访问链接");
        data.add(head);
        List<PrivilegeDO> privileges = privilegeService.listPrivilege();
        for (PrivilegeDO prvg : privileges) {
            List<String> row = new ArrayList<>();
            row.add(prvg.getName());
            row.add(prvg.getUrl());
            data.add(row);
        }
        String sheetName = "权限列表";
        String fileName = "prvg.xls";
        ExcelUtil.exportExcel(response,data,sheetName,fileName,20);
    }

    @RequestMapping("/importData")
    @ResponseBody
    public Result<ImportResult> importData(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request,UserDO user) {
        logger.info("进入批量导入权限的方法");
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
                String url = "";
                Row row = sheet.getRow(r);
                PrivilegeDO privilege = new PrivilegeDO();
                if (row != null) {
                    Cell cellName = row.getCell(0);
                    cellName.setCellType(CellType.STRING);
                    name = cellName.getStringCellValue();
                    Cell cellUrl = row.getCell(1);
                    cellUrl.setCellType(CellType.STRING);
                    url = cellUrl.getStringCellValue();
                    //根据权限名查询权限是否存在
                    PrivilegeDO prvg = privilegeService.getPrvgByPrvgName(name);
                    if (prvg != null) {
                        prvg.setUrl(url);
                        privilegeService.updateDept(prvg);
                        updateCount++;
                    }else{
                        privilege.setName(name);
                        privilege.setUrl(url);
                        privilegeService.addPrvg(privilege);
                        addCount++;
                    }
                }
            }
        } catch (IOException e) {
            Result.success(CodeMsg.READ_FILE_ERRPE);
        }
        return Result.success(new ImportResult(addCount,updateCount));
    }

    @RequestMapping (value = "/downModal")
    public void downModal(HttpServletResponse response,UserDO user) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        head.add("权限名");
        head.add("访问链接");
        data.add(head);
        List<String> row = new ArrayList<>();
        row.add("XXX");
        row.add("/XXX/XXX");
        data.add(row);
        String sheetName = "权限列表";
        String fileName = "prvg.xls";
        ExcelUtil.exportExcel(response,data,sheetName,fileName,20);
    }
}
