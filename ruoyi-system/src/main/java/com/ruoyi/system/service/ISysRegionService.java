package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysRegion;

/**
 * 地区管理Service接口
 *
 * @author Liyu
 * @date 2022-11-13
 */
public interface ISysRegionService
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

    /**
     * 修改地区管理
     *
     * @param sysRegion 地区管理
     * @return 结果
     */
    public int updateSysRegion(SysRegion sysRegion);

    /**
     * 批量删除地区管理
     *
     * @param regionIds 需要删除的地区管理主键集合
     * @return 结果
     */
    public int deleteSysRegionByRegionIds(Long[] regionIds);

    /**
     * 删除地区管理信息
     *
     * @param regionId 地区管理主键
     * @return 结果
     */
    public int deleteSysRegionByRegionId(Long regionId);

    String importRegion(List<SysRegion> regionList, Boolean isUpdateSupport, String operName);

    List<TreeSelect> selectRegionTreeList(SysRegion region);

    List<TreeSelect> buildRegionTreeSelect(List<SysRegion> regionList);

    List<SysRegion> buildRegionTree(List<SysRegion> regionList);
}
