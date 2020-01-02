package com.heihei.management.system.result;

/**
 * @ClassName CodeMsg
 * @Description 错误时候的结果集
 * @Author CHENZEJIA
 * @Date 2019/12/17 10:57
 **/
public class CodeMsg {


    private int code;
    private String msg;
    //登录模块
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(10001,"密码错误");
    public static CodeMsg UNKNOWACCOUNT = new CodeMsg(10002,"用户不存在");
    public static CodeMsg LOGOUT_ERROR = new CodeMsg(10004,"登录失败");
    public static CodeMsg USER_EXISTED = new CodeMsg(10005,"用户已存在");
    public static CodeMsg REGISTER_ERROR = new CodeMsg(10006,"数据库异常，注册失败");
    //显示模块
    public static CodeMsg UPDATE_ERROR = new CodeMsg(20001,"修改用户资料失败");
    public static CodeMsg OLDPASSWORD_ERROR = new CodeMsg(20002,"旧密码错误");
    public static CodeMsg UPDATEPASSWORD_ERROR = new CodeMsg(20003,"更换密码失败");
    //用户管理
    public static CodeMsg DELETEUSER_ERROR = new CodeMsg(30001,"删除用户失败");
    public static CodeMsg UPDATEUSER_ERROR = new CodeMsg(30002,"更改用户资料失败");
    public static CodeMsg ADDUSER_ERROR = new CodeMsg(30003,"添加用户失败");
    //通用
    public static CodeMsg BIRTHDAY_ERROR = new CodeMsg(40001,"生日格式错误");
    //部门管理
    public static CodeMsg DELETEDEPT_ERROR = new CodeMsg(50001,"删除部门失败");
    public static CodeMsg ADDEDEPT_ERROR = new CodeMsg(50002,"添加部门失败");
    public static CodeMsg DEPT_EXISTED = new CodeMsg(50003,"部门名称已存在");
    //岗位管理
    public static CodeMsg  POSITION_EXISTED= new CodeMsg(60001,"岗位名称已存在");
    public static CodeMsg  ADD_POSITION_ERROR= new CodeMsg(60002,"增加岗位失败，数据库异常");
    public static CodeMsg  DELETE_POSITION_ERROR= new CodeMsg(60003,"删除岗位失败，数据库异常");
    public static CodeMsg  UPDATE_POSITION_ERROR= new CodeMsg(60004,"更新岗位失败，数据库异常");

    public static CodeMsg  PRIVILEGE_EXISTED= new CodeMsg(70001,"权限名已经存在");
    public static final CodeMsg ADD_PRIVILEGE_ERROR =new CodeMsg(70002,"添加权限失败，数据库异常");
    public static final CodeMsg DELETE_PRIVILEGE_ERROR = new CodeMsg(70003,"删除权限失败，数据库异常");
    public static final CodeMsg UPDATE_PRIVILEGE_ERROR = new CodeMsg(70004,"更新权限失败，数据库异常");

    public static final CodeMsg DELETE_ROLE_ERROR =  new CodeMsg(80001,"删除角色失败，数据库异常");
    public static final CodeMsg ROLE_EXISTED = new CodeMsg(80002,"角色名已存在");
    public static final CodeMsg ADD_ROLE_ERROR = new CodeMsg(80003,"添加角色失败，数据库异常");
    public static final CodeMsg UPDATE_ROLE_ERROR = new CodeMsg(80004,"更新角色失败，数据库异常");

    public static final CodeMsg READ_FILE_ERRPE = new CodeMsg(90001,"读取文件失败");;
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
