package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 社区管理对象 sys_comm
 * 
 * @author Liyu
 * @date 2022-11-14
 */
public class SysComm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键、社区编号 */
    private Long commId;

    /** 社区编号，与地区表中地区编号相同 */
    @Excel(name = "社区编号，与地区表中地区编号相同")
    private String commCode;

    /** 所属区/县 */
    @Excel(name = "所属区/县")
    private String county;

    /** 所属街道/乡镇 */
    @Excel(name = "所属街道/乡镇")
    private String town;

    /** 社区名 */
    @Excel(name = "社区名")
    private String commName;

    /** 社区人数 */
    @Excel(name = "社区人数")
    private String commNum;

    /** 社区状态：开发中、0；使用中、1 */
    @Excel(name = "社区状态：开发中、0；使用中、1")
    private String commStatus;

    /** 社区封控状态：正常、0；封控、1 */
    @Excel(name = "社区封控状态：正常、0；封控、1")
    private String commContStatus;

    public void setCommId(Long commId) 
    {
        this.commId = commId;
    }

    public Long getCommId() 
    {
        return commId;
    }
    public void setCommCode(String commCode) 
    {
        this.commCode = commCode;
    }

    public String getCommCode() 
    {
        return commCode;
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
    public void setCommName(String commName) 
    {
        this.commName = commName;
    }

    public String getCommName() 
    {
        return commName;
    }
    public void setCommNum(String commNum) 
    {
        this.commNum = commNum;
    }

    public String getCommNum() 
    {
        return commNum;
    }
    public void setCommStatus(String commStatus) 
    {
        this.commStatus = commStatus;
    }

    public String getCommStatus() 
    {
        return commStatus;
    }
    public void setCommContStatus(String commContStatus) 
    {
        this.commContStatus = commContStatus;
    }

    public String getCommContStatus() 
    {
        return commContStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("commId", getCommId())
            .append("commCode", getCommCode())
            .append("county", getCounty())
            .append("town", getTown())
            .append("commName", getCommName())
            .append("commNum", getCommNum())
            .append("commStatus", getCommStatus())
            .append("commContStatus", getCommContStatus())
            .toString();
    }
}
