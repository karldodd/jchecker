package cache;

import java.util.HashMap;
import java.util.Map;

public class CacherManager {
	
	private Map<String, Cacher> cachers = new HashMap<String, Cacher>();
	
	/**
	 * Make it invisible, to prevent from creating any instance of this class.
	 */
	private CacherManager() {
	}
	
	/**
	 * register for cacher
	 * 
	 * @param name
	 * @param cacher
	 * @return
	 */
	public static boolean registerCacher(String name, Cacher cacher) {
		return true;
	}
	
	/**
	 * Find cacher by name
	 * 
	 * @param name
	 * @return
	 */
	public static Cacher findCacherByName(String name) {
		return null;
	}
}
