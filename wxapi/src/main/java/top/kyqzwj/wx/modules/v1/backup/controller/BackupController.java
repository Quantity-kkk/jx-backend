package top.kyqzwj.wx.modules.v1.backup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.kyqzwj.wx.facade.ResponsePayload;
import top.kyqzwj.wx.modules.v1.backup.dto.KzOvertimeDto;
import top.kyqzwj.wx.modules.v1.backup.service.BackupService;
import top.kyqzwj.wx.util.ConfigUtil;
import top.kyqzwj.wx.util.WebSpringBeanUtils;

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

}
