package top.kyqzwj.wx.jpa.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.kyqzwj.wx.util.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.*;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:20
 */
@Repository
public class CrudSQL {
    private static final Logger log = LoggerFactory.getLogger(CrudSQL.class);
    private static EntityManager em;
    private static final int BATCH_COUNT = 8;

    public CrudSQL(EntityManager em, JdbcTemplate jdbcTemplate) {
        CrudSQL.em = em;
    }

    private static DomainCompileHandler getDomainCompileHandler() {
        String[] beanNames = WebSpringBeanUtils.getBeanForType(DomainCompileHandler.class);
        DomainCompileHandler domainCompileHandler = null;
        if (beanNames.length > 0) {
            domainCompileHandler = (DomainCompileHandler) WebSpringBeanUtils.getBean(DomainCompileHandler.class);
        }

        return domainCompileHandler;
    }

    public static <T> T insert(T obj) {
        em.persist(obj);
        return obj;
    }

    public static void batchInsert(List objLsts) {
        int i = 0;
        if (ListUtil.isNotEmpty(objLsts)) {
            Iterator var2 = objLsts.iterator();

            while(var2.hasNext()) {
                Object obj = var2.next();
                insert(obj);
                ++i;
                if (i % 8 == 0) {
                    em.flush();
                }
            }
        }

    }

    public static <T> T update(T obj) {
        if (!em.contains(obj)) {

            T db = (T)findByPK(obj.getClass(), getObjId(obj));

            BeanUtil.beanCopy(obj, db, true);
            obj = db;
        }

        obj = em.merge(obj);
        return obj;
    }

    public static int batchUpdate(List objLsts) {
        int i = 0;
        if (ListUtil.isNotEmpty(objLsts)) {
            Iterator var2 = objLsts.iterator();

            while(var2.hasNext()) {
                Object obj = var2.next();
                Serializable id = getObjId(obj);
                update(obj);
                ++i;
                if (i % 8 == 0) {
                    em.flush();
                }
            }
        }

        return i;
    }

    //TODO 暂未构建的代码能力,根据传入的数据获取其Id信息
    private static Serializable getObjId(Object obj) {
        //依据数据的注解信息获取获取其注解为id的属性
        return null;
    }

    public static void delete(Object obj) {
        em.remove(obj);
    }


    public static void batchDelete(List objList) {
        int i = 0;
        if (ListUtil.isNotEmpty(objList)) {
            Iterator var2 = objList.iterator();

            while(var2.hasNext()) {
                Object obj = var2.next();
                em.remove(obj);
                ++i;
                if (i % 8 == 0) {
                    em.flush();
                }
            }
        }

    }

    public static <T> T findByPK(Class<T> clazz, Serializable pk) {
        return em.find(clazz, pk);
    }

    public static <T> List<T> findByAnyFields(Class<T> clazz, String conditionStr, List params, Integer... splitPage) {
        StringBuffer strSql = new StringBuffer();
        strSql.append("from ");
        strSql.append(clazz.getSimpleName());
        strSql.append(" where 1 = 1 ");
        if (StringUtil.isNotEmpty(conditionStr)) {
            strSql.append(conditionStr);
        }

        log.info("LOG00020:query sql is:" + strSql);
        Query query = em.createQuery(strSql.toString());
        if (ListUtil.isNotEmpty(params)) {
            for(int i = 0; i < params.size(); ++i) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        SQLCommon.setPageAdapter(query, splitPage);
        return query.getResultList();
    }

    public static <T> T findOneByAnyFields(Class<T> clazz, String conditionStr, List params) {
        return ListUtil.get(findByAnyFields(clazz, conditionStr, params, 0, 1), 0);
    }

    public static <T> List<T> findByAnyFields(Class<T> clazz, Map conditionMap, String orderByFields, Integer... splitPage) {
        StringBuilder strConditions = new StringBuilder();
        List lstParams = new ArrayList();
        if (MapUtil.isNotEmpty(conditionMap)) {
            conditionMap.forEach((key, value) -> {
                strConditions.append("and ");
                if (value == null) {
                    strConditions.append(key + " is null ");
                } else {
                    strConditions.append(key + " = ?" + (lstParams.size() + 1) + " ");
                    lstParams.add(value);
                }

            });
        }

        strConditions.append(orderByFields);
        return findByAnyFields(clazz, (String)strConditions.toString(), (List)lstParams, splitPage);
    }

    public static <T> T findOneByAnyFields(Class<T> clazz, Map conditionMap, String orderByFields) {
        return ListUtil.get(findByAnyFields(clazz, conditionMap, orderByFields, 0, 1), 0);
    }

    public static <T> List<T> findAll(Class<T> clazz, Integer... splitPage) {
        return findByAnyFields(clazz, "", (List)null, splitPage);
    }

    public static <T> List<T> findAll(Class<T> clazz, Iterable<String> ids) {
        if (ids != null && ids.iterator().hasNext()) {
            List<T> results = new ArrayList();
            Iterator iter = ids.iterator();

            while(iter.hasNext()) {
                String id = (String)iter.next();
                results.add(findByPK(clazz, id));
            }

            return results;
        } else {
            return Collections.emptyList();
        }
    }

    public static <T> List<T> findAll(Class<T> clazz, String idsSplit) {
        Iterable<String> ids = Arrays.asList(StringUtil.split(idsSplit, new String[0]));
        return findAll(clazz, (Iterable)ids);
    }

    public static <T> List<T> findByHql(Class<T> clazz, String hql) {
        Query query = JpaContext.getEntityManager().createQuery(hql);
        return query.getResultList();
    }

    public static boolean existByHql(String hql) {
        Query query = JpaContext.getEntityManager().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<?> list = query.getResultList();
        return list.size() > 0;
    }

    public static <T> Page<T> findAll(Class<T> clazz, Specification<T> spec, Pageable pageable) {
        return JpaContext.getSimpleJpaRepository(clazz).findAll(spec, pageable);
    }

    public static <T> Page<T> findAll(Class<T> clazz, Pageable pageable) {
        return JpaContext.getSimpleJpaRepository(clazz).findAll(pageable);
    }

    public static <T> List<T> findAll(Class<T> clazz, Specification<T> spec, Sort sort) {
        return JpaContext.getSimpleJpaRepository(clazz).findAll(spec, sort);
    }

    public static <T> List<T> findAll(Class<T> clazz, Sort sort) {
        return JpaContext.getSimpleJpaRepository(clazz).findAll(sort);
    }

    public static void flush() {
        em.flush();
    }
}
