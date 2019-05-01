package cn.neuwljs.module_d.model.base;

import android.graphics.Bitmap;

/**
 * 发布者
 */
public class Publisher {

    /**
     * 头像
     */
    private Bitmap headImage;

    /**
     * 昵称
     */
    private String name;

    /**
     * 时间
     */
    private long time;

    /**
     * 学号
     */
    private String id;

    /**
     * 学院
     */
    private String college;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Bitmap getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Bitmap headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
