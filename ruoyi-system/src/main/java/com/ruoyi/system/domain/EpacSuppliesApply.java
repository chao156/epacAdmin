package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物资审核对象 epac_supplies_apply
 *
 * @author Liyu
 * @date 2023-03-01
 */
public class EpacSuppliesApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long itemApplyId;

    @Excel(name = "申请人姓名")
    private String userName;

    /** 申请物资类别 */
    @Excel(name = "申请物资类别")
    private String itemApplyType;

    /** 申请物资名称 */
    @Excel(name = "申请物资名称")
    private String itemApplyName;

    /** 申请物资单位 */
    @Excel(name = "申请物资单位")
    private String itemApplyUnit;

    /** 申请物资数量 */
    @Excel(name = "申请物资数量")
    private String itemApplyAmount;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String itemApplyLocal;

    /** 物资申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "物资申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date itemApplyTime;

    /** 当前申请状态 */
    @Excel(name = "当前申请状态")
    private String itemApplyStatus;

    /** 状态修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "状态修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date itemApplyStatusTime;

    /** 驳回原因 */
    @Excel(name = "驳回原因")
    private String itemApplyRemark;

    /** 所在街道 */
    @Excel(name = "所在街道")
    private String itemApplyTown;

    /** 所在社区 */
    @Excel(name = "所在社区")
    private String itemApplyComm;

    public void setItemApplyId(Long itemApplyId)
    {
        this.itemApplyId = itemApplyId;
    }

    public Long getItemApplyId()
    {
        return itemApplyId;
    }
    public void setItemApplyType(String itemApplyType)
    {
        this.itemApplyType = itemApplyType;
    }

    public String getItemApplyType()
    {
        return itemApplyType;
    }
    public void setItemApplyName(String itemApplyName)
    {
        this.itemApplyName = itemApplyName;
    }

    public String getItemApplyName()
    {
        return itemApplyName;
    }
    public void setItemApplyUnit(String itemApplyUnit)
    {
        this.itemApplyUnit = itemApplyUnit;
    }

    public String getItemApplyUnit()
    {
        return itemApplyUnit;
    }
    public void setItemApplyAmount(String itemApplyAmount)
    {
        this.itemApplyAmount = itemApplyAmount;
    }

    public String getItemApplyAmount()
    {
        return itemApplyAmount;
    }
    public void setItemApplyLocal(String itemApplyLocal)
    {
        this.itemApplyLocal = itemApplyLocal;
    }

    public String getItemApplyLocal()
    {
        return itemApplyLocal;
    }
    public void setItemApplyTime(Date itemApplyTime)
    {
        this.itemApplyTime = itemApplyTime;
    }

    public Date getItemApplyTime()
    {
        return itemApplyTime;
    }
    public void setItemApplyStatus(String itemApplyStatus)
    {
        this.itemApplyStatus = itemApplyStatus;
    }

    public String getItemApplyStatus()
    {
        return itemApplyStatus;
    }
    public void setItemApplyStatusTime(Date itemApplyStatusTime)
    {
        this.itemApplyStatusTime = itemApplyStatusTime;
    }

    public Date getItemApplyStatusTime()
    {
        return itemApplyStatusTime;
    }
    public void setItemApplyRemark(String itemApplyRemark)
    {
        this.itemApplyRemark = itemApplyRemark;
    }

    public String getItemApplyRemark()
    {
        return itemApplyRemark;
    }
    public void setItemApplyTown(String itemApplyTown)
    {
        this.itemApplyTown = itemApplyTown;
    }

    public String getItemApplyTown()
    {
        return itemApplyTown;
    }
    public void setItemApplyComm(String itemApplyComm)
    {
        this.itemApplyComm = itemApplyComm;
    }

    public String getItemApplyComm()
    {
        return itemApplyComm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemApplyId", getItemApplyId())
            .append("itemApplyType", getItemApplyType())
            .append("itemApplyName", getItemApplyName())
            .append("itemApplyUnit", getItemApplyUnit())
            .append("itemApplyAmount", getItemApplyAmount())
            .append("itemApplyLocal", getItemApplyLocal())
            .append("itemApplyTime", getItemApplyTime())
            .append("itemApplyStatus", getItemApplyStatus())
            .append("itemApplyStatusTime", getItemApplyStatusTime())
            .append("itemApplyRemark", getItemApplyRemark())
            .append("itemApplyTown", getItemApplyTown())
            .append("itemApplyComm", getItemApplyComm())
            .toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
