package top.kyqzwj.wx.modules.v1.backup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.modules.v1.backup.dto.KzOtSubsidyDto;
import top.kyqzwj.wx.modules.v1.backup.dto.KzOvertimeDto;
import top.kyqzwj.wx.modules.v1.backup.dto.KzdActivityCostDto;
import top.kyqzwj.wx.modules.v1.backup.service.BackupService;
import top.kyqzwj.wx.util.ConfigUtil;
import top.kyqzwj.wx.util.StringUtil;
import top.kyqzwj.wx.util.WebSpringBeanUtils;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/14 16:13
 */
@RestController
@RequestMapping("/v1/backup")
public class BackupController {

    @Autowired
    BackupService backupService;

    @RequestMapping(value = "/overtime/", method = RequestMethod.POST)
    public ResponsePayload saveOvertimeRecord(@RequestBody KzOvertimeDto dto){
        backupService.saveOvertimeRecord(dto);
        return ResponsePayload.success();
    }

    @RequestMapping(value = "/otsubsidy/", method = RequestMethod.POST)
    public ResponsePayload saveOtSubsidyRecord(@RequestBody KzOtSubsidyDto dto){
        backupService.saveSubsidyRecord(dto);
        return ResponsePayload.success();
    }

    @RequestMapping(value = "/activity/", method = RequestMethod.POST)
    public ResponsePayload saveActivityRecord(@RequestBody KzdActivityCostDto dto){
        backupService.saveActivityRecord(dto);
        return ResponsePayload.success();
    }

    @RequestMapping(value = "/overtime/", method = RequestMethod.GET)
    public ResponsePayload getOvertimeRecord(String start, String end, String peopleName){
        Map params = new HashMap(8);
        params.put("start", start);
        params.put("end", end);
        params.put("peopleName", "null".equals(peopleName)? null : peopleName);
        return ResponsePayload.success(backupService.getOvertimeRecord(params));
    }

    @RequestMapping(value = "/overtime/detail/", method = RequestMethod.GET)
    public ResponsePayload getOvertimeRecordDetail(String start, String end, String peopleName){
        Map params = new HashMap(8);
        params.put("start", start);
        params.put("end", end);
        if("null".equals(peopleName)|| StringUtil.isEmpty(peopleName)){
            return ResponsePayload.fail(new RuntimeException("姓名不能为空！"));
        }
        params.put("peopleName", peopleName);

        return ResponsePayload.success(backupService.getOvertimeRecordDetail(params));
    }

    @RequestMapping(value = "/otsubsidy/", method = RequestMethod.GET)
    public ResponsePayload getOtSubsidyRecord(String start, String end){
        Map params = new HashMap(8);
        params.put("start", start);
        params.put("end", end);

        return ResponsePayload.success(backupService.getSubsidyRecord(params));
    }

    @RequestMapping(value = "/activity/", method = RequestMethod.GET)
    public ResponsePayload getActivityRecord(String start, String end){
        Map params = new HashMap(8);
        params.put("start", start);
        params.put("end", end);

        return ResponsePayload.success(backupService.getActivityRecord(params));
    }
}
