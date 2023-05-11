package com.ruoyi.system.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import ch.qos.logback.core.util.TimeUtil;
import com.ruoyi.common.constant.EpacConstant;
import com.ruoyi.common.utils.CurrentTimeUtil;
import com.ruoyi.system.domain.OutApproval;
import com.ruoyi.system.mapper.EpacSuppliesMapper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.EpacSuppliesApplyMapper;
import com.ruoyi.system.domain.EpacSuppliesApply;
import com.ruoyi.system.service.IEpacSuppliesApplyService;

/**
 * 物资审核Service业务层处理
 *
 * @author Liyu
 * @date 2023-03-01
 */
@Service
public class EpacSuppliesApplyServiceImpl implements IEpacSuppliesApplyService
{
    @Autowired
    private EpacSuppliesApplyMapper epacSuppliesApplyMapper;

    @Autowired
    private EpacSuppliesMapper epacSuppliesMapper;
    /**
     * 查询物资审核
     *
     * @param itemApplyId 物资审核主键
     * @return 物资审核
     */
    @Override
    public EpacSuppliesApply selectEpacSuppliesApplyByItemApplyId(Long itemApplyId)
    {
        return epacSuppliesApplyMapper.selectEpacSuppliesApplyByItemApplyId(itemApplyId);
    }

    /**
     * 查询物资审核列表
     *
     * @param epacSuppliesApply 物资审核
     * @return 物资审核
     */
    @Override
    public List<EpacSuppliesApply> selectEpacSuppliesApplyList(EpacSuppliesApply epacSuppliesApply)
    {
        return epacSuppliesApplyMapper.selectEpacSuppliesApplyList(epacSuppliesApply);
    }

    /**
     * 批量审核通过
     *
     * @param ids 审核通过的id数组
     * @return 结果
     */
    @Override
    public void handleItemApply(Long[] ids,int tag)
    {
        if(tag == EpacConstant.OUT_APPLY_TAG_ADOPT) {
            for (int i = 0; i < ids.length; i++) {
                EpacSuppliesApply itemApply = new EpacSuppliesApply();
                itemApply.setItemApplyId(ids[i]);
                itemApply.setItemApplyStatus(EpacConstant.OUT_APPLY_STATUS_ADOPT);
                try {
                    itemApply.setItemApplyStatusTime(CurrentTimeUtil.StringToDate(CurrentTimeUtil.getCurrentTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                epacSuppliesApplyMapper.updateEpacSuppliesApply(itemApply);
            }
        }
        if(tag == EpacConstant.OUT_APPLY_TAG_REJECT){
            for (int i = 0; i < ids.length; i++) {
                EpacSuppliesApply itemApply = new EpacSuppliesApply();
                itemApply.setItemApplyId(ids[i]);
                itemApply.setItemApplyStatus(EpacConstant.OUT_APPLY_STATUS_REJECT);
                try {
                    itemApply.setItemApplyStatusTime(CurrentTimeUtil.StringToDate(CurrentTimeUtil.getCurrentTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                epacSuppliesApplyMapper.updateEpacSuppliesApply(itemApply);
            }
        }
    }

    /**
     * 新增物资审核
     *
     * @param epacSuppliesApply 物资审核
     * @return 结果
     */
    @Override
    public int insertEpacSuppliesApply(EpacSuppliesApply epacSuppliesApply)
    {
        String currentTime = CurrentTimeUtil.getCurrentTime();
        try {
            Date time = CurrentTimeUtil.StringToDate(currentTime);
            epacSuppliesApply.setItemApplyTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        epacSuppliesApply.setItemApplyStatus(EpacConstant.ITEM_APPLY_STATUS_AUDIT);
        epacSuppliesApply.setItemApplyUnit(epacSuppliesMapper.getUnitByitem(epacSuppliesApply.getItemApplyName()));
        return epacSuppliesApplyMapper.insertEpacSuppliesApply(epacSuppliesApply);
    }

    /**
     * 修改物资审核
     *
     * @param epacSuppliesApply 物资审核
     * @return 结果
     */
    @Override
    public int updateEpacSuppliesApply(EpacSuppliesApply epacSuppliesApply)
    {
        return epacSuppliesApplyMapper.updateEpacSuppliesApply(epacSuppliesApply);
    }

    /**
     * 批量删除物资审核
     *
     * @param itemApplyIds 需要删除的物资审核主键
     * @return 结果
     */
    @Override
    public int deleteEpacSuppliesApplyByItemApplyIds(Long[] itemApplyIds)
    {
        return epacSuppliesApplyMapper.deleteEpacSuppliesApplyByItemApplyIds(itemApplyIds);
    }

    /**
     * 删除物资审核信息
     *
     * @param itemApplyId 物资审核主键
     * @return 结果
     */
    @Override
    public int deleteEpacSuppliesApplyByItemApplyId(Long itemApplyId)
    {
        return epacSuppliesApplyMapper.deleteEpacSuppliesApplyByItemApplyId(itemApplyId);
    }
}
