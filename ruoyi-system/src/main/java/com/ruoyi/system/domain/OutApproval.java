package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 外出审核对象 out_approval
 *
 * @author Liyu
 * @date 2023-02-02
 */
public class OutApproval extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long outId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String userName;

    /** 所在社区 */
    @Excel(name = "所在社区")
    private String community;

    /** 外出原因 */
    @Excel(name = "外出原因")
    private String outReason;

    /** 外出去向 */
    @Excel(name = "外出去向")
    private String outDestination;

    /** 外出时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Excel(name = "外出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outTime;

    /** 返回时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Excel(name = "返回时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnTime;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private String status;

    /** 凭证（图片存储路径） */
    @Excel(name = "凭证", readConverterExp = "图=片存储路径")
    private String certificate;

    /** 外出类型（0：公务；1、私人） */
    @Excel(name = "外出类型", readConverterExp = "0=：公务；1、私人")
    private String outType;

    public void setOutId(Long outId)
    {
        this.outId = outId;
    }

    public Long getOutId()
    {
        return outId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setCommunity(String community)
    {
        this.community = community;
    }

    public String getCommunity()
    {
        return community;
    }
    public void setOutReason(String outReason)
    {
        this.outReason = outReason;
    }

    public String getOutReason()
    {
        return outReason;
    }
    public void setOutDestination(String outDestination)
    {
        this.outDestination = outDestination;
    }

    public String getOutDestination()
    {
        return outDestination;
    }
    public void setOutTime(Date outTime)
    {
        this.outTime = outTime;
    }

    public Date getOutTime()
    {
        return outTime;
    }
    public void setReturnTime(Date returnTime)
    {
        this.returnTime = returnTime;
    }

    public Date getReturnTime()
    {
        return returnTime;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCertificate(String certificate)
    {
        this.certificate = certificate;
    }

    public String getCertificate()
    {
        return certificate;
    }
    public void setOutType(String outType)
    {
        this.outType = outType;
    }

    public String getOutType()
    {
        return outType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("outId", getOutId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("community", getCommunity())
            .append("outReason", getOutReason())
            .append("outDestination", getOutDestination())
            .append("outTime", getOutTime())
            .append("returnTime", getReturnTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("certificate", getCertificate())
            .append("outType", getOutType())
            .toString();
    }
}
