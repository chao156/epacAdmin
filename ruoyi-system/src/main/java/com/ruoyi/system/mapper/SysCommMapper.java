package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysComm;

/**
 * 社区管理Mapper接口
 *
 * @author Liyu
 * @date 2022-11-14
 */
public interface SysCommMapper
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
     * 将查询出的社区信息导入到社区信息表中
     */
    public void importCommInfo(List<SysComm> list);

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
     * 删除社区管理
     *
     * @param commId 社区管理主键
     * @return 结果
     */
    public int deleteSysCommByCommId(Long commId);

    /**
     * 批量删除社区管理
     *
     * @param commIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCommByCommIds(Long[] commIds);
}
