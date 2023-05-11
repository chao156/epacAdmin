package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.constant.EpacConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.OutApprovalMapper;
import com.ruoyi.system.domain.OutApproval;
import com.ruoyi.system.service.IOutApprovalService;

/**
 * 外出审核Service业务层处理
 *
 * @author Liyu
 * @date 2023-02-02
 */
@Service
public class OutApprovalServiceImpl implements IOutApprovalService
{
    @Autowired
    private OutApprovalMapper outApprovalMapper;

    /**
     * 查询外出审核
     *
     * @param outId 外出审核主键
     * @return 外出审核
     */
    @Override
    public OutApproval selectOutApprovalByOutId(Long outId)
    {
        return outApprovalMapper.selectOutApprovalByOutId(outId);
    }

    /**
     * 查询外出审核列表
     *
     * @param outApproval 外出审核
     * @return 外出审核
     */
    @Override
    public List<OutApproval> selectOutApprovalList(OutApproval outApproval)
    {
        return outApprovalMapper.selectOutApprovalList(outApproval);
    }

    /**
     * 批量审核通过
     *
     * @param ids 审核通过的id数组
     * @return 结果
     */
    @Override
    public void handleApply(Long[] ids,int tag)
    {
        if(tag == EpacConstant.OUT_APPLY_TAG_ADOPT) {
            for (int i = 0; i < ids.length; i++) {
                OutApproval outApproval = new OutApproval();
                outApproval.setOutId(ids[i]);
                outApproval.setStatus(EpacConstant.OUT_APPLY_STATUS_ADOPT);
                outApprovalMapper.updateOutApproval(outApproval);
            }
        }
        if(tag == EpacConstant.OUT_APPLY_TAG_REJECT){
            for (int i = 0; i < ids.length; i++) {
                OutApproval outApproval = new OutApproval();
                outApproval.setOutId(ids[i]);
                outApproval.setStatus(EpacConstant.OUT_APPLY_STATUS_REJECT);
                outApprovalMapper.updateOutApproval(outApproval);
            }
        }
        //return outApprovalMapper.updateOutApproval(outApproval);
    }

    /**
     * 新增外出审核
     *
     * @param outApproval 外出审核
     * @return 结果
     */
    @Override
    public int insertOutApproval(OutApproval outApproval)
    {
        return outApprovalMapper.insertOutApproval(outApproval);
    }

    /**
     * 修改外出审核
     *
     * @param outApproval 外出审核
     * @return 结果
     */
    @Override
    public int updateOutApproval(OutApproval outApproval)
    {
        return outApprovalMapper.updateOutApproval(outApproval);
    }

    /**
     * 批量删除外出审核
     *
     * @param outIds 需要删除的外出审核主键
     * @return 结果
     */
    @Override
    public int deleteOutApprovalByOutIds(Long[] outIds)
    {
        return outApprovalMapper.deleteOutApprovalByOutIds(outIds);
    }

    /**
     * 删除外出审核信息
     *
     * @param outId 外出审核主键
     * @return 结果
     */
    @Override
    public int deleteOutApprovalByOutId(Long outId)
    {
        return outApprovalMapper.deleteOutApprovalByOutId(outId);
    }
}
