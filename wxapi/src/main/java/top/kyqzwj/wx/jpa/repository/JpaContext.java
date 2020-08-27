package top.kyqzwj.wx.jpa.repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ConcurrentReferenceHashMap;

import javax.persistence.EntityManager;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/27 22:04
 */
@Repository
public class JpaContext {
    private static EntityManager em;
    private static JdbcTemplate jdbcTemplate;
    private static ConcurrentReferenceHashMap<Class<?>, SimpleJpaRepository> JPA_CACHE = new ConcurrentReferenceHashMap();

    public JpaContext(EntityManager em, JdbcTemplate jdbcTemplate) {
        JpaContext.em = em;
        JpaContext.jdbcTemplate = jdbcTemplate;
    }

    public static SimpleJpaRepository getSimpleJpaRepository(Class<?> domainClass) {
        if (JPA_CACHE.containsKey(domainClass)) {
            return (SimpleJpaRepository)JPA_CACHE.get(domainClass);
        } else {
            SimpleJpaRepository jpa = new SimpleJpaRepository(domainClass, em);
            return cacheAndReturn(domainClass, jpa);
        }
    }

    public static EntityManager getEntityManager() {
        return em;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private static SimpleJpaRepository cacheAndReturn(Class<?> domainClass, SimpleJpaRepository jpa) {
        JPA_CACHE.put(domainClass, jpa);
        return jpa;
    }
}
