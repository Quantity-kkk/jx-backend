package top.kyqzwj.wx.jpa.repository;

import org.hibernate.transform.ResultTransformer;
import top.kyqzwj.wx.util.StringUtil;

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
 * @Date 2020/8/27 22:01
 */
public class MapResultTransformer implements ResultTransformer {
    public static MapResultTransformer INSTANSE = new MapResultTransformer();

    private MapResultTransformer() {
    }

    @Override
    public Object transformTuple(Object[] objects, String[] aliases) {
        Map result = new HashMap(objects.length);

        for(int i = 0; i < objects.length; ++i) {
            String alias = StringUtil.camel(aliases[i]);
            if (alias != null) {
                result.put(alias, objects[i]);
            }
        }

        return result;
    }

    public List transformList(List list) {
        return list;
    }
}
