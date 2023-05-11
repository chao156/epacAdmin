package com.ruoyi.web.controller.epac;

import com.ruoyi.common.constant.EpacConstant;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.EpacSuppliesApply;
import com.ruoyi.system.service.IEpacSuppliesApplyService;
import com.ruoyi.system.service.IOutApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.system.domain.OutApproval;

/**
 * 申请控制器
 */
@RestController
@RequestMapping("/system/Applet")
public class EpacAppletController extends BaseController {

    @Autowired
    private IOutApprovalService outApprovalService;

    @Autowired
    private IEpacSuppliesApplyService epacSuppliesApplyService;
    /**
     * 小程序-外出申请，提交审核
     * @param outApproval
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:approval:add')")
    @PostMapping("outApplyCommit")
    public AjaxResult outApplyCommit(@RequestBody OutApproval outApproval){
        outApproval.setStatus(EpacConstant.OUT_APPLY_STATUS_AUDIT);
        return toAjax(outApprovalService.insertOutApproval(outApproval));
    }


    /**
     * 小程序-物资申请，提交审核
     * @param epacSuppliesApply
     * @return
     */
    @PostMapping("itemApplyCommit")
    public AjaxResult itemApplyCommit(@RequestBody EpacSuppliesApply epacSuppliesApply){
        return toAjax(epacSuppliesApplyService.insertEpacSuppliesApply(epacSuppliesApply));
    }
}
