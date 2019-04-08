package com.neuwljs.wallsmalltwo.model.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 提交之后返回的结果
 */
public class SubmitPropertyResult {

    /**
     * 该条数据插入数据库中的序列号
     */
    @SerializedName ("ser_num")
    private String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
