package prover.impl.xxx.egraph.utils.DisjointSetImpl;


/**
 * 实现不相交集合的数据结构，采用不相交集合森林算法并在其基础上添加遍历集合的链表项
 * 
 * @author Kaiduo He
 *
 */

public class DisjointSet {
	private Node headNode;
	private int size;
	private DisjointSet(Object atom){
		this.size=1;
		headNode=new Node(atom);
		headNode.nextNode=null;
		headNode.parent=null;
	}
	
	public static DisjointSet makeSet(Object atom){
		return new DisjointSet(atom);
	}
	
	public static DisjointSet findSet(Object atom){
		///? how to find the node by atom?
		/// Where to preserve a hash set from atom to node?
		return null;
	}
	
	public static DisjointSet union(DisjointSet ds1, DisjointSet ds2){
		return null;
	}
}
