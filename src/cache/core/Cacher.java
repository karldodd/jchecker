package cache.core;


/**
 * Cacher的接口定义
 * @author aleck
 *
 * @param <K>
 * @param <V>
 */
public abstract class Cacher<K extends Cachable, V> {
	private final Scheduler scheduler;
	private final CacherPolicy policy;
	
	private final String name;
	
	public Cacher() {
		this("unnamed-cacher" + CacherManager.randName(), null);
	}
	
	public Cacher(String name) {
		this(name, null);
	}

	public Cacher(String name, CacherPolicy policy) {
		this.name = name;
		this.policy = policy;
		if (policy != null) {
			this.scheduler = policy.getScheduler();
		} else {
			this.scheduler = null;
		}
	}

	/**
	 * 从Cacher里面找到相应的key对应的value，并且返回。
	 * 相应的key在返回后仍然在Cacher中存在
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
		return this.getClass().getSimpleName() + ": " + name;
	}

	/**
	 * 得到注册的名字
	 */
	public String getName() {
		return name;
	}
	
}
