package cache;

import java.util.HashMap;
import java.util.Map;

public class NaiveCacher<K extends INormalCachable, V> extends AbstractNormalCacher<K, V> {
	
	private Map<K, V> map = new HashMap<K, V>();
	
	private CacherPolicy<K, V> policy = new CacherPolicy<K, V>() {
		@Override
		public V dealDupKey(K key, V oldValue, V newValue) {
			return newValue;
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
	public V recover(K key) {
		return map.get(key);
	}
}
