package cache;

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
	public abstract V recover(K key);
	
	/**
	 * Push to cacher
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract boolean cache(K key, V value);
	
}
