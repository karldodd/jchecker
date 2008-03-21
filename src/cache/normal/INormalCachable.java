package cache.normal;

import cache.core.ICachable;

/**
 * 一般的可以Cache的类型
 * 最好是Immutable的，因为在Cacher里面存储了若干个INormalCachable的实例，并通过一定结构管理。
 * 如果Key是能够修改的，则可能在管理的时候出现没有update的情况，而实时update的代价太高。
 * @author aleck
 *
 */
public interface INormalCachable extends ICachable {

}
