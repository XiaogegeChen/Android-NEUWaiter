package com.neuwljs.wallsmalltwo.model;

public class Constant {

    private Constant(){
    }

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
    public static final String NO_TYPE_SELECTED = "未选择类型";

    /**
     * 从SharedPreferences中字符串,如果没取到默认是null
     */
    public static final String XML_DEFAULT_VALUE = null;
}
