package com.geektech.lesson2.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {
    Integer id;
    String content;
    @SerializedName("group")
    Integer groupId;
    String title;
    @SerializedName("user")
    Integer userId;


    public Post(String content, Integer groupId, String title, Integer userId) {
        this.content = content;
        this.groupId = groupId;
        this.title = title;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUserId() {
        return userId;
    }
}

