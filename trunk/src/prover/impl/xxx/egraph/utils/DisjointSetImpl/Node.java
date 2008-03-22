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
	
	public boolean isHeadNode()
	{
		return (parent==null)&&(this instanceof HeadNode);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Node)
			return getK().equals(((Node)obj).getK());
		return false;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return stuff.hashCode();
	}
}
