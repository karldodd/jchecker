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
	public abstract boolean equals();
	public abstract int hashCode();
	public HashCode longHashCode();
}
