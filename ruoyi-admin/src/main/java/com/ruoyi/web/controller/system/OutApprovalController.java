package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.EpacConstant;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.OutApproval;
import com.ruoyi.system.service.IOutApprovalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外出审核Controller
 *
 * @author Liyu
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/system/approval")
public class OutApprovalController extends BaseController
{
    @Autowired
    private IOutApprovalService outApprovalService;

    /**
     * 查询外出审核列表
     */
    @PreAuthorize("@ss.hasPermi('system:approval:list')")
    @GetMapping("/list")
    public TableDataInfo list(OutApproval outApproval)
    {
        startPage();
        List<OutApproval> list = outApprovalService.selectOutApprovalList(outApproval);
        return getDataTable(list);
    }

    /**
     * 审批通过外出申请
     * @param outIds
     */
    @PreAuthorize("@ss.hasPermi('system:approval:edit')")
    @GetMapping(value = "/handleApply/{outIds}")
    public void handleApply(@PathVariable("outIds") Long[] outIds){
        outApprovalService.handleApply(outIds,EpacConstant.OUT_APPLY_TAG_ADOPT);
    }

    /**
     * 审批驳回外出申请
     * @param outIds
     */
    @PreAuthorize("@ss.hasPermi('system:approval:edit')")
    @GetMapping(value = "/handleReject/{outIds}")
    public void handleReject(@PathVariable("outIds") Long[] outIds){
        outApprovalService.handleApply(outIds,EpacConstant.OUT_APPLY_TAG_REJECT);
    }


    /**
     * 导出外出审核列表
     */
    @PreAuthorize("@ss.hasPermi('system:approval:export')")
    @Log(title = "外出审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OutApproval outApproval)
    {
        List<OutApproval> list = outApprovalService.selectOutApprovalList(outApproval);
        ExcelUtil<OutApproval> util = new ExcelUtil<OutApproval>(OutApproval.class);
        util.exportExcel(response, list, "外出审核数据");
    }

}
