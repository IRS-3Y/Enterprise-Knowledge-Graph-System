package mtech.irs.ekgs.util;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

abstract public class CacheUtils {
	
	public static <K, V> LoadingCache<K, V> buildCache(Function<K, V> loader, long expirySeconds){
		CacheLoader<K, V> cacheLoader = new CacheLoader<>() {
			@Override
			public V load(K key) throws Exception {
				return loader.apply(key);
			}
		};
		return CacheBuilder.newBuilder()
				.expireAfterWrite(expirySeconds, TimeUnit.SECONDS)
				.build(cacheLoader);
	}
}
