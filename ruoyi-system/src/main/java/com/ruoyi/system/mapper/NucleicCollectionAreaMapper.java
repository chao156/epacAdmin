package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.NucleicCollectionArea;

/**
 * 核酸点管理Mapper接口
 *
 * @author Liyu
 * @date 2022-11-25
 */
public interface NucleicCollectionAreaMapper
{
    /**
     * 查询核酸点管理
     *
     * @param ncaId 核酸点管理主键
     * @return 核酸点管理
     */
    public NucleicCollectionArea selectNucleicCollectionAreaByNcaId(Long ncaId);

    public List<Map> getCollectionList();

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
     * 删除核酸点管理
     *
     * @param ncaId 核酸点管理主键
     * @return 结果
     */
    public int deleteNucleicCollectionAreaByNcaId(Long ncaId);

    /**
     * 批量删除核酸点管理
     *
     * @param ncaIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNucleicCollectionAreaByNcaIds(Long[] ncaIds);
}
