package cache.core;


/**
 * Cache时候的一些策略，比如碰到重复Key怎么处理。
 * @author aleck
 *
 */
public abstract class CacherPolicy<K extends Cachable, V> {
	/**
	 * 返回一个scheduler
	 * @return
	 */
	public abstract Scheduler getScheduler();
	
	/**
	 * 碰到重复的key怎么处理？返回值是需要保留的值。
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public abstract V dealDupKey(K key, V oldValue, V newValue);
}
