/**
 * 
 */
package prover.impl.xxx.egraph.utils.DisjointSetImpl;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Administrator
 *
 */
public class DisjointSetIterator<Node<K>> implements Iterator<Node<K>> {

	Node<K> nextNode;

	public DisjointSetIterator(DisjointSet<K> ds){
		nextNode=ds.getHeadNode();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return nextNode==null;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Node<K> next() {
		// TODO Auto-generated method stub
		if(nextNode!=null){
			Node<K> temp=nextNode;
			nextNode=nextNode.nextNode;
			return temp;
		}
		else
			throw new NoSuchElementException();		
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
