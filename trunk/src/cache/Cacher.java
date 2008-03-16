package cache;

public abstract class Cacher<K extends ICachable, V> {
	/**
	 *  recover object from cache
	 * @param key
	 * @return
	 */
	public abstract Object recover(K key);
	
	/**
	 * push cache to cacher
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract Object cache(K key, Object value);
	
}
