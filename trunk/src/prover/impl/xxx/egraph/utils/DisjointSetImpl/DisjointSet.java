package prover.impl.xxx.egraph.utils.DisjointSetImpl;

import java.lang.annotation.Inherited;
import java.util.Iterator;


/**
 * 实现不相交集合的数据结构，采用不相交集合森林算法并在其基础上添加遍历集合的链表项
 * 
 * @author Kaiduo He
 *
 */

public class DisjointSet<K> implements Iterable<Node<K>>{
	private Node<K> headNode;
	private int size;
	protected DisjointSet(K atom){
		this.size=1;
		headNode=new HeadNode<K>(this,atom);
	}
	
	public Node<K> getHeadNode(){
		return headNode;
	}
	
	@Override public int hashCode(){
		return headNode.hashCode();
	}	
	
//	@Override public boolean equals(Object o){
//		if(o instanceof DisjointSet){
//			
//			(DisjointSet<K>(o))
//		}
//		return false;
//	}
	
	public int getSize(){
		return size;
	}
	
	public Iterator<Node<K>> iterator(){
		return new DisjointSetIterator<Node<K>>(this.headNode);
	}
}
