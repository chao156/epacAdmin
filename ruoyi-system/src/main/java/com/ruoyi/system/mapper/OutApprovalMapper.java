package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OutApproval;

/**
 * 外出审核Mapper接口
 * 
 * @author Liyu
 * @date 2023-02-02
 */
public interface OutApprovalMapper 
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
     * 删除外出审核
     * 
     * @param outId 外出审核主键
     * @return 结果
     */
    public int deleteOutApprovalByOutId(Long outId);

    /**
     * 批量删除外出审核
     * 
     * @param outIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOutApprovalByOutIds(Long[] outIds);
}
