package cn.sixlab.web.common.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseTable;

@DatabaseTable(tableName="sixlab_time_line")
public class SixlabTimeLine {

    @DatabaseField(columnName = "id")
    private int id;
    @DatabaseField(columnName = "date")
    private Date date;
    @DatabaseField(columnName = "intro")
    private String intro;
    @DatabaseField(columnName = "remark")
    private String remark;

    public int getId () {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate () {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIntro () {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getRemark () {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}