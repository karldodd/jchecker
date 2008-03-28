/**
 * 
 */
package prover.impl.fopic.determination.theory.uif.egraph.utils.DisjointSetImpl.Exceptions;

/**
 * 当Disjoint Set Environment中试图插入重复元素时抛出的异常
 * @author KarlHe
 *
 */
public class DuplicateElementException extends Exception {
	public DuplicateElementException() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "DisjointSet does not support duplicate elements.";
	}
}
