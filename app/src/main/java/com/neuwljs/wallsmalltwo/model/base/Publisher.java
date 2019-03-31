package com.neuwljs.wallsmalltwo.model.base;

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
    private String time;

    /**
     * 详细内容，描述
     */
    private String content;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
