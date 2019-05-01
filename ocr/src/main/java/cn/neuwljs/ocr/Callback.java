package cn.neuwljs.ocr;

/**
 * 图片识别的回调
 * @param <T> 返回值类型
 */
public interface Callback<T> {
    /**
     * 成功
     * @param t 返回值
     */
    void onSuccess(T t);

    /**
     * 失败
     * @param e 含有失败信息的Exception
     */
    void onFailure(OCRException e);
}
