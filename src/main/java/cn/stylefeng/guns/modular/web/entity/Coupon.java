package cn.stylefeng.guns.modular.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

//优惠券

@TableName("db_coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String couponName;//优惠券名称
    private Integer couponCount;//优惠券数量
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private Integer couponMoney;//优惠券金额
    private String couponDesc;//使用说明
    private Integer status;//0删除 1正常
    private Integer site;//0删除 1正常
    private Date createTime;//添加时间
    private Date updateTime;//修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Integer couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", couponName='" + couponName + '\'' +
                ", couponCount=" + couponCount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", couponMoney=" + couponMoney +
                ", couponDesc='" + couponDesc + '\'' +
                ", status=" + status +
                ", site=" + site +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}