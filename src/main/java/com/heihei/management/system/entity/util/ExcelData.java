package com.heihei.management.system.entity.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExcelData implements Serializable {
   private String fileName;
   private String sheetName;
   private String columnName;
   private List<String> head;
   private List<List<String>> data;
   public ExcelData() {
       head = new ArrayList<>();
       data = new ArrayList<>();
   }
    public ExcelData(String fileName, String sheetName, String columnName, List<String> head, List<List<String>> data) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.columnName = columnName;
        this.head = head;
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public List<String> getHead() {
        return head;
    }

    public void setHead(List<String> head) {
        this.head = head;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
