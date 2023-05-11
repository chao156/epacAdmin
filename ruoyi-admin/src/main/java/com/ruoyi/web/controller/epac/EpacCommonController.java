package com.ruoyi.web.controller.epac;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRegion;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.EpacSupplies;
import com.ruoyi.system.domain.EpacSuppliesApply;
import com.ruoyi.system.domain.NucleicCollectionArea;
import com.ruoyi.system.domain.OutApproval;
import com.ruoyi.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.core.domain.AjaxResult.success;

@RestController
@RequestMapping("/system/epacCommon")
public class EpacCommonController {

    @Autowired
    SysRegionMapper sysRegionMapper;

    @Autowired
    EpacSuppliesMapper epacSuppliesMapper;

    @Autowired
    EpacSuppliesApplyMapper epacSuppliesApplyMapper;

    @Autowired
    OutApprovalMapper outApprovalMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    RedisCache redisCache;

    @Autowired
    NucleicCollectionAreaMapper areaMapper;

    @PostMapping("/userRegister")
    public AjaxResult userRegister(@RequestBody SysUser sysUser) {
        return success(sysUserMapper.insertUser(sysUser));
    }

    @GetMapping("/getTownList")
    public List<Map> getTownList() {
        List<SysRegion> townList = sysRegionMapper.getTownList();
        List<Map> resList = new ArrayList();
        for (SysRegion sysRegion : townList) {
            Map map = new HashMap();
            map.put("townCode", sysRegion.getRegionCode());
            map.put("townName", sysRegion.getRegionName());
            resList.add(map);
        }
        return resList;
    }

    @GetMapping("/getCommList/{code}")
    public List<Map> getCommList(@PathVariable("code") String code) {
        List<SysRegion> commList = sysRegionMapper.getCommList(code);
        List<Map> resList = new ArrayList<>();
        for (SysRegion sysRegion : commList) {
            Map map = new HashMap();
            map.put("commCode", sysRegion.getRegionCode());
            map.put("commName", sysRegion.getRegionName());
            resList.add(map);
        }
        return resList;
    }

    @GetMapping("/getItemList")
    public List<Map> getItemList() {
        List<EpacSupplies> itemList = epacSuppliesMapper.getItemList();
        List<Map> resList = new ArrayList<>();
        for (EpacSupplies epacSupplies : itemList) {
            Map map = new HashMap();
            map.put("itemCode", epacSupplies.getItemName());
            map.put("itemName", epacSupplies.getItemName());
            resList.add(map);
        }
        return resList;
    }

    @GetMapping("/getItemApplyInfo/{userName}")
    public List<EpacSuppliesApply> getItemApplyInfo(@PathVariable("userName") String userName) {
        EpacSuppliesApply esa = new EpacSuppliesApply();


        esa.setUserName(userName);
        List<EpacSuppliesApply> list = epacSuppliesApplyMapper.selectEpacSuppliesApplyList(esa);
        for (EpacSuppliesApply data : list) {
            if (data.getItemApplyStatus().equals("0")) {
                data.setItemApplyStatus("待审核");
            } else if (data.getItemApplyStatus().equals("1")) {
                data.setItemApplyStatus("已通过");
            } else if (data.getItemApplyStatus().equals("2")) {
                data.setItemApplyStatus("驳回");
            }
        }
        return list;
    }

    @GetMapping("/getOutApplyInfo/{userName}")
    public List<OutApproval> getOutApplyInfo(@PathVariable("userName") String userName) {
        OutApproval out = new OutApproval();
        out.setUserName(userName);
        List<OutApproval> list = outApprovalMapper.selectOutApprovalList(out);
        for (OutApproval data : list) {
            if (data.getStatus().equals("0")) {
                data.setStatus("待审核");
            } else if (data.getStatus().equals("1")) {
                data.setStatus("已通过");
            } else if (data.getStatus().equals("2")) {
                data.setStatus("驳回");
            }
        }
        return list;
    }

    @GetMapping("/getQueueNumberArea")
    public List getQueueNumberArea() {
        //取出有排队人数的检测点ID，方便去缓存中取出对应的排队数
        Object obj = redisCache.getCacheObject("QueueNumber");
        List<String> idList = (List<String>) obj;
            NucleicCollectionArea nca = new NucleicCollectionArea();
            List<NucleicCollectionArea> areaList = areaMapper.selectNucleicCollectionAreaList(nca);
            for (String areaId : idList) {
                Integer queueNumber = redisCache.getCacheObject("QueueNumber_" + areaId);
                for (NucleicCollectionArea nuca : areaList) {
                    if (nuca.getNcaId() == Long.parseLong(areaId)) {
                        nuca.setQueueNumber(queueNumber.toString());
                    }
                }
            }
            for (NucleicCollectionArea nuca : areaList) {
                if (nuca.getQueueNumber() == null) {
                    nuca.setQueueNumber("当前无人排队");
                }
            }
        return areaList;
        }
}
