package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.EpacSuppliesMapper;
import com.ruoyi.system.domain.EpacSupplies;
import com.ruoyi.system.service.IEpacSuppliesService;

/**
 * 物资管理Service业务层处理
 * 
 * @author Liyu
 * @date 2023-02-01
 */
@Service
public class EpacSuppliesServiceImpl implements IEpacSuppliesService 
{
    @Autowired
    private EpacSuppliesMapper epacSuppliesMapper;

    /**
     * 查询物资管理
     * 
     * @param itemId 物资管理主键
     * @return 物资管理
     */
    @Override
    public EpacSupplies selectEpacSuppliesByItemId(Long itemId)
    {
        return epacSuppliesMapper.selectEpacSuppliesByItemId(itemId);
    }

    /**
     * 查询物资管理列表
     * 
     * @param epacSupplies 物资管理
     * @return 物资管理
     */
    @Override
    public List<EpacSupplies> selectEpacSuppliesList(EpacSupplies epacSupplies)
    {
        return epacSuppliesMapper.selectEpacSuppliesList(epacSupplies);
    }

    /**
     * 新增物资管理
     * 
     * @param epacSupplies 物资管理
     * @return 结果
     */
    @Override
    public int insertEpacSupplies(EpacSupplies epacSupplies)
    {
        return epacSuppliesMapper.insertEpacSupplies(epacSupplies);
    }

    /**
     * 修改物资管理
     * 
     * @param epacSupplies 物资管理
     * @return 结果
     */
    @Override
    public int updateEpacSupplies(EpacSupplies epacSupplies)
    {
        return epacSuppliesMapper.updateEpacSupplies(epacSupplies);
    }

    /**
     * 批量删除物资管理
     * 
     * @param itemIds 需要删除的物资管理主键
     * @return 结果
     */
    @Override
    public int deleteEpacSuppliesByItemIds(Long[] itemIds)
    {
        return epacSuppliesMapper.deleteEpacSuppliesByItemIds(itemIds);
    }

    /**
     * 删除物资管理信息
     * 
     * @param itemId 物资管理主键
     * @return 结果
     */
    @Override
    public int deleteEpacSuppliesByItemId(Long itemId)
    {
        return epacSuppliesMapper.deleteEpacSuppliesByItemId(itemId);
    }
}
