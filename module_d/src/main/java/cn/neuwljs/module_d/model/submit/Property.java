package cn.neuwljs.module_d.model.submit;

import cn.neuwljs.module_d.model.base.LostOrFound;
import cn.neuwljs.module_d.model.base.LostPropertyType;

/**
 * 待提交的物品信息
 */
public class Property {

    /**
     * 物品类型,只能是
     * {@link LostPropertyType#STUDENT_CARD}或
     * {@link LostPropertyType#ID_CARD}或
     * {@link LostPropertyType#BANK_CARD}或
     * {@link LostPropertyType#OTHER}
     */
    private String lostPropertyType;

    /**
     * 物品属性,只能是
     * {@link LostOrFound#LOST}或
     * {@link LostOrFound#FOUND}
     */
    private String lostOrFound;

    /**
     * 物品归属者的姓名,就是失主的姓名
     */
    private String ownerName;

    /**
     * 失主的学院
     */
    private String ownerCollege;

    /**
     * 失主的学号
     */
    private String ownerId;

    /**
     * 详细信息
     */
    private String information;

    /**
     * 发布者的姓名
     */
    private String publisherName;

    /**
     * 发布者的学院
     */
    private String publisherCollege;

    /**
     * 发布者的学号
     */
    private String publisherId;

    /**
     * 发布时间
     */
    private String publishTime;

    public Property(){
    }

    public String getLostPropertyType() {
        return lostPropertyType;
    }

    public void setLostPropertyType(String lostPropertyType) {
        this.lostPropertyType = lostPropertyType;
    }

    public String getLostOrFound() {
        return lostOrFound;
    }

    public void setLostOrFound(String lostOrFound) {
        this.lostOrFound = lostOrFound;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerCollege() {
        return ownerCollege;
    }

    public void setOwnerCollege(String ownerCollege) {
        this.ownerCollege = ownerCollege;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherCollege() {
        return publisherCollege;
    }

    public void setPublisherCollege(String publisherCollege) {
        this.publisherCollege = publisherCollege;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
