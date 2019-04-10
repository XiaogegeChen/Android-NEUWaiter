package com.neuwljs.wallsmalltwo.model.gson;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

/**
 * 招领的bean
 */
public class Found {

    /**
     * 发布者头像
     */
    @SerializedName ("pub_head_img")
    private String publisherHeadImage;

    /**
     * 发布者姓名
     */
    @SerializedName ("pub_name")
    private String publisherName;

    /**
     * 发布时间
     */
    @SerializedName ("pub_time")
    private String publishTime;

    /**
     * 发布者发布时附带的详细内容
     */
    @SerializedName ("pub_info")
    private String information;

    /**
     * 失主姓名
     */
    @SerializedName ("own_name")
    private String ownerName;

    /**
     * 失主学号
     */
    @SerializedName ("own_id")
    private String ownerId;

    /**
     * 失主学院
     */
    @SerializedName ("own_college")
    private String ownerCollege;

    public String getPublisherHeadIamge() {
        return publisherHeadImage;
    }

    public void setPublisherHeadIamge(String publisherHeadIamge) {
        this.publisherHeadImage = publisherHeadIamge;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerCollege() {
        return ownerCollege;
    }

    public void setOwnerCollege(String ownerCollege) {
        this.ownerCollege = ownerCollege;
    }

    @NonNull
    @Override
    public String toString() {
        return "publisherName: "+publisherName+" publishTime: "+publishTime+" information: "+information;
    }
}
