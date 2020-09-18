package top.kyqzwj.wx.modules.v1.backup.service;

import top.kyqzwj.wx.modules.v1.backup.dto.KzOvertimeDto;

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
}
