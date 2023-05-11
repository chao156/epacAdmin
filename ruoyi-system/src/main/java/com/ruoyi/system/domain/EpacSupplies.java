package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物资管理对象 epac_supplies
 * 
 * @author Liyu
 * @date 2023-02-01
 */
public class EpacSupplies extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long itemId;

    /** 物资类别（0：生活物资、1：防疫物资） */
    @Excel(name = "物资类别", readConverterExp = "0=：生活物资、1：防疫物资")
    private String itemType;

    /** 物资名称 */
    @Excel(name = "物资名称")
    private String itemName;

    /** 物资单位（吨、桶等单位） */
    @Excel(name = "物资单位", readConverterExp = "吨=、桶等单位")
    private String itemUnit;

    /** 物资数量 */
    @Excel(name = "物资数量")
    private String itemAmount;

    /** 存放地址 */
    @Excel(name = "存放地址")
    private String storagePlace;

    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warehousingTime;

    /** 出库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryTime;

    /** 来源 */
    @Excel(name = "来源")
    private String source;

    /** 出库去向 */
    @Excel(name = "出库去向")
    private String deliveryDestination;

    /** 物品状态 */
    @Excel(name = "物品状态")
    private String status;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 维度 */
    @Excel(name = "维度")
    private String latitude;

    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }
    public void setItemType(String itemType) 
    {
        this.itemType = itemType;
    }

    public String getItemType() 
    {
        return itemType;
    }
    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }
    public void setItemUnit(String itemUnit) 
    {
        this.itemUnit = itemUnit;
    }

    public String getItemUnit() 
    {
        return itemUnit;
    }
    public void setItemAmount(String itemAmount) 
    {
        this.itemAmount = itemAmount;
    }

    public String getItemAmount() 
    {
        return itemAmount;
    }
    public void setStoragePlace(String storagePlace) 
    {
        this.storagePlace = storagePlace;
    }

    public String getStoragePlace() 
    {
        return storagePlace;
    }
    public void setWarehousingTime(Date warehousingTime) 
    {
        this.warehousingTime = warehousingTime;
    }

    public Date getWarehousingTime() 
    {
        return warehousingTime;
    }
    public void setDeliveryTime(Date deliveryTime) 
    {
        this.deliveryTime = deliveryTime;
    }

    public Date getDeliveryTime() 
    {
        return deliveryTime;
    }
    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getSource() 
    {
        return source;
    }
    public void setDeliveryDestination(String deliveryDestination) 
    {
        this.deliveryDestination = deliveryDestination;
    }

    public String getDeliveryDestination() 
    {
        return deliveryDestination;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemId", getItemId())
            .append("itemType", getItemType())
            .append("itemName", getItemName())
            .append("itemUnit", getItemUnit())
            .append("itemAmount", getItemAmount())
            .append("storagePlace", getStoragePlace())
            .append("warehousingTime", getWarehousingTime())
            .append("deliveryTime", getDeliveryTime())
            .append("source", getSource())
            .append("deliveryDestination", getDeliveryDestination())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .toString();
    }
}
