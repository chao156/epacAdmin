package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.EpacSuppliesApply;
import com.ruoyi.system.domain.OutApproval;

/**
 * 物资审核Mapper接口
 *
 * @author Liyu
 * @date 2023-03-01
 */
public interface EpacSuppliesApplyMapper
{

    /**
     * 查询物资审核
     *
     * @param itemApplyId 物资审核主键
     * @return 物资审核
     */
    public EpacSuppliesApply selectEpacSuppliesApplyByItemApplyId(Long itemApplyId);

    /**
     * 查询物资审核列表
     *
     * @param epacSuppliesApply 物资审核
     * @return 物资审核集合
     */
    public List<EpacSuppliesApply> selectEpacSuppliesApplyList(EpacSuppliesApply epacSuppliesApply);

    /**
     * 新增物资审核
     *
     * @param epacSuppliesApply 物资审核
     * @return 结果
     */
    public int insertEpacSuppliesApply(EpacSuppliesApply epacSuppliesApply);

    /**
     * 修改物资审核
     *
     * @param epacSuppliesApply 物资审核
     * @return 结果
     */
    public int updateEpacSuppliesApply(EpacSuppliesApply epacSuppliesApply);

    /**
     * 删除物资审核
     *
     * @param itemApplyId 物资审核主键
     * @return 结果
     */
    public int deleteEpacSuppliesApplyByItemApplyId(Long itemApplyId);

    /**
     * 批量删除物资审核
     *
     * @param itemApplyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEpacSuppliesApplyByItemApplyIds(Long[] itemApplyIds);
}
