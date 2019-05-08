package cn.neuwljs.module_d.model.event;

/**
 * 通知FragmentDBC更新物品描述的消息事件
 * 由{@link cn.neuwljs.module_d.view.impl.FragmentDBB}发出，
 * {@link cn.neuwljs.module_d.view.impl.FragmentDBC}j接收
 */
public class NotifyFragmentDBCRefreshPropertyInformationEvent {

    /**
     * 失物的详细描述
     */
    private String information;

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
