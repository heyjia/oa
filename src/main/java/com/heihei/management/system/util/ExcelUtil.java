package com.heihei.management.system.util;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    public static void exportExcel(HttpServletResponse response, List<List<String>> excelData,String sheetName,
                                   String fileName, int columnWidth) throws IOException {
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格的名字
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表格列的宽度
        //sheet.setDefaultColumnWidth(columnWidth);
        sheet.setDefaultColumnWidth(columnWidth);
        //写入数据
        int rowIndex = 0;
        for (List<String> data : excelData) {
            //创建一行
            HSSFRow row = sheet.createRow(rowIndex++);
            //遍历所有的数据
            for (int i = 0;i < data.size();i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(data.get(i));
                cell.setCellValue(text);
            }
        }
        //将excel的输出流通过response输出到页面下载
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
