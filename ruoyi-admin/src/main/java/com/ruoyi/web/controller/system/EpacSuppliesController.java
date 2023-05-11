package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.EpacSupplies;
import com.ruoyi.system.service.IEpacSuppliesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物资管理Controller
 *
 * @author Liyu
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/system/supplies")
public class EpacSuppliesController extends BaseController
{
    @Autowired
    private IEpacSuppliesService epacSuppliesService;

    /**
     * 查询物资管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:supplies:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpacSupplies epacSupplies)
    {
        startPage();
        List<EpacSupplies> list = epacSuppliesService.selectEpacSuppliesList(epacSupplies);
        return getDataTable(list);
    }

    /**
     * 导出物资管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:supplies:export')")
    @Log(title = "物资管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpacSupplies epacSupplies)
    {
        List<EpacSupplies> list = epacSuppliesService.selectEpacSuppliesList(epacSupplies);
        ExcelUtil<EpacSupplies> util = new ExcelUtil<EpacSupplies>(EpacSupplies.class);
        util.exportExcel(response, list, "物资管理数据");
    }

    /**
     * 获取物资管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:supplies:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") Long itemId)
    {
        return success(epacSuppliesService.selectEpacSuppliesByItemId(itemId));
    }

    /**
     * 新增物资管理
     */
    @PreAuthorize("@ss.hasPermi('system:supplies:add')")
    @Log(title = "物资管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpacSupplies epacSupplies)
    {
        return toAjax(epacSuppliesService.insertEpacSupplies(epacSupplies));
    }

    /**
     * 修改物资管理
     */
    @PreAuthorize("@ss.hasPermi('system:supplies:edit')")
    @Log(title = "物资管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpacSupplies epacSupplies)
    {
        return toAjax(epacSuppliesService.updateEpacSupplies(epacSupplies));
    }

    /**
     * 删除物资管理
     */
    @PreAuthorize("@ss.hasPermi('system:supplies:remove')")
    @Log(title = "物资管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds)
    {
        return toAjax(epacSuppliesService.deleteEpacSuppliesByItemIds(itemIds));
    }
}
