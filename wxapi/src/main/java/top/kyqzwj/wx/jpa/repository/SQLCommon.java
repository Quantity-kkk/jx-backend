package top.kyqzwj.wx.jpa.repository;

import org.springframework.data.domain.Pageable;
import top.kyqzwj.wx.util.ArrayUtil;
import top.kyqzwj.wx.util.StringUtil;

import javax.persistence.Query;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:03
 */
public class SQLCommon {
    SQLCommon() {
    }

    static void setPageAdapter(Query query, Integer... splitPage) {
        if (ArrayUtil.isNotEmpty(splitPage) && splitPage.length == 2) {
            Integer start = splitPage[0];
            Integer limit = splitPage[1];
            if (start != null && limit != null) {
                query.setFirstResult(start);
                query.setMaxResults(limit);
            }
        }

    }

    static int calcStartFromPageable(Pageable pageable) {
        return pageable.getPageNumber() * pageable.getPageSize();
    }

    static int calcLimitFromPageable(Pageable pageable) {
        return pageable.getPageSize();
    }

    static String searchAddIsVoid(String search) {
        if (StringUtil.isNotEmpty(search)) {
            if (search.indexOf("isVoid") < 0) {
                search = search + ";isVoid==0";
            }
        } else {
            search = "isVoid==0";
        }

        return search;
    }
}
