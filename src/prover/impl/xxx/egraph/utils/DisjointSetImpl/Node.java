package prover.impl.xxx.egraph.utils.DisjointSetImpl;

/**
 * 不相交森林中的结点
 * @author Kaiduo He
 *
 */
public class Node<K> {
	private K stuff;
	protected Node<K> nextNode;
	protected Node<K> parent;
	
	protected Node(K o){
		stuff=o;
		nextNode=null;
		parent=null;
	}
	
	public K getK(){
		return stuff;
	}
}
