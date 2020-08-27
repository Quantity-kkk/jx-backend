package top.kyqzwj.wx.util;

import java.util.List;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:00
 */
public class ListUtil {

    public static <T> T get(List<T> list, int index) {
        return isEmpty(list) ? null : list.get(index);
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }
}
