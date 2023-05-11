package com.ruoyi.common.core.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 地区管理对象 sys_region
 *
 * @author Liyu
 * @date 2022-11-13
 */
public class SysRegion extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 地区id */
    private Long regionId;

    /** 地区编号 */
    @Excel(name = "地区编号")
    private String regionCode;

    /** 地区父编号 */
    @Excel(name = "地区父编号")
    private String regionParentCode;

    /** 地区名称 */
    @Excel(name = "地区名称")
    private String regionName;

    /** 地区等级（1：区、2：街道、3：社区） */
    @Excel(name = "地区等级")
    private String regionLevel;

    /** 地区领导人 */
    @Excel(name = "地区领导人")
    private String regionLeader;

    /** 状态 */
    @Excel(name = "状态")
    private String regionStatus;

    private String town;

    private List<SysRegion> children = new ArrayList<SysRegion>();



    public void setRegionId(Long regionId)
    {
        this.regionId = regionId;
    }

    public Long getRegionId()
    {
        return regionId;
    }
    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }

    public String getRegionCode()
    {
        return regionCode;
    }
    public void setRegionParentCode(String regionParentCode)
    {
        this.regionParentCode = regionParentCode;
    }

    public String getRegionParentCode()
    {
        return regionParentCode;
    }
    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    public String getRegionName()
    {
        return regionName;
    }
    public void setRegionLevel(String regionLevel)
    {
        this.regionLevel = regionLevel;
    }

    public String getRegionLevel()
    {
        return regionLevel;
    }
    public void setRegionLeader(String regionLeader)
    {
        this.regionLeader = regionLeader;
    }

    public String getRegionLeader()
    {
        return regionLeader;
    }
    public void setRegionStatus(String regionStatus)
    {
        this.regionStatus = regionStatus;
    }

    public String getRegionStatus()
    {
        return regionStatus;
    }

    public List<SysRegion> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = (List<SysRegion>) children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("regionId", getRegionId())
            .append("regionCode", getRegionCode())
            .append("regionParentCode", getRegionParentCode())
            .append("regionName", getRegionName())
            .append("regionLevel", getRegionLevel())
            .append("regionLeader", getRegionLeader())
            .append("regionStatus", getRegionStatus())
            .toString();
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }




}
