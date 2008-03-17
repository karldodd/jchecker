package cache;

public interface IPrefixCachable extends ICachable {
	/**
	 * 满足前缀关系的HashCode
	 * @return
	 */
	public PrefixHashCode prefixHashCode();
}
