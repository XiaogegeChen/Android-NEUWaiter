package cn.neuwljs.module_d;

import java.util.HashMap;
import java.util.Map;

import static cn.neuwljs.common.base.Constants.NETWORK_BASE_UR_HEAD_KEY;

/**
 * 常量类
 */
public class Constants {

    /**
     * xml的key集合
     */
    // 将未提交成功的property存储在xml中的key
    public static final String XML_KEY_UNDONE_PROPERTY = "xml_key_undone_property";

    /**
     * 每次请求的最大的数据量
     */
    public static final int LOAD_DATA_ONCE_NUM = 3;

    /**
     * 轮播图播放时间间隔
     */
    public static final int BANNER_INTERVAL = 4000;

    /**
     * 失物类型未选择
     */
    public static final String NO_TYPE_SELECTED = "未选择类型,请先选择类型";

    /**
     * 百度图像识别拍照之后图片存放位置的文件名
     * 具体位置为  sdcard/Android/data/com.neuwljs.wallsmalltwo/cache/fragment_d_b_b_image.jpg"
     */
    public static final String PHOTO_FILE_NAME = "fragment_d_b_b_image.jpg";

    /**
     * 在百度识别时相机打开失败的提示
     */
    public static final String TAKE_PHOTO_FAILED = "拍照失败";

    /**
     * 在百度识别时相册打开失败的提示
     */
    public static final String CHOOSE_PHOTO_FAILED = "选取照片失败";

    /**
     * 失物的owner和publisher信息获取失败时显示的信息
     */
    public static final String OWNER_NAME_DEFAULT = "获取失败";
    public static final String OWNER_ID_DEFAULT = "0";
    public static final String OWNER_COLLEGE_DEFAULT = "获取失败";
    public static final String PUBLISHER_NAME_DEFAULT = "墙小二";
    public static final int NAME_MAX_LENGTH = 4;
    public static final String OWNER_NAME_LIMITED = "名字长度超出限制，不应超过4个字";
    public static final int ID_MAX_LENGTH = 8;
    public static final String OWNER_ID_LIMITED = "学号应该是8位纯数字";
    public static final int COLLEGE_MAX_LENGTH = 10;
    public static final String OWNER_COLLEGE_LIMITED = "学院长度超出限制，不应超过8个字";

    /**
     * 失物上传失败，保存
     */
    public static final String PROPERTY_IS_ALREADY_SAVED = "已保存,可在 “我的” 中查看";

    /**
     * 请求头和baseUrl的映射集合
     * 每个key对应一个baseUrl,动态更改的时候使用
     */
    public static final Map<String, String> OKHTTP_HEAD_MAP;

    /**
     * 动态更改baseurl使用的头的key
     * 这个是全局常量
     */
    public static final String OKHTTP_HEAD_NAME = NETWORK_BASE_UR_HEAD_KEY;

    /**
     * 百度OCR的AK
     */
    public static final String OCR_AK = "Fww20HZOlhRhNuvQET6jDSQt";

    /**
     * 百度OCR的SK
     */
    public static final String OCR_SK = "ylXMmGC4VMC90zwsBwDpQ68EmzbjulUE";

    /**
     * 百度OCR的GRANT_TYPE
     */
    public static final String GRANT_TYPE = "client_credentials";

    /**
     * 百度OCR请求头中的Content-Type的key
     */
    public static final String CONTENT_TYPE_KEY = "Content-Type";

    /**
     * 百度OCR请求头中的Content-Type的value
     */
    public static final String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

    /**
     * 百度OCR的head的key
     */
    public static final String OCR_HEAD_KEY="ocr";

    /**
     * 百度OCR的baseUrl
     */
    public static final String OCR_BASE_URL = "https://aip.baidubce.com/";

    /**
     * 失物招领服务器的head的key
     */
    public static final String LOST_AND_FOUND_HEAD_KEY="lost_and_found";

    /**
     * 失物招领服务器的baseUrl
     * 所有用到的baseurl与头的映射都要放进这个map中
     */
    public static final String LOST_AND_FOUND_BASE_URL = "https://www.neuwljs.cn/";

    /**
     * 请求lost列表时候api中的lost字段，目前无实际意义
     */
    public static final String LOST_FIELD_DEFAULT = "lost_field_default";

    static {
        OKHTTP_HEAD_MAP = new HashMap<> ();
        OKHTTP_HEAD_MAP.put (OCR_HEAD_KEY, OCR_BASE_URL);
        OKHTTP_HEAD_MAP.put (LOST_AND_FOUND_HEAD_KEY, LOST_AND_FOUND_BASE_URL);
    }
}
