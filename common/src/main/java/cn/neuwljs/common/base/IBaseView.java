package cn.neuwljs.common.base;

/**
 * mvp视图基类
 */
public interface IBaseView {
    /**
     * 进度页
     */
    void showProgressPage();

    /**
     * 错误页
     */
    void showErrorPage();

    /**
     * 弹toast
     * @param message 内容
     */
    void showToast(String message);
}
