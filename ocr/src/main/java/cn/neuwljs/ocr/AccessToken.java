package cn.neuwljs.ocr;

import com.google.gson.annotations.SerializedName;

/**
 * 百度OCR鉴权返回的json的解析类
 */
class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    String getAccessToken() {
        return accessToken;
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
