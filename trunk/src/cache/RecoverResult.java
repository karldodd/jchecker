/**
 * 返回结果的类型
 * 需要这个类型是因为使用PrefixCacher来recover一个key的时候，可能需要返回不止是value的信息。
 * 可能还有匹配的信息。
 * @author aleck
 *
 * @param <V>
 */
public class RecoverResult<V> {
	private V value;
	
	RecoverResult() {
	}
	
	RecoverResult(V value) {
		this.value = value;
	}
	
	public V getValue() {
		return value;
	}
}
