package cn.stylefeng.guns.modular.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

//课程表
@TableName("db_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer courseType;//课程类型 1精品班 2管理班 3金融班 4地产班 5其它班
    private String courseName;//课程名称
    private String courseImage;//课程封面
    private Integer price;//价格
    private String courseLength;//学制时长
    private String courseDesc;//课程详情
    private String descImages;//小程序详情图片多个用,分割
    private String startTime;//开课时间
    private String courseAddr;//上课地址
    private String descUrl;//招生简章下载地址
    private String joinUrl;//报名表下载地址
    private Integer status;//0删除 1正常
    private Date createTime;//添加时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCourseAddr() {
        return courseAddr;
    }

    public void setCourseAddr(String courseAddr) {
        this.courseAddr = courseAddr;
    }

    public String getDescUrl() {
        return descUrl;
    }

    public void setDescUrl(String descUrl) {
        this.descUrl = descUrl;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
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

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
    }

    public String getDescImages() {
        return descImages;
    }

    public void setDescImages(String descImages) {
        this.descImages = descImages;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseType=" + courseType +
                ", courseName='" + courseName + '\'' +
                ", courseImage=" + courseImage +
                ", price=" + price +
                ", courseDesc='" + courseDesc + '\'' +
                ", startTime='" + startTime + '\'' +
                ", courseAddr='" + courseAddr + '\'' +
                ", descUrl='" + descUrl + '\'' +
                ", joinUrl='" + joinUrl + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
