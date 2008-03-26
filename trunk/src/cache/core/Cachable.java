package cache.core;

/**
 * Interface of Cachable Object.
 * 
 * Warning: the object should be <b>immutable<b>, 
 * since we just calculate the hashCode at creating time.
 * Once and only once.
 *  
 * 我们需要保证如下的关系成立:
 *   A equals to B ==> A is equivalent to B ==> A.hashCode = B.hashCode
 * 
 * @author aleck
 *
 */
public abstract class Cachable {
	/**
	 * 生成HashCode
	 */
	public abstract int hashCode();
	/**
	 * 是否完全相等
	 * @param that
	 * @return
	 */
	public abstract boolean equals(Object that);
	/**
	 * 是否等价
	 * @param that
	 * @return
	 */
	public abstract boolean equivalent(Object that);
}
