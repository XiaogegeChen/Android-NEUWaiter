package com.neuwljs.wallsmalltwo.model.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 百度OCR鉴权返回的json的解析类
 */
public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    public AccessToken() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
