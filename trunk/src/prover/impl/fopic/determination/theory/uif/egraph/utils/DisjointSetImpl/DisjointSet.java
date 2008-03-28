package prover.impl.fopic.determination.theory.uif.egraph.utils.DisjointSetImpl;

import java.util.Iterator;


/**
 * 实现不相交集合的数据结构，采用不相交集合森林算法并在其基础上添加遍历集合的链表项
 * 
 * @author Kaiduo He
 *
 */

public class DisjointSet<K> implements Iterable<Node<K>>{
	private HeadNode<K> headNode;
	private int size;
	protected DisjointSet(K atom){
		this.size=1;
		headNode=new HeadNode<K>(this,atom);
	}
	
	public HeadNode<K> getHeadNode(){
		return headNode;
	}
	
	public Node<K> getLastNode(){
		Node<K> n=getHeadNode();
		while(n.nextNode!=null){
			n=n.nextNode;
		}
		return n;
	}
	
	@Override public int hashCode(){
		int hashValue=0;
		for (Node<K> n: this){
			hashValue+=n.hashCode();
		}
		return hashValue;
	}	
	
	@Override public boolean equals(Object o){
		if(o instanceof DisjointSet){
			if(this.hashCode()!=((DisjointSet<K>)(o)).hashCode())return false;
			for(Node<K> n : this){
				//throw new Exception;
				//to be impl
			}
			return true;
		}
		return false;
	}
	
	public int getSize(){
		return size;
	}
	
	protected void setSize(int size){
		this.size=size;
	}
	
	public Iterator<Node<K>> iterator(){
		return new DisjointSetIterator<K>(this);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer s=new StringBuffer();
		s.append("{");
		for (Node<K> n : this){
			s.append(n.toString()+", ");
		}
		s.append("}");
		return s.toString();
	}
}
