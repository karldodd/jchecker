package cache;

public class NormalRecoverResult<V> extends AbstractRecoverResult<V> {
	private V value;
	
	NormalRecoverResult() {
	}
	
	NormalRecoverResult(V value) {
		this.value = value;
	}
	
	public V getValue() {
		return value;
	}
}
