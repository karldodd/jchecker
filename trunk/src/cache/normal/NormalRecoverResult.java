package cache.normal;

import cache.core.AbstractRecoverResult;

public class NormalRecoverResult<V> extends AbstractRecoverResult<V> {
	private V value;
	
	public NormalRecoverResult() {
	}
	
	public NormalRecoverResult(V value) {
		this.value = value;
	}
	
	public V getValue() {
		return value;
	}
}
