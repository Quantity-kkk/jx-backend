package top.kyqzwj.wx.jpa.repository;

import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.kyqzwj.wx.util.ListUtil;
import top.kyqzwj.wx.util.MapUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 21:12
 */
@Repository
public class NativeSql {
    private static EntityManager em;
    private static JdbcTemplate jdbcTemplate;

    public NativeSql(EntityManager em, JdbcTemplate jdbcTemplate) {
        NativeSql.em = em;
        NativeSql.jdbcTemplate = jdbcTemplate;
    }

    public static List<Map> findByNativeSQL(String strSql, List params, Integer... splitPage) {
        Query query = em.createNativeQuery(strSql);
        (query.unwrap(NativeQueryImpl.class)).setResultTransformer(MapResultTransformer.INSTANSE);
        if (ListUtil.isNotEmpty(params)) {
            for(int i = 0; i < params.size(); ++i) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        SQLCommon.setPageAdapter(query, splitPage);
        return query.getResultList();
    }

    public static <T> List<T> findByNativeSQLPageable(String strSql, List params, Class<T> dtoClass, Pageable pageable) {
        Query query = JpaContext.getEntityManager().createNativeQuery(strSql);
        ((NativeQueryImpl)query.unwrap(NativeQueryImpl.class)).setResultTransformer(new DtoResultTransformer(dtoClass));
        return findByNativeSQLPageable(query, params, pageable);
    }

    public static List<Map> findByNativeSQLPageable(String strSql, List params, Pageable pageable) {
        Query query = em.createNativeQuery(strSql);
        ((NativeQueryImpl)query.unwrap(NativeQueryImpl.class)).setResultTransformer(MapResultTransformer.INSTANSE);
        return findByNativeSQLPageable(query, params, pageable);
    }

    private static <T> List<T> findByNativeSQLPageable(Query query, List params, Pageable pageable){
        if (ListUtil.isNotEmpty(params)) {
            for(int i = 0; i < params.size(); ++i) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        if (pageable != null) {
            query.setFirstResult(SQLCommon.calcStartFromPageable(pageable));
            query.setMaxResults(SQLCommon.calcLimitFromPageable(pageable));
        }

        return query.getResultList();
    }

    public static Map findOneByNativeSQL(String strSql, List params) {
        return (Map)ListUtil.get(findByNativeSQL(strSql, params), 0);
    }

    public static Integer findCountByNativeSQL(String strSql, List params){
        Map map = ListUtil.get(findByNativeSQL(strSql, params), 0);
        if(MapUtil.isEmpty(map)){
            return 0;
        }
        Object value = map.values().toArray()[0];
        return new BigInteger(value.toString(),10).intValue();
    }

    public static int countByNativeSQL(String strSql, List params) {
        String countStrSql = "select count(*) as count from ( " + strSql + " ) total";
        Map mapResult = findOneByNativeSQL(countStrSql, params);
        return ((BigInteger)mapResult.get("count")).intValue();
    }

    public static int updateByNativeSQL(String strSql, List params) {
        Object[] objParams = null;
        if (ListUtil.isNotEmpty(params)) {
            objParams = params.toArray();
        }

        return jdbcTemplate.update(strSql, objParams);
    }

    public static int[] batchUpdateByNativeSQL(String strSql, List<List> params) {
        List batchLstParams = new ArrayList();
        if (ListUtil.isNotEmpty(params)) {
            for(int i = 0; i < params.size(); ++i) {
                List lstParams = (List)ListUtil.get(params, i);
                Object[] objParams = null;
                if (ListUtil.isNotEmpty(lstParams)) {
                    objParams = lstParams.toArray();
                }

                batchLstParams.add(objParams);
            }
        }

        return jdbcTemplate.batchUpdate(strSql, batchLstParams);
    }

    public static void execute(String sql) {
        jdbcTemplate.execute(sql);
    }

    public static int update(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    public static <T> List<T> queryForList(String sql, Class<T> elementType, Object... params) {
        return jdbcTemplate.queryForList(sql, elementType, params);
    }
}
