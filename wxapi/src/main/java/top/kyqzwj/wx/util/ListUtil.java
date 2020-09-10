package top.kyqzwj.wx.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map convertList2Map(List<Map> list, String keyField, String ...valueField){
        Map<String, Object> ret = new HashMap<>(list.size());
        for(Map map : list){
            if(valueField.length>0){
                ret.put((String) map.get(keyField), map.get(valueField[0]));
            }else {
                ret.put((String) map.get(keyField), map);
            }
        }
        return ret;
    }
}
