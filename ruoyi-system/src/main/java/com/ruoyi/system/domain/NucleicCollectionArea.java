package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 核酸点管理对象 nucleic_collection_area
 *
 * @author Liyu
 * @date 2022-11-25
 */
public class NucleicCollectionArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long ncaId;

    /** 区/县 */
    @Excel(name = "区/县")
    private String county;

    /** 街道/镇 */
    @Excel(name = "街道/镇")
    private String town;

    /** 社区/村 */
    @Excel(name = "社区/村")
    private String community;

    /** 具体位置 */
    @Excel(name = "具体位置")
    private String address;

    /** 经度 */
    @Excel(name = "经度")
    private String logitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 检测开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检测开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ncStartTime;

    /** 检测结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检测结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ncEndTime;

    /** 核酸点状态 */
    @Excel(name = "检测点状态")
    private String ncStatus;

    /** 排队人数 */
    @Excel(name = "排队人数")
    private String queueNumber;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creator;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /** 修改人 */
    @Excel(name = "修改人")
    private String modifier;

    public void setNcaId(Long ncaId)
    {
        this.ncaId = ncaId;
    }

    public Long getNcaId()
    {
        return ncaId;
    }
    public void setCounty(String county)
    {
        this.county = county;
    }

    public String getCounty()
    {
        return county;
    }
    public void setTown(String town)
    {
        this.town = town;
    }

    public String getTown()
    {
        return town;
    }
    public void setCommunity(String community)
    {
        this.community = community;
    }

    public String getCommunity()
    {
        return community;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setLogitude(String logitude)
    {
        this.logitude = logitude;
    }

    public String getLogitude()
    {
        return logitude;
    }
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLatitude()
    {
        return latitude;
    }
    public void setNcStartTime(Date ncStartTime)
    {
        this.ncStartTime = ncStartTime;
    }

    public Date getNcStartTime()
    {
        return ncStartTime;
    }
    public void setNcEndTime(Date ncEndTime)
    {
        this.ncEndTime = ncEndTime;
    }

    public Date getNcEndTime()
    {
        return ncEndTime;
    }
    public void setNcStatus(String ncStatus)
    {
        this.ncStatus = ncStatus;
    }

    public String getNcStatus()
    {
        return ncStatus;
    }
    public void setQueueNumber(String queueNumber)
    {
        this.queueNumber = queueNumber;
    }

    public String getQueueNumber()
    {
        return queueNumber;
    }
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }
    public void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime()
    {
        return modifyTime;
    }
    public void setModifier(String modifier)
    {
        this.modifier = modifier;
    }

    public String getModifier()
    {
        return modifier;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ncaId", getNcaId())
            .append("county", getCounty())
            .append("town", getTown())
            .append("community", getCommunity())
            .append("address", getAddress())
            .append("logitude", getLogitude())
            .append("latitude", getLatitude())
            .append("ncStartTime", getNcStartTime())
            .append("ncEndTime", getNcEndTime())
            .append("ncStatus", getNcStatus())
            .append("queueNumber", getQueueNumber())
            .append("createTime", getCreateTime())
            .append("creator", getCreator())
            .append("modifyTime", getModifyTime())
            .append("modifier", getModifier())
            .toString();
    }
}
