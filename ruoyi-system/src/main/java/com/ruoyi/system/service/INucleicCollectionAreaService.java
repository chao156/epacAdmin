package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.NucleicCollectionArea;

/**
 * 核酸点管理Service接口
 *
 * @author Liyu
 * @date 2022-11-25
 */
public interface INucleicCollectionAreaService
{
    /**
     * 查询核酸点管理
     *
     * @param ncaId 核酸点管理主键
     * @return 核酸点管理
     */
    public NucleicCollectionArea selectNucleicCollectionAreaByNcaId(Long ncaId);

    /**
     * 获取检测点下拉列表
     * @return
     */
    public List<Map> getCollectionList();

    /**
     * 获取检测点经纬度
     * @return
     */
    public List<NucleicCollectionArea> getPosition();

    /**
     * 查询核酸点管理列表
     *
     * @param nucleicCollectionArea 核酸点管理
     * @return 核酸点管理集合
     */
    public List<NucleicCollectionArea> selectNucleicCollectionAreaList(NucleicCollectionArea nucleicCollectionArea);

    /**
     * 新增核酸点管理
     *
     * @param nucleicCollectionArea 核酸点管理
     * @return 结果
     */
    public int insertNucleicCollectionArea(NucleicCollectionArea nucleicCollectionArea);

    /**
     * 修改核酸点管理
     *
     * @param nucleicCollectionArea 核酸点管理
     * @return 结果
     */
    public int updateNucleicCollectionArea(NucleicCollectionArea nucleicCollectionArea);

    /**
     * 批量删除核酸点管理
     *
     * @param ncaIds 需要删除的核酸点管理主键集合
     * @return 结果
     */
    public int deleteNucleicCollectionAreaByNcaIds(Long[] ncaIds);

    /**
     * 删除核酸点管理信息
     *
     * @param ncaId 核酸点管理主键
     * @return 结果
     */
    public int deleteNucleicCollectionAreaByNcaId(Long ncaId);
}
