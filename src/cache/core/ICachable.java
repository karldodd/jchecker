package cache.core;

/**
 * Interface of Cachable Object.
 * 
 * Warning: the object should be <b>immutable<b>, 
 * since we just calculate the hashCode / eqvHashCode at creating time.
 * Once and only once.
 *  
 * @author aleck
 *
 */
public abstract interface ICachable {
	// In fact, it's implemented by Object.
	// here, just for reminding user, that it is critical to cacher.
	public boolean equals(Object that);
	public int hashCode();
}
