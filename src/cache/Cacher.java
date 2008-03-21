package cache;

/**
 * Cacher的接口定义
 * @author aleck
 *
 * @param <K>
 * @param <V>
 */
public abstract class Cacher<K extends ICachable, V> {
	private final String name;
	
	public Cacher() {
		this.name = "cacher" + hashCode();
	}
	
	public Cacher(String name) {
		this.name = name;
	}

	/**
	 * Recover object from cache
	 * @param key
	 * @return
	 */
	public abstract AbstractRecoverResult<V> recover(K key);
	
	/**
	 * Push to cacher
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract boolean cache(K key, V value);
	
	public String toString() {
		return name;
	}
	
}
