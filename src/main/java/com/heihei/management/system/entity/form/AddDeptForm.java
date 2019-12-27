package com.heihei.management.system.entity.form;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @ClassName AddDeptForm
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/23 12:45
 **/
public class AddDeptForm {
    private int id;
    private String deptName;
    private String address;
    private String describe;
    private int selectDept;
    private String postIds;

    public AddDeptForm() {
    }

    public AddDeptForm(int id, String deptName, String address, String describe, int selectDept, String postIds) {
        this.id = id;
        this.deptName = deptName;
        this.address = address;
        this.describe = describe;
        this.selectDept = selectDept;
        this.postIds = postIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getSelectDept() {
        return selectDept;
    }

    public void setSelectDept(int selectDept) {
        this.selectDept = selectDept;
    }

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds;
    }
}
