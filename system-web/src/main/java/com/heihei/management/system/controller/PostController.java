package com.heihei.management.system.controller;

import com.heihei.management.system.entity.ImportResult;
import com.heihei.management.system.entity.PositionDO;
import com.heihei.management.system.entity.UserDO;
import com.heihei.management.system.entity.form.AddPostForm;
import com.heihei.management.system.result.CodeMsg;
import com.heihei.management.system.result.Result;
import com.heihei.management.system.service.PostService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName PostController
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/23 18:12
 **/
@Controller
@RequestMapping(value = "/post")
public class PostController {
    Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    PostService postService;
    @RequestMapping (value = "/toPostManagement")
    public String toPostManagement(Model model){
        List<PositionDO> positions = postService.listPost();
        model.addAttribute("positions",positions);
        UserDO user = (UserDO)SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        return "demo/postManagement";
    }
    //模糊查询岗位
    @RequestMapping(value = "/selectPostByTip/{selectTip}")
    public String selectPostByTip(Model model , @PathVariable("selectTip") String selectTip) {
        logger.info("模糊查询岗位" + selectTip);
        List<PositionDO> positions = postService.selectPostByTip(selectTip);
        for (int i = 0;i < positions.size();i++) {
            logger.info(positions.get(i).toString());
        }
        model.addAttribute("positions",positions);
        UserDO user = (UserDO) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        model.addAttribute("u",user);
        return "demo/postManagement";
    }
    //添加部门
    @RequestMapping(value = "/addPost")
    @ResponseBody
    public Result<Boolean> addPost(AddPostForm addPostForm) {
        PositionDO post = postService.getPostByPostName(addPostForm.getPostName());
        if (post != null) {
            return  Result.error(CodeMsg.POSITION_EXISTED);
        }
        PositionDO toAddPost = new PositionDO();
        toAddPost.setUpdtTime(new Date());
        toAddPost.setCrtTime(new Date());
        toAddPost.setName(addPostForm.getPostName());
        toAddPost.setDescribe(addPostForm.getDescribe());
        int item = postService.addPost(toAddPost);
        if (item <= 0) {
            return  Result.error(CodeMsg.ADD_POSITION_ERROR);
        }
        return Result.success(true);
    }
    //删除岗位
    @RequestMapping(value="/delete/{postId}")
    @ResponseBody
    public Result<Boolean> deletePost(@PathVariable("postId") int postId){
        logger.info("删除岗位");
        //删除岗位和用户的联系
        postService.deleteUserPostByPostId(postId);
        //删除岗位和部门的联系
        postService.deleteDeptPostByPostId(postId);
        //删除岗位
        int item = postService.deletePostByPostId(postId);
        if (item <= 0) {
            return Result.error(CodeMsg.DELETE_POSITION_ERROR);
        }
        return Result.success(true);
    }

    //修改岗位editPost
    @RequestMapping(value = "/editPost")
    @ResponseBody
    public Result<Boolean> editPost(AddPostForm addPostForm) {
        logger.info("editPost");
        PositionDO toUpdatePost = postService.getPostByPostId(addPostForm.getPostId());
        PositionDO post = postService.getPostByPostName(addPostForm.getPostName());
        if (post != null && !(post.getName().equals(toUpdatePost.getName()))) {
            return  Result.error(CodeMsg.POSITION_EXISTED);
        }
        toUpdatePost.setUpdtTime(new Date());
        toUpdatePost.setName(addPostForm.getPostName());
        toUpdatePost.setDescribe(addPostForm.getDescribe());
        int item = postService.updatePostByForm(toUpdatePost);
        if (item <= 0) {
            return  Result.error(CodeMsg.UPDATE_POSITION_ERROR);
        }
        return Result.success(true);
    }

    //根据岗位id查询岗位为了回显
    @RequestMapping(value="/selectPostByPostId/{postId}")
    @ResponseBody
    public PositionDO selectPostByPostId(@PathVariable("postId") int postId){
        logger.info("查询岗位");
        PositionDO post = postService.getPostByPostId(postId);
        return post;
    }


    //批量删除岗位
    @RequestMapping(value="/batchBatch")
    @ResponseBody
    public Result<Boolean> batchBatch(String ids) {
        logger.info("批量删除岗位，为"+ids);
        String[] strs = ids.split(",");
        for (int i = 0; i < strs.length;i++) {
            if (strs[i].equals("")){
                continue;
            }
            int id = Integer.parseInt(strs[i]);
            //删除岗位和用户的联系
            postService.deleteUserPostByPostId(id);
            //删除岗位和部门的联系
            postService.deleteDeptPostByPostId(id);
            //删除岗位
            postService.deletePostByPostId(id);
        }
        return Result.success(true);
    }

    @RequestMapping(value = "/download")
    public void excelDownload(HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        head.add("岗位名");
        head.add("描述");
        head.add("创建时间");
        head.add("更新时间");
        data.add(head);
        List<PositionDO> positions = postService.listPost();
        for (PositionDO post : positions) {
            List<String> row = new ArrayList<>();
            row.add(post.getName());
            row.add(post.getDescribe());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            row.add(format.format(post.getCrtTime()));
            row.add(format.format(post.getUpdtTime()));
            data.add(row);
        }
        String sheetName = "岗位列表";
        String fileName = "post.xls";
        ExcelUtil.exportExcel(response,data,sheetName,fileName,20);
    }

    @RequestMapping (value = "/downModal")
    public void downModal(HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        head.add("岗位名");
        head.add("描述");
        data.add(head);
        List<String> row = new ArrayList<>();
        row.add("XXX");
        row.add("");
        data.add(row);
        String sheetName = "岗位列表";
        String fileName = "position.xls";
        ExcelUtil.exportExcel(response,data,sheetName,fileName,20);
    }

    @RequestMapping("/importData")
    @ResponseBody
    public Result<ImportResult> importData(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        logger.info("进入批量导入岗位的方法");
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
                String describe = "";
                Row row = sheet.getRow(r);
                PositionDO position = new PositionDO();
                if (row != null) {
                    Cell cellName = row.getCell(0);
                    cellName.setCellType(CellType.STRING);
                    name = cellName.getStringCellValue();
                    Cell cellDesb = row.getCell(1);
                    cellDesb.setCellType(CellType.STRING);
                    describe = cellDesb.getStringCellValue();
                    //根据岗位名查询岗位是否存在
                    PositionDO post = postService.getPostByPostName(name);
                    if (post != null) {
                        post.setDescribe(describe);
                        post.setUpdtTime(new Date());
                        postService.updatePostByForm(post);
                        updateCount++;
                    }else{
                        position.setName(name);
                        position.setDescribe(describe);
                        position.setCrtTime(new Date());
                        position.setUpdtTime(new Date());
                        postService.addPost(position);
                        addCount++;
                    }
                }
            }
        } catch (IOException e) {
            Result.success(CodeMsg.READ_FILE_ERRPE);
        }
        return Result.success(new ImportResult(addCount,updateCount));
    }
}
