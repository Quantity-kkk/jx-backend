package top.kyqzwj.wx.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 20:51
 */
public class ArrayUtil {

    /**
     * Array判空
     */
    public static boolean isEmpty(Object[] data) {
        boolean isEmpty = true;
        if (data != null && data.length > 0) {
            isEmpty = false;
        }
        return isEmpty;
    }

    /**
     * Array判非空
     */
    public static boolean isNotEmpty(Object[] data) {
        return !isEmpty(data);
    }

    /**
     * 数组转换List
     */
    public static List asList(Object[] arr) {
        if (isEmpty(arr)) {
            return new ArrayList();
        } else {
            return Arrays.asList(arr);
        }
    }
}
