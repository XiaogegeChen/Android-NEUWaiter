package com.neuwljs.wallsmalltwo.model;

public class Constant {

    /**
     * 文本  “您还可以 去查看 | 继续发布”  中“去查看”和“继续发布”的位置
     */
    public static final int FRAGMENT_D_B_D_TEXT_1_START = 4;
    public static final int FRAGMENT_D_B_D_TEXT_1_EDN = 8;
    public static final int FRAGMENT_D_B_D_TEXT_2_START = 10;
    public static final int FRAGMENT_D_B_D_TEXT_2_EDN = 15;

    /**
     * 获取权限失败
     */
    public static final String NO_PERMISSION = "获取权限失败,部分功能无法使用";

    /**
     * 失物类型未选择
     */
    public static final String NO_TYPE_SELECTED = "未选择类型,请先选择类型";

    /**
     * 失物类型未选择
     */
    public static final String PROPERTY_IS_ALREADY_SAVED = "已保存,可在 “我的” 中查看";

    /**
     * 拍照之后图片存放位置的文件名
     * 具体位置为  sdcard/Android/data/com.neuwljs.wallsmalltwo/cache/fragment_d_b_b_image.jpg"
     */
    public static final String PHOTO_FILE_NAME = "fragment_d_b_b_image.jpg";

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
    public static final int COLLEGE_MAX_LENGTH = 8;
    public static final String OWNER_COLLEGE_LIMITED = "学院长度超出限制，不应超过8个字";

    /**
     * 轮播图播放时间间隔
     */
    public static final int BANNER_INTERVAL = 4000;

    /**
     * 每次请求的最大的数据量
     */
    public static final int LOAD_DATA_ONCE_NUM = 3;

    public static class XmlConstants{
        /**
         * 从SharedPreferences中字符串,如果没取到默认是null
         */
        public static final String XML_DEFAULT_VALUE_STRING = null;

        /**
         * 从SharedPreferences中取long,如果没取到默认是null
         */
        public static final long XML_DEFAULT_VALUE_LONG = 0;

        /**
         * xml里面access_token的key
         */
        public static final String XML_ACCESS_TOKEN_KEY = "xml_access_token_key";

        /**
         * xml里面access_token第一次请求到时间的key
         */
        public static final String XML_ACCESS_TOKEN_TIME_KEY = "xml_access_token_time_key";

        /**
         * 将未提交成功的property存储在xml中的key
         */
        public static final String XML_UNDONE_PROPERTY_KEY = "xml_undone_property_key";
    }
}
