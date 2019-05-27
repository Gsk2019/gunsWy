package cn.stylefeng.guns.modular.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

//课程的课表
@TableName("db_timetable")
public class TimeTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer courseId;//课程id
    private String tableName;//课表名称
    private String tableSummary;//课表简介
    private String tableImages;//课表详情图片 小程序用
    private String tableDesc;//课表图文详情
    private Integer status;//0删除 1正常
    private Integer site;//1北清中心 2北清在线
    private Date createTime;//添加时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSummary() {
        return tableSummary;
    }

    public void setTableSummary(String tableSummary) {
        this.tableSummary = tableSummary;
    }

    public String getTableImages() {
        return tableImages;
    }

    public void setTableImages(String tableImages) {
        this.tableImages = tableImages;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", tableName='" + tableName + '\'' +
                ", tableSummary='" + tableSummary + '\'' +
                ", tableImages='" + tableImages + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                ", status=" + status +
                ", site=" + site +
                ", createTime=" + createTime +
                '}';
    }
}
