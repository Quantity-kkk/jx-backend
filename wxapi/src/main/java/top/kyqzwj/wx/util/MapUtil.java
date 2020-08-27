package top.kyqzwj.wx.util;

import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 20:51
 */
public class MapUtil {

    /**
     * Map判空
     */
    public static boolean isEmpty(Map data) {
        boolean isEmpty = true;
        if (data != null && data.size() > 0) {
            isEmpty = false;
        }
        return isEmpty;
    }

    /**
     * Map判非空
     */
    public static boolean isNotEmpty(Map data) {
        return !isEmpty(data);
    }
}
