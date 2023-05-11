package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysRegion;
import com.ruoyi.system.mapper.SysRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysCommMapper;
import com.ruoyi.system.domain.SysComm;
import com.ruoyi.system.service.ISysCommService;

/**
 * 社区管理Service业务层处理
 *
 * @author Liyu
 * @date 2022-11-14
 */
@Service
public class SysCommServiceImpl implements ISysCommService
{
    @Autowired
    private SysCommMapper sysCommMapper;

    @Autowired
    private SysRegionMapper sysRegionMapper;

    /**
     * 查询社区管理
     *
     * @param commId 社区管理主键
     * @return 社区管理
     */
    @Override
    public SysComm selectSysCommByCommId(Long commId)
    {
        return sysCommMapper.selectSysCommByCommId(commId);
    }

    /**
     * 查询社区管理列表
     *
     * @param sysComm 社区管理
     * @return 社区管理
     */
    @Override
    public List<SysComm> selectSysCommList(SysComm sysComm)
    {
        return sysCommMapper.selectSysCommList(sysComm);
    }

    /**
     * 新增社区管理
     *
     * @param sysComm 社区管理
     * @return 结果
     */
    @Override
    public int insertSysComm(SysComm sysComm)
    {
        return sysCommMapper.insertSysComm(sysComm);
    }

    @Override
    public void importComm(){
        //从地区信息表中查询社区信息
        List<SysRegion> sysRegionList = sysRegionMapper.getCommInfoFromRegion();
        String countyName = sysRegionMapper.getCountyName();
        List<SysComm> sysCommList = new ArrayList<>();
        for (SysRegion sysRegion:sysRegionList) {
            SysComm sysComm = new SysComm();
            sysComm.setCommCode(sysRegion.getRegionCode());
            sysComm.setCommName(sysRegion.getRegionName());
            sysComm.setCommContStatus("0");
            sysComm.setCounty(countyName);
            sysComm.setTown(sysRegion.getTown());
            sysComm.setCommStatus("1");
            sysCommList.add(sysComm);
        }
        //将查询出的社区信息插入到社区信息表中
        sysCommMapper.importCommInfo(sysCommList);
    }

    /**
     * 修改社区管理
     *
     * @param sysComm 社区管理
     * @return 结果
     */
    @Override
    public int updateSysComm(SysComm sysComm)
    {
        return sysCommMapper.updateSysComm(sysComm);
    }

    /**
     * 批量删除社区管理
     *
     * @param commIds 需要删除的社区管理主键
     * @return 结果
     */
    @Override
    public int deleteSysCommByCommIds(Long[] commIds)
    {
        return sysCommMapper.deleteSysCommByCommIds(commIds);
    }

    /**
     * 删除社区管理信息
     *
     * @param commId 社区管理主键
     * @return 结果
     */
    @Override
    public int deleteSysCommByCommId(Long commId)
    {
        return sysCommMapper.deleteSysCommByCommId(commId);
    }
}
