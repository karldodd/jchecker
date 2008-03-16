package cache;

/**
 * Interface of Cachable Object.
 * 
 * Warning: the object should be immutable, 
 * since we just calculate the hashCode / eqvHashCode at creating time.
 * Once and only once.
 *  
 * @author aleck
 *
 */
public interface ICachable {
	// In fact, these methods are implemented by Object.
	// here, just for reminding user, that these 2 methods are critical to cacher.
	public boolean equals();
	public int hashCode();
}
