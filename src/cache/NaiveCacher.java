package cache;

import java.util.HashMap;
import java.util.Map;

import cache.core.Cacher;
import cache.core.CacherPolicy;
import cache.core.Scheduler;
import cache.normal.NormalCachable;
import cache.normal.NormalRecoverResult;

/**
 * 这个类可以用来测试Cacher的接口。
 * 在没有其他Cacher的实现的时候，不妨使用这个最笨且最Lazy的Cacher。
 * 
 * @author aleck
 *
 * @param <K>
 * @param <V>
 */
public class NaiveCacher<K extends NormalCachable, V> extends Cacher<K, V> {
	
	private Map<K, V> map = new HashMap<K, V>();
	
	private CacherPolicy<K, V> policy = new CacherPolicy<K, V>() {
		@Override
		public V dealDupKey(K key, V oldValue, V newValue) {
			return newValue;
		}

		@Override
		public Scheduler getScheduler() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	public NaiveCacher() {
	}

	@Override
	public boolean cache(K key, V value) {
		try {
			V oldValue = map.get(key);
			if (oldValue == null) {
				map.put(key, value);
			} else {
				map.put(key, policy.dealDupKey(key, oldValue, value));
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public NormalRecoverResult<V> recover(K key) {
		return new NormalRecoverResult<V>(map.get(key));
	}
}
