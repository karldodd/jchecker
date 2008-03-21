package cache;

/**
 * 返回结果的类型
 * 需要这个类型是因为使用PrefixCacher来recover一个key的时候，可能需要返回不止是value的信息。
 * 可能还有匹配的信息。
 * @author aleck
 *
 * @param <V>
 */
public abstract class AbstractRecoverResult<V> {
	public abstract V getValue();
}
