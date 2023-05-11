package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.EpacSupplies;

/**
 * 物资管理Mapper接口
 *
 * @author Liyu
 * @date 2023-02-01
 */
public interface EpacSuppliesMapper
{
    /**
     * 查询物资管理
     *
     * @param itemId 物资管理主键
     * @return 物资管理
     */
    public EpacSupplies selectEpacSuppliesByItemId(Long itemId);

    /**
     * 查询物资管理列表
     *
     * @param epacSupplies 物资管理
     * @return 物资管理集合
     */
    public List<EpacSupplies> selectEpacSuppliesList(EpacSupplies epacSupplies);

    /**
     * 新增物资管理
     *
     * @param epacSupplies 物资管理
     * @return 结果
     */
    public int insertEpacSupplies(EpacSupplies epacSupplies);

    /**
     * 修改物资管理
     *
     * @param epacSupplies 物资管理
     * @return 结果
     */
    public int updateEpacSupplies(EpacSupplies epacSupplies);

    /**
     * 删除物资管理
     *
     * @param itemId 物资管理主键
     * @return 结果
     */
    public int deleteEpacSuppliesByItemId(Long itemId);

    /**
     * 批量删除物资管理
     *
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEpacSuppliesByItemIds(Long[] itemIds);

    public List<EpacSupplies> getItemList();

    public String getUnitByitem(String itemName);
}
