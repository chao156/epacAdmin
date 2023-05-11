package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.OutApproval;

/**
 * 外出审核Service接口
 *
 * @author Liyu
 * @date 2023-02-02
 */
public interface IOutApprovalService
{
    /**
     * 查询外出审核
     *
     * @param outId 外出审核主键
     * @return 外出审核
     */
    public OutApproval selectOutApprovalByOutId(Long outId);

    /**
     * 查询外出审核列表
     *
     * @param outApproval 外出审核
     * @return 外出审核集合
     */
    public List<OutApproval> selectOutApprovalList(OutApproval outApproval);

    public void handleApply(Long[] ids,int tag);

    /**
     * 新增外出审核
     *
     * @param outApproval 外出审核
     * @return 结果
     */
    public int insertOutApproval(OutApproval outApproval);

    /**
     * 修改外出审核
     *
     * @param outApproval 外出审核
     * @return 结果
     */
    public int updateOutApproval(OutApproval outApproval);

    /**
     * 批量删除外出审核
     *
     * @param outIds 需要删除的外出审核主键集合
     * @return 结果
     */
    public int deleteOutApprovalByOutIds(Long[] outIds);

    /**
     * 删除外出审核信息
     *
     * @param outId 外出审核主键
     * @return 结果
     */
    public int deleteOutApprovalByOutId(Long outId);
}
