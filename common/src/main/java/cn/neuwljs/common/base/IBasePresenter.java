package cn.neuwljs.common.base;

/**
 * mvp业务逻辑接口基类
 * @param <T> 视图
 */
public interface IBasePresenter<T extends IBaseView> {
    /**
     * 绑定
     * @param t 待绑定的视图
     */
    void attach(T t);

    /**
     * 解绑
     */
    void detach();
}
