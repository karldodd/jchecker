package cache;

/**
 * Cache时候的一些策略，比如碰到重复Key怎么处理。
 * @author aleck
 *
 */
public abstract class CacherPolicy<K extends ICachable, V> {
	public abstract V dealDupKey(K key, V oldValue, V newValue);
}
