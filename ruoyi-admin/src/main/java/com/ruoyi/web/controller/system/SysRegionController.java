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
import com.ruoyi.common.core.domain.entity.SysRegion;
import com.ruoyi.system.service.ISysRegionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 地区管理Controller
 *
 * @author Liyu
 * @date 2022-11-13
 */
@RestController
@RequestMapping("/system/region")
public class SysRegionController extends BaseController
{
    @Autowired
    private ISysRegionService sysRegionService;

    /**
     * 查询地区管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:region:list')")
    @GetMapping("/list")
    public AjaxResult list(SysRegion sysRegion)
    {
        List<SysRegion> list = sysRegionService.selectSysRegionList(sysRegion);
        return success(list);
    }

    /**
     * 导出地区管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:region:export')")
    @Log(title = "地区管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysRegion sysRegion)
    {
        List<SysRegion> list = sysRegionService.selectSysRegionList(sysRegion);
        ExcelUtil<SysRegion> util = new ExcelUtil<SysRegion>(SysRegion.class);
        util.exportExcel(response, list, "地区管理数据");
    }

    /**
     * 获取地区管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:region:query')")
    @GetMapping(value = "/{regionId}")
    public AjaxResult getInfo(@PathVariable("regionId") Long regionId)
    {
        return success(sysRegionService.selectSysRegionByRegionId(regionId));
    }

    /**
     * 新增地区管理
     */
    @PreAuthorize("@ss.hasPermi('system:region:add')")
    @Log(title = "地区管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysRegion sysRegion)
    {
        return toAjax(sysRegionService.insertSysRegion(sysRegion));
    }

    @Log(title = "地区", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:region:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysRegion> util = new ExcelUtil<SysRegion>(SysRegion.class);
        List<SysRegion> regionList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = sysRegionService.importRegion(regionList, updateSupport, operName);
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysRegion> util = new ExcelUtil<SysRegion>(SysRegion.class);

        util.importTemplateExcel(response, "地区数据");
    }


    /**
     * 修改地区管理
     */
    @PreAuthorize("@ss.hasPermi('system:region:edit')")
    @Log(title = "地区管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysRegion sysRegion)
    {
        return toAjax(sysRegionService.updateSysRegion(sysRegion));
    }

    /**
     * 删除地区管理
     */
    @PreAuthorize("@ss.hasPermi('system:region:remove')")
    @Log(title = "地区管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{regionIds}")
    public AjaxResult remove(@PathVariable Long[] regionIds)
    {
        return toAjax(sysRegionService.deleteSysRegionByRegionIds(regionIds));
    }
}
