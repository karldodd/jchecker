package cache.prefix;

import java.util.Iterator;

/**
 * 定义一类保持前缀关系的HashCode结构
 * @author aleck
 *
 */
public class PrefixHashCode extends HashCode implements Iterable<Integer> {
	/**
	 * 使用一个数组来保存相应的hashCode序列
	 */
	protected int[] codes;

	@Override
	public Iterator<Integer> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
