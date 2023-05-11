package com.ruoyi.web.controller.system;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.extra.qrcode.QrCodeException;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.NucleicCollectionArea;
import com.ruoyi.system.service.INucleicCollectionAreaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 核酸点管理Controller
 *
 * @author Liyu
 * @date 2022-11-25
 */
@RestController
@RequestMapping("/system/area")
public class NucleicCollectionAreaController extends BaseController
{
    @Autowired
    private INucleicCollectionAreaService nucleicCollectionAreaService;

    /**
     * 获取检测点下拉列表
     * @return
     */
    @GetMapping("/getCollectionList")
    public AjaxResult getCollectionList(){
        List<Map> list = nucleicCollectionAreaService.getCollectionList();
        return success(list);
    }

    /**
     * 生成二维码
     * @return
     */
    @GetMapping("/getQrCode/{ncaId}")
    @ResponseBody
    public void getQrCode(@PathVariable("ncaId") Long ncaId, HttpServletResponse response){
        //将检测点id作为数据放入二维码。
        String qrCodeUrl = ncaId.toString();
        try {
            QrConfig qrConfig = new QrConfig(1000, 1000);
            QrCodeUtil.generate(qrCodeUrl, qrConfig, "png", response.getOutputStream());
        } catch (QrCodeException | IOException e) {
            System.out.println("生成二维码发生错误！ {}！"+ e.getMessage());
        }
    }

    /**
     * 获取检测点经纬度
     * @return
     */
    @GetMapping("/getPosition")
    public List<NucleicCollectionArea> getPosition(){
        List<NucleicCollectionArea> positionList = nucleicCollectionAreaService.getPosition();
        return positionList;
    }

    /**
     * 查询核酸点管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:area:list')")
    @GetMapping("/list")
    public TableDataInfo list(NucleicCollectionArea nucleicCollectionArea)
    {
        startPage();
        List<NucleicCollectionArea> list = nucleicCollectionAreaService.selectNucleicCollectionAreaList(nucleicCollectionArea);
        return getDataTable(list);
    }

    /**
     * 导出核酸点管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:area:export')")
    @Log(title = "核酸点管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NucleicCollectionArea nucleicCollectionArea)
    {
        List<NucleicCollectionArea> list = nucleicCollectionAreaService.selectNucleicCollectionAreaList(nucleicCollectionArea);
        ExcelUtil<NucleicCollectionArea> util = new ExcelUtil<NucleicCollectionArea>(NucleicCollectionArea.class);
        util.exportExcel(response, list, "核酸点管理数据");
    }

    /**
     * 获取核酸点管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:area:query')")
    @GetMapping(value = "/{ncaId}")
    public AjaxResult getInfo(@PathVariable("ncaId") Long ncaId)
    {
        return success(nucleicCollectionAreaService.selectNucleicCollectionAreaByNcaId(ncaId));
    }

    /**
     * 新增核酸点管理
     */
    @PreAuthorize("@ss.hasPermi('system:area:add')")
    @Log(title = "核酸点管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NucleicCollectionArea nucleicCollectionArea)
    {
        return toAjax(nucleicCollectionAreaService.insertNucleicCollectionArea(nucleicCollectionArea));
    }

    /**
     * 修改核酸点管理
     */
    @PreAuthorize("@ss.hasPermi('system:area:edit')")
    @Log(title = "核酸点管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NucleicCollectionArea nucleicCollectionArea)
    {
        return toAjax(nucleicCollectionAreaService.updateNucleicCollectionArea(nucleicCollectionArea));
    }

    /**
     * 删除核酸点管理
     */
    @PreAuthorize("@ss.hasPermi('system:area:remove')")
    @Log(title = "核酸点管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ncaIds}")
    public AjaxResult remove(@PathVariable Long[] ncaIds)
    {
        return toAjax(nucleicCollectionAreaService.deleteNucleicCollectionAreaByNcaIds(ncaIds));
    }
}
