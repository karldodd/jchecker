package prover.impl.xxx.egraph.utils.DisjointSetImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Kaiduo He
 *
 * @param <K> 原子类，注意一定要正确覆盖其hashCode()方法
 */
public class DisjointSetEnvironment<K> {
	private List<K> currentAtoms;
	private List<DisjointSet<K>> currentSets;

	// Map: atom->node
	private Map<K, Node<K>> atomToNodeMap;
//	private Map<Node<K>,DisjointSet<K>> nodeToSetMap;

	public DisjointSetEnvironment(){
		currentAtoms=new LinkedList<K>();
		currentSets=new LinkedList<DisjointSet<K>>();
		atomToNodeMap=new HashMap<K, Node<K>>();
	}
	
	public DisjointSet<K> makeSet(K atom) {
		DisjointSet<K> ds = new DisjointSet<K>(atom);
		currentAtoms.add(atom);
		currentSets.add(ds);
		atomToNodeMap.put(atom, ds.getHeadNode());
		return ds;
	}

	/**
	 * 通过原子atom找寻它所属的集合，此处实现了路径压缩算法
	 * @param atom
	 * @return
	 */
	public DisjointSet<K> findSet(K atom) {
		Node<K> n=atomToNodeMap.get(atom);
		ArrayList<Node<K>> tempList=new ArrayList<Node<K>>();
		while(n.parent!=null){
			tempList.add(n);
			n=n.parent;
		}
		if(n instanceof HeadNode){
			for(Node<K> node : tempList){
				node.parent=n;
			}
			return ((HeadNode<K>)n).getMyDisjointSet();
		}
		System.err.println("Error! Unexpected execution! Null value returned when findSet()");
		return null;
	}

	public DisjointSet<K> union(DisjointSet<K> ds1, DisjointSet<K> ds2) {
		if(ds1.getSize()>=ds2.getSize()){
			//ds2并入ds1
			currentSets.remove(ds2);
			
		}
		else{
			
		}
		return null;
	}
}
