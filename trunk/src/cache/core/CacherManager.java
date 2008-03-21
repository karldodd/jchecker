package cache.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CacherManager {
	private static Random rand = new Random();
	
	@SuppressWarnings("unchecked")
	private static final Map<String, Cacher> cachersMap = new HashMap<String, Cacher>();
	
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
	public static void registerCacher(String name, Cacher cacher) {
		if (cachersMap.get(name) == null) {
			cachersMap.put(name, cacher);
		} else {
			throw new RuntimeException("name: " + name + 
					" is already be defined as: " + cachersMap.get(name));
		}
	}
	
	/**
	 * Find cacher by name
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Cacher findCacherByName(String name) {
		return cachersMap.get(name);
	}

	/**
	 * 返回一个随即的Cacher的名字
	 * @return
	 */
	public static String randName() {
		return String.format("%06d", rand.nextInt(1000000));
	}
}
