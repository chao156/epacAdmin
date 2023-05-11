package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysRegionMapper;
import com.ruoyi.common.core.domain.entity.SysRegion;
import com.ruoyi.system.service.ISysRegionService;

import javax.validation.Validator;

/**
 * 地区管理Service业务层处理
 *
 * @author Liyu
 * @date 2022-11-13
 */
@Service
public class SysRegionServiceImpl implements ISysRegionService
{
    private static final Logger log = LoggerFactory.getLogger(SysRegionServiceImpl.class);

    @Autowired
    private SysRegionMapper sysRegionMapper;

    @Autowired
    private Validator validator;

    /**
     * 查询地区管理
     *
     * @param regionId 地区管理主键
     * @return 地区管理
     */
    @Override
    public SysRegion selectSysRegionByRegionId(Long regionId)
    {
        return sysRegionMapper.selectSysRegionByRegionId(regionId);
    }

    /**
     * 查询地区管理列表
     *
     * @param sysRegion 地区管理
     * @return 地区管理
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRegion> selectSysRegionList(SysRegion sysRegion)
    {
        return sysRegionMapper.selectSysRegionList(sysRegion);
    }

    /**
     * 新增地区管理
     *
     * @param sysRegion 地区管理
     * @return 结果
     */
    @Override
    public int insertSysRegion(SysRegion sysRegion)
    {
        return sysRegionMapper.insertSysRegion(sysRegion);
    }

    /**
     * 修改地区管理
     *
     * @param sysRegion 地区管理
     * @return 结果
     */
    @Override
    public int updateSysRegion(SysRegion sysRegion)
    {
        return sysRegionMapper.updateSysRegion(sysRegion);
    }

    /**
     * 批量删除地区管理
     *
     * @param regionIds 需要删除的地区管理主键
     * @return 结果
     */
    @Override
    public int deleteSysRegionByRegionIds(Long[] regionIds)
    {
        return sysRegionMapper.deleteSysRegionByRegionIds(regionIds);
    }

    /**
     * 删除地区管理信息
     *
     * @param regionId 地区管理主键
     * @return 结果
     */
    @Override
    public int deleteSysRegionByRegionId(Long regionId)
    {
        return sysRegionMapper.deleteSysRegionByRegionId(regionId);
    }

    /**
     * 导入用户数据
     *
     * @param regionList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importRegion(List<SysRegion> regionList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(regionList) || regionList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysRegion region : regionList)
        {
            try
            {
                // 验证是否存在这个地区
                SysRegion sysRegion = sysRegionMapper.selectSysRegionByRegionCode(region.getRegionCode());
                //不存在则添加一条
                if (StringUtils.isNull(sysRegion))
                {
                    BeanValidators.validateWithException(validator, region);
                    this.insertSysRegion(region);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + region.getRegionName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, region);
                    this.updateSysRegion(region);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + region.getRegionName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + region.getRegionName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + region.getRegionName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


    /**
     * 查询地区树结构信息
     *
     * @param region 地区
     * @return 地区树信息集合
     */
    @Override
    public List<TreeSelect> selectRegionTreeList(SysRegion region)
    {
        List<SysRegion> retionList = SpringUtils.getAopProxy(this).selectSysRegionList(region);
        return buildRegionTreeSelect(retionList);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param regionList 地区列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildRegionTreeSelect(List<SysRegion> regionList)
    {
        List<SysRegion> regionTrees = buildRegionTree(regionList);
        return regionTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param regionList 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysRegion> buildRegionTree(List<SysRegion> regionList)
    {
        List<SysRegion> returnList = new ArrayList<SysRegion>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysRegion region : regionList)
        {
            tempList.add(region.getRegionId());
        }
        for (SysRegion region : regionList)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(region.getParentId()))
            {
                recursionFn(regionList, region);
                returnList.add(region);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = regionList;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysRegion> list, SysRegion t)
    {
        // 得到子节点列表
        List<SysRegion> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysRegion tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysRegion> getChildList(List<SysRegion> list, SysRegion t)
    {
        List<SysRegion> tlist = new ArrayList<SysRegion>();
        Iterator<SysRegion> it = list.iterator();
        while (it.hasNext())
        {
            SysRegion region = (SysRegion) it.next();
            if (StringUtils.isNotNull(region.getRegionParentCode()) && region.getRegionParentCode().equals(t.getRegionCode()))
            {
                tlist.add(region);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysRegion> list, SysRegion t)
    {
        return getChildList(list, t).size() > 0;
    }


}
