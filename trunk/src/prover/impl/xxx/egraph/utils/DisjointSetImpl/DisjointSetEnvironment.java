package prover.impl.xxx.egraph.utils.DisjointSetImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 不相交集合的环境类，管理此数据结构
 * 
 * @author Kaiduo He
 * 
 * @param <K>
 *            原子类，注意一定要正确覆盖其hashCode()方法
 */
public class DisjointSetEnvironment<K> {
	private List<K> currentAtoms;
	private List<DisjointSet<K>> currentSets;

	// Map: atom->node
	private Map<K, Node<K>> atomToNodeMap;

	// private Map<Node<K>,DisjointSet<K>> nodeToSetMap;

	public DisjointSetEnvironment() {
		currentAtoms = new LinkedList<K>();
		currentSets = new LinkedList<DisjointSet<K>>();
		atomToNodeMap = new HashMap<K, Node<K>>();
	}

	/**
	 * 从原子构建初始不相交集合的方法
	 * 
	 * @param atom
	 * @return
	 */
	public DisjointSet<K> makeSet(K atom) {
		if (atom == null)
			return null;
		if (atomToNodeMap.containsKey(atom)) {
			return null;
			//throw new Exception();
			//to be impl
		}
		DisjointSet<K> ds = new DisjointSet<K>(atom);
		currentAtoms.add(atom);
		currentSets.add(ds);

		atomToNodeMap.put(atom, ds.getHeadNode());
		return ds;
	}

	// public DisjointSet<K> makeSet(List<K> atoms){
	// return null;
	// }

	/**
	 * 通过原子atom找寻它所属的集合，此处实现了路径压缩算法
	 * 
	 * @param atom
	 * @return
	 */
	public DisjointSet<K> findSet(K atom) {
		if (atom == null)
			return null;
		Node<K> n = atomToNodeMap.get(atom);
		if (n == null)
			return null;

		ArrayList<Node<K>> tempList = new ArrayList<Node<K>>();
		while (n.parent != null) {
			tempList.add(n);
			n = n.parent;
		}
		if (n.isHeadNode()) {
			for (Node<K> node : tempList) {
				node.parent = n;
			}
			return ((HeadNode<K>) n).getMyDisjointSet();
		}
		System.err
				.println("Error! Unexpected execution! Null value returned when findSet()");
		return null;
	}

	/**
	 * 不相交集合求并的方法。 注意方法执行后，两个参数中的某一个将失效。
	 * 
	 * @param ds1
	 * @param ds2
	 * @return
	 */
	public DisjointSet<K> union(DisjointSet<K> ds1, DisjointSet<K> ds2) {
		//throw new Exception();
		//to be impl 若为同一个集合，若集合相交
		if (ds1.getSize() >= ds2.getSize()) {
			// ds2并入ds1
			currentSets.remove(ds2);
			ds2.getHeadNode().parent = ds1.getHeadNode();
			ds2.getHeadNode().setAsNormalNode();
			ds1.getLastNode().nextNode = ds2.getHeadNode();
			ds1.setSize(ds1.getSize() + ds2.getSize());
			ds2 = null;
			return ds1;
		} else {
			// ds1并入ds2
			currentSets.remove(ds1);
			ds1.getHeadNode().parent = ds2.getHeadNode();
			ds1.getHeadNode().setAsNormalNode();
			ds2.getLastNode().nextNode = ds1.getHeadNode();
			ds2.setSize(ds1.getSize() + ds2.getSize());
			ds1 = null;
			return ds2;
		}
	}

	public List<DisjointSet<K>> getCurrentSets() {
		return this.currentSets;
	}

}
