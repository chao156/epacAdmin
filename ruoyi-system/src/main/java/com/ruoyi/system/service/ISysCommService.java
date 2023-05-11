package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysComm;

/**
 * 社区管理Service接口
 *
 * @author Liyu
 * @date 2022-11-14
 */
public interface ISysCommService
{
    /**
     * 查询社区管理
     *
     * @param commId 社区管理主键
     * @return 社区管理
     */
    public SysComm selectSysCommByCommId(Long commId);

    /**
     * 查询社区管理列表
     *
     * @param sysComm 社区管理
     * @return 社区管理集合
     */
    public List<SysComm> selectSysCommList(SysComm sysComm);

    /**
     * 将地区管理表中的社区信息导入到社区管理中
     * @return
     */
    public void importComm();

    /**
     * 新增社区管理
     *
     * @param sysComm 社区管理
     * @return 结果
     */
    public int insertSysComm(SysComm sysComm);

    /**
     * 修改社区管理
     *
     * @param sysComm 社区管理
     * @return 结果
     */
    public int updateSysComm(SysComm sysComm);

    /**
     * 批量删除社区管理
     *
     * @param commIds 需要删除的社区管理主键集合
     * @return 结果
     */
    public int deleteSysCommByCommIds(Long[] commIds);

    /**
     * 删除社区管理信息
     *
     * @param commId 社区管理主键
     * @return 结果
     */
    public int deleteSysCommByCommId(Long commId);
}
