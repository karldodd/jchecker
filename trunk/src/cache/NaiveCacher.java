package cache;

public class NaiveCacher<K extends ICachable, V> extends Cacher<K, V> {
	private NaiveCacher() {}
	
	@Override
	public Object cache(K key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object recover(K key) {
		// TODO Auto-generated method stub
		return null;
	}

}
