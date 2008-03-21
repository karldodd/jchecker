package prover.impl.xxx.egraph.utils.DisjointSetImpl;

/**
 * 不相交森林中的结点
 * @author Kaiduo He
 *
 */
public class Node {
	private Object stuff;
	protected Node nextNode;
	protected Node parent;
	
	protected Node(Object o){
		stuff=o;
	}
}
