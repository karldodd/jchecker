package cache.prefix;

import cache.core.Cachable;

/**
 * 同时实现了prefixHashCode的ICachable类
 * 用于某些应用下，如果查找不到Key对应的Value，则转而查找Key的子集对应的Value。
 * @author aleck
 *
 */
public abstract class IPrefixCachable extends Cachable {
	/**
	 * 满足前缀关系的HashCode
	 * @return
	 */
	public abstract PrefixHashCode prefixHashCode();
	
	/**
	 * 判断是否是某一个对象的prefix
	 * @param that
	 * @return
	 */
	public abstract boolean isPrefixOf(IPrefixCachable that);
}
