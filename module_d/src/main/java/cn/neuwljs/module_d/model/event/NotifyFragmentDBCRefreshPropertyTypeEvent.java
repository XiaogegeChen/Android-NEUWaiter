package cn.neuwljs.module_d.model.event;

/**
 * 通知FragmentDBC刷新物品类型的消息事件
 * 由{@link cn.neuwljs.module_d.view.impl.FragmentDBA}发出，
 * {@link cn.neuwljs.module_d.view.impl.FragmentDBC}j接收
 */
public class NotifyFragmentDBCRefreshPropertyTypeEvent {
    /**
     * 失物类型
     */
    private String propertyType;

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
