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
import com.ruoyi.system.domain.SysComm;
import com.ruoyi.system.service.ISysCommService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 社区管理Controller
 *
 * @author Liyu
 * @date 2022-11-14
 */
@RestController
@RequestMapping("/system/comm")
public class SysCommController extends BaseController
{
    @Autowired
    private ISysCommService sysCommService;

    /**
     * 查询社区管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:comm:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysComm sysComm)
    {
        startPage();
        List<SysComm> list = sysCommService.selectSysCommList(sysComm);
        return getDataTable(list);
    }

    /**
     * 导出社区管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:comm:export')")
    @Log(title = "社区管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysComm sysComm)
    {
        List<SysComm> list = sysCommService.selectSysCommList(sysComm);
        ExcelUtil<SysComm> util = new ExcelUtil<SysComm>(SysComm.class);
        util.exportExcel(response, list, "社区管理数据");
    }

    /**
     * 获取社区管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:comm:query')")
    @GetMapping(value = "/{commId}")
    public AjaxResult getInfo(@PathVariable("commId") Long commId)
    {
        return success(sysCommService.selectSysCommByCommId(commId));
    }

    /**
     * 新增社区管理
     */
    @PreAuthorize("@ss.hasPermi('system:comm:add')")
    @Log(title = "社区管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysComm sysComm)
    {
        return toAjax(sysCommService.insertSysComm(sysComm));
    }

    @PreAuthorize("@ss.hasPermi('system.comm.add')")
    @GetMapping
    public void importComm(){
        sysCommService.importComm();
    }

    /**
     * 修改社区管理
     */
    @PreAuthorize("@ss.hasPermi('system:comm:edit')")
    @Log(title = "社区管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysComm sysComm)
    {
        return toAjax(sysCommService.updateSysComm(sysComm));
    }

    /**
     * 删除社区管理
     */
    @PreAuthorize("@ss.hasPermi('system:comm:remove')")
    @Log(title = "社区管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{commIds}")
    public AjaxResult remove(@PathVariable Long[] commIds)
    {
        return toAjax(sysCommService.deleteSysCommByCommIds(commIds));
    }
}
