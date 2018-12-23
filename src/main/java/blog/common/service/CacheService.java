package blog.common.service;

import blog.common.etc.CacheName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * @author cyclamen on 23/12/2018
 */
@Component
public class CacheService {

  private final CacheManager cacheManager;

  @Autowired
  public CacheService(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }


  public String get(CacheName cacheName, String key) {
    Cache cache = cacheManager.getCache(cacheName.name());
    assert cache != null;

    return cache.get(key, String.class);
  }

  public void put(CacheName cacheName, String key, String value) {
    Cache cache = cacheManager.getCache(cacheName.name());
    assert cache != null;
    cache.put(key, value);
  }

  public void evict(CacheName cacheName) {
    Cache cache = cacheManager.getCache(cacheName.name());
    assert cache != null;
    cache.clear();
  }
}
