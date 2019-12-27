package com.heihei.management.system.entity.form;

/**
 * @ClassName AddPostForm
 * @Description TODO
 * @Author CHENZEJIA
 * @Date 2019/12/23 21:48
 **/
public class AddPostForm {
    private int postId;
    private String postName;
    private String describe;

    public AddPostForm() {
    }

    public AddPostForm(int postId, String postName, String describe) {
        this.postId = postId;
        this.postName = postName;
        this.describe = describe;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "AddPostForm{" +
                "postId=" + postId +
                ", postName='" + postName + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
