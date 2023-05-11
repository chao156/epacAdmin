package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysRegion;

/**
 * 地区管理Mapper接口
 *
 * @author Liyu
 * @date 2022-11-13
 */
public interface SysRegionMapper
{
    /**
     * 查询地区管理
     *
     * @param regionId 地区管理主键
     * @return 地区管理
     */
    public SysRegion selectSysRegionByRegionId(Long regionId);

    /**
     * 查询地区管理列表
     *
     * @param sysRegion 地区管理
     * @return 地区管理集合
     */
    public List<SysRegion> selectSysRegionList(SysRegion sysRegion);

    /**
     * 新增地区管理
     *
     * @param sysRegion 地区管理
     * @return 结果
     */
    public int insertSysRegion(SysRegion sysRegion);

    public String getCountyName();

    /**
     * 修改地区管理
     *
     * @param sysRegion 地区管理
     * @return 结果
     */
    public int updateSysRegion(SysRegion sysRegion);

    /**
     * 从地区信息表中查询出社区信息
     * @return
     */
    public List<SysRegion> getCommInfoFromRegion();

    /**
     * 删除地区管理
     *
     * @param regionId 地区管理主键
     * @return 结果
     */
    public int deleteSysRegionByRegionId(Long regionId);

    /**
     * 批量删除地区管理
     *
     * @param regionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysRegionByRegionIds(Long[] regionIds);

    /**
     * 根据地区编号查询地区信息
     * @param regionCode
     * @return
     */
    public SysRegion selectSysRegionByRegionCode(String regionCode);

    public List<SysRegion> getTownList();

    public List<SysRegion> getCommList(String townId);
}
