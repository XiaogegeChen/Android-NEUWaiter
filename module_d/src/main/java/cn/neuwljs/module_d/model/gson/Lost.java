package cn.neuwljs.module_d.model.gson;

import com.google.gson.annotations.SerializedName;

public class Lost {

    /**
     * 失主姓名
     */
    @SerializedName("own_name")
    private String OwnerName;

    /**
     * 失主学号
     */
    @SerializedName("own_id")
    private String OwnerId;

    /**
     * 失主学院
     */
    @SerializedName("own_college")
    private String OwnerCollege;

    /**
     * 失主头像url地址
     */
    @SerializedName("own_head_img")
    private String OwnerHeadImage;

    /**
     * 失主发布信息的时间
     */
    @SerializedName("own_time")
    private String OwnerPublishTime;

    /**
     * 失主发布的详细信息
     */
    @SerializedName("own_info")
    private String OwnerInfo;

    /**
     * 失主丢失物品的类型
     */
    @SerializedName("own_type")
    private String OwnerPropertyType;

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getOwnerCollege() {
        return OwnerCollege;
    }

    public void setOwnerCollege(String ownerCollege) {
        OwnerCollege = ownerCollege;
    }

    public String getOwnerHeadImage() {
        return OwnerHeadImage;
    }

    public void setOwnerHeadImage(String ownerHeadImage) {
        OwnerHeadImage = ownerHeadImage;
    }

    public String getOwnerPublishTime() {
        return OwnerPublishTime;
    }

    public void setOwnerPublishTime(String ownerPublishTime) {
        OwnerPublishTime = ownerPublishTime;
    }

    public String getOwnerInfo() {
        return OwnerInfo;
    }

    public void setOwnerInfo(String ownerInfo) {
        OwnerInfo = ownerInfo;
    }

    public String getOwnerPropertyType() {
        return OwnerPropertyType;
    }

    public void setOwnerPropertyType(String ownerPropertyType) {
        OwnerPropertyType = ownerPropertyType;
    }
}
