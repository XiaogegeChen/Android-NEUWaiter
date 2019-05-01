package cn.neuwljs.ocr;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 */
class Constant {

    // 使用到的head的key和baseurl map
    static final Map<String, String> MAP;

    // 用于动态更改baseurl的head的key
    static final String OKHTTP_HEAD_NAME = "okhttp_head_name";

    /**
     * 百度OCR的AK
     */
    static final String OCR_AK = "Fww20HZOlhRhNuvQET6jDSQt";

    /**
     * 百度OCR的SK
     */
    static final String OCR_SK = "ylXMmGC4VMC90zwsBwDpQ68EmzbjulUE";

    /**
     * 百度OCR的head的key
     */
    static final String OCR_HEAD_KEY="ocr";

    /**
     * 百度OCR的baseUrl
     */
    private static final String OCR_BASE_URL = "https://aip.baidubce.com/";

    /**
     * 百度OCR的GRANT_TYPE
     */
    static final String GRANT_TYPE = "client_credentials";

    /**
     * 百度OCR请求头中的Content-Type的key
     */
    static final String CONTENT_TYPE_KEY = "Content-Type";

    /**
     * 百度OCR请求头中的Content-Type的value
     */
    static final String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

    /**
     * xml里面access_token的key
     */
    static final String OCR_XML_ACCESS_TOKEN_KEY = "ocr_xml_access_token_key";

    /**
     * xml里面access_token第一次请求到时间的key
     */
    static final String OCR_XML_ACCESS_TOKEN_TIME_KEY = "ocr_xml_access_token_time_key";

    static {
        MAP = new HashMap<> ();
        MAP.put (OCR_HEAD_KEY, OCR_BASE_URL);
    }
}
