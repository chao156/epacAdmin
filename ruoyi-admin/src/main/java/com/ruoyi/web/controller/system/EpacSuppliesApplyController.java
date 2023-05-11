package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.EpacConstant;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.EpacSuppliesApply;
import com.ruoyi.system.service.IEpacSuppliesApplyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物资审核Controller
 *
 * @author Liyu
 * @date 2023-03-01
 */
@RestController
@RequestMapping("/system/apply")
public class EpacSuppliesApplyController extends BaseController
{
    @Autowired
    private IEpacSuppliesApplyService epacSuppliesApplyService;

    /**
     * 查询物资审核列表
     */
    @PreAuthorize("@ss.hasPermi('system:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpacSuppliesApply epacSuppliesApply)
    {
        startPage();
        List<EpacSuppliesApply> list = epacSuppliesApplyService.selectEpacSuppliesApplyList(epacSuppliesApply);
        return getDataTable(list);
    }

    /**
     * 审批通过物资申请
     * @param itemIds
     */
    @PreAuthorize("@ss.hasPermi('system:approval:edit')")
    @GetMapping(value = "/handleItemApply/{itemIds}")
    public void handleItemApply(@PathVariable("itemIds") Long[] itemIds){
        epacSuppliesApplyService.handleItemApply(itemIds, EpacConstant.OUT_APPLY_TAG_ADOPT);
    }

    /**
     * 审批驳回物资申请
     * @param itemIds
     */
    @PreAuthorize("@ss.hasPermi('system:approval:edit')")
    @GetMapping(value = "/rejectItem/{itemIds}")
    public void rejectItem(@PathVariable("itemIds") Long[] itemIds){
        epacSuppliesApplyService.handleItemApply(itemIds,EpacConstant.OUT_APPLY_TAG_REJECT);
    }

    /**
     * 导出物资审核列表
     */
    @PreAuthorize("@ss.hasPermi('system:apply:export')")
    @Log(title = "物资审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpacSuppliesApply epacSuppliesApply)
    {
        List<EpacSuppliesApply> list = epacSuppliesApplyService.selectEpacSuppliesApplyList(epacSuppliesApply);
        ExcelUtil<EpacSuppliesApply> util = new ExcelUtil<EpacSuppliesApply>(EpacSuppliesApply.class);
        util.exportExcel(response, list, "物资审核数据");
    }

    /**
     * 获取物资审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:apply:query')")
    @GetMapping(value = "/{itemApplyId}")
    public AjaxResult getInfo(@PathVariable("itemApplyId") Long itemApplyId)
    {
        return success(epacSuppliesApplyService.selectEpacSuppliesApplyByItemApplyId(itemApplyId));
    }

    /**
     * 新增物资审核
     */
    @PreAuthorize("@ss.hasPermi('system:apply:add')")
    @Log(title = "物资审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpacSuppliesApply epacSuppliesApply)
    {
        return toAjax(epacSuppliesApplyService.insertEpacSuppliesApply(epacSuppliesApply));
    }

    /**
     * 修改物资审核
     */
    @PreAuthorize("@ss.hasPermi('system:apply:edit')")
    @Log(title = "物资审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpacSuppliesApply epacSuppliesApply)
    {
        return toAjax(epacSuppliesApplyService.updateEpacSuppliesApply(epacSuppliesApply));
    }

    /**
     * 删除物资审核
     */
    @PreAuthorize("@ss.hasPermi('system:apply:remove')")
    @Log(title = "物资审核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemApplyIds}")
    public AjaxResult remove(@PathVariable Long[] itemApplyIds)
    {
        return toAjax(epacSuppliesApplyService.deleteEpacSuppliesApplyByItemApplyIds(itemApplyIds));
    }
}
