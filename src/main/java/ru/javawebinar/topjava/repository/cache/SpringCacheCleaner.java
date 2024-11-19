package ru.javawebinar.topjava.repository.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

public class SpringCacheCleaner implements CacheCleaner {

    @Autowired
    private CacheManager cacheManager;
    private String entityName;

    public SpringCacheCleaner(String entityName) {
        this.entityName = entityName;
    }

    public void clean() {
        cacheManager.getCache(entityName).clear();
    }
}
