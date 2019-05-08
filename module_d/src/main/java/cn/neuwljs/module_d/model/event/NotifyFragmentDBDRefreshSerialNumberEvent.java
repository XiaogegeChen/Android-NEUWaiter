package cn.neuwljs.module_d.model.event;

/**
 * 通知FragmentDBD刷新序列号的消息事件
 * 由{@link cn.neuwljs.module_d.view.impl.FragmentDBC}发出，
 * {@link cn.neuwljs.module_d.view.impl.FragmentDBD}j接收
 */
public class NotifyFragmentDBDRefreshSerialNumberEvent {

    /**
     * 序列号
     */
    private String SerialNumber;

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }
}
