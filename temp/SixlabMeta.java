package cn.sixlab.web.common.bean;import org.nutz.dao.entity.annotation.Table;import org.nutz.dao.entity.annotation.Id;import org.nutz.dao.entity.annotation.Column;@Table("sixlab_meta")
public class SixlabMeta {

    @Column("id")
    private int id;
    @Column("key")
    private String key;
    @Column("value")
    private String value;
    @Column("flag")
    private String flag;
    @Column("remark")
    private String remark;

    public int getId () {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey () {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue () {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlag () {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRemark () {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}