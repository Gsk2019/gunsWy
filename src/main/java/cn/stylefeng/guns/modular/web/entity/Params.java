package cn.stylefeng.guns.modular.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

//参数表
@TableName("db_params")
public class Params implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String paramName;//参数名称
    private String paramValue;//参数值
    private Integer isShow;//是否显示 1显示 0不显示
    private Integer paramType;//参数类型 1常规参数 2图片参数
    private Integer status;//0删除 1正常
    private Integer sortNo;//排序值
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

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Params{" +
                "id=" + id +
                ", paramName='" + paramName + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", isShow=" + isShow +
                ", paramType=" + paramType +
                ", status=" + status +
                ", sortNo=" + sortNo +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
