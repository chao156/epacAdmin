package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NucleicCollectionAreaMapper;
import com.ruoyi.system.domain.NucleicCollectionArea;
import com.ruoyi.system.service.INucleicCollectionAreaService;

/**
 * 核酸点管理Service业务层处理
 *
 * @author Liyu
 * @date 2022-11-25
 */
@Service
public class NucleicCollectionAreaServiceImpl implements INucleicCollectionAreaService
{
    @Autowired
    private NucleicCollectionAreaMapper nucleicCollectionAreaMapper;

    /**
     * 查询核酸点管理
     *
     * @param ncaId 核酸点管理主键
     * @return 核酸点管理
     */
    @Override
    public NucleicCollectionArea selectNucleicCollectionAreaByNcaId(Long ncaId)
    {
        return nucleicCollectionAreaMapper.selectNucleicCollectionAreaByNcaId(ncaId);
    }

    public List<Map> getCollectionList(){
        return nucleicCollectionAreaMapper.getCollectionList();
    }

    /**
     * 获取核酸监测点经纬度坐标
     * @return 经纬度坐标列表
     */
    @Override
    public List<NucleicCollectionArea> getPosition(){
        List<NucleicCollectionArea> positionList = nucleicCollectionAreaMapper.getPosition();
        return positionList;
    }

    /**
     * 查询核酸点管理列表
     *
     * @param nucleicCollectionArea 核酸点管理
     * @return 核酸点管理
     */
    @Override
    public List<NucleicCollectionArea> selectNucleicCollectionAreaList(NucleicCollectionArea nucleicCollectionArea)
    {
        return nucleicCollectionAreaMapper.selectNucleicCollectionAreaList(nucleicCollectionArea);
    }

    /**
     * 新增核酸点管理
     *
     * @param nucleicCollectionArea 核酸点管理
     * @return 结果
     */
    @Override
    public int insertNucleicCollectionArea(NucleicCollectionArea nucleicCollectionArea)
    {
        nucleicCollectionArea.setCreateTime(DateUtils.getNowDate());
        return nucleicCollectionAreaMapper.insertNucleicCollectionArea(nucleicCollectionArea);
    }

    /**
     * 修改核酸点管理
     *
     * @param nucleicCollectionArea 核酸点管理
     * @return 结果
     */
    @Override
    public int updateNucleicCollectionArea(NucleicCollectionArea nucleicCollectionArea)
    {
        return nucleicCollectionAreaMapper.updateNucleicCollectionArea(nucleicCollectionArea);
    }

    /**
     * 批量删除核酸点管理
     *
     * @param ncaIds 需要删除的核酸点管理主键
     * @return 结果
     */
    @Override
    public int deleteNucleicCollectionAreaByNcaIds(Long[] ncaIds)
    {
        return nucleicCollectionAreaMapper.deleteNucleicCollectionAreaByNcaIds(ncaIds);
    }

    /**
     * 删除核酸点管理信息
     *
     * @param ncaId 核酸点管理主键
     * @return 结果
     */
    @Override
    public int deleteNucleicCollectionAreaByNcaId(Long ncaId)
    {
        return nucleicCollectionAreaMapper.deleteNucleicCollectionAreaByNcaId(ncaId);
    }
}
