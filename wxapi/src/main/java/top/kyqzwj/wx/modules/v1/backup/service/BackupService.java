package top.kyqzwj.wx.modules.v1.backup.service;

import top.kyqzwj.wx.modules.v1.backup.dto.KzOtSubsidyDto;
import top.kyqzwj.wx.modules.v1.backup.dto.KzOvertimeDto;
import top.kyqzwj.wx.modules.v1.backup.dto.KzdActivityCostDto;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/14 14:16
 */
public interface BackupService {

    /**
     * 保存加班记录
     * @param dto
     * */
    void saveOvertimeRecord(KzOvertimeDto dto);

    /**
     * 保存餐补信息
     * @param dto
     * */
    void saveSubsidyRecord(KzOtSubsidyDto dto);

    /**
     * 保存团建信息
     * @param dto
     * */
    void saveActivityRecord(KzdActivityCostDto dto);

    /**
     * 获取用户登记的加班记录
     * @param params
     * @return
     * */
    List<Map> getOvertimeRecord(Map params);

    /**
     * 获取用户登记的加班明细
     * @param paramMap
     * @return
     * */
    List<Map> getOvertimeRecordDetail(Map paramMap);


    /**
     * 获取用户登记的餐补记录
     * @param paramMap
     * @return
     * */
    List<Map> getSubsidyRecord(Map paramMap);

    /**
     * 获取用户登记的团建记录
     * @param params
     * @return
     * */
    List<Map> getActivityRecord(Map params);
}
