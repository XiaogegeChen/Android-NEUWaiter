package cn.neuwljs.module_d.model.base;

/**
 * 失主信息
 */
public class Owner {

    /**
     * 姓名
     */
    private String name;

    /**
     * 学号
     */
    private String id;

    /**
     * 学院
     */
    private String college;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
