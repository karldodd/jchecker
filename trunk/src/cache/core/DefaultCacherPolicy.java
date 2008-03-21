package cache.core;


public class DefaultCacherPolicy<K extends ICachable, V> extends CacherPolicy<K, V> {

	@Override
	public V dealDupKey(K key, V oldValue, V newValue) {
		return newValue;
	}

	@Override
	public Scheduler getScheduler() {
		return null;
	}
}
