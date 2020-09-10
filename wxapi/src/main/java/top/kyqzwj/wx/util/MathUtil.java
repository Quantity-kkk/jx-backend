package top.kyqzwj.wx.util;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/9 14:20
 */
public class MathUtil {
    public static final Integer INTEGER_ZERO = 0;
    public static final Integer INTEGER_ONE = 1;

    public static Integer toInteger(Object srcObj) {
        if (srcObj == null) {
            return null;
        } else {
            String objStr = StringUtil.unwrap(ObjectUtil.toString(srcObj), "\"");
            if ("true".equalsIgnoreCase(objStr)) {
                return INTEGER_ONE;
            } else {
                return "false".equalsIgnoreCase(objStr) ? INTEGER_ZERO : NumberUtils.createInteger(objStr);
            }
        }
    }
}
