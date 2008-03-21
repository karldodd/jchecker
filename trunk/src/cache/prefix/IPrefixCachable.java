package cache.prefix;

import cache.core.ICachable;

/**
 * 同时实现了prefixHashCode的ICachable类
 * 用于某些应用下，如果查找不到Key对应的Value，则转而查找Key的子集对应的Value。
 * @author aleck
 *
 */
public interface IPrefixCachable extends ICachable {
	/**
	 * 满足前缀关系的HashCode
	 * @return
	 */
	public PrefixHashCode prefixHashCode();
	
	/**
	 * 判断是否是某一个对象的prefix
	 * @param that
	 * @return
	 */
	public boolean isPrefixOf(IPrefixCachable that);
}
