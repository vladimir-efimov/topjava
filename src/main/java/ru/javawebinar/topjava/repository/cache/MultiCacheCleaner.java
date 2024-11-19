package ru.javawebinar.topjava.repository.cache;

import java.util.List;

public class MultiCacheCleaner implements CacheCleaner {

    private List<CacheCleaner> cacheCleaners;

    public MultiCacheCleaner(List<CacheCleaner> cacheCleaners) {
        this.cacheCleaners = cacheCleaners;
    }
    public void clean() {
        cacheCleaners.forEach(CacheCleaner::clean);
    }
}
