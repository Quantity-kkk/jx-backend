package top.kyqzwj.wx.modules.v1.backup.dto;

import lombok.Data;
import top.kyqzwj.wx.modules.v1.backup.domain.KzOvertime;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/14 16:15
 */
@Data
public class KzOvertimeDto extends KzOvertime {
    private boolean cost;
}
