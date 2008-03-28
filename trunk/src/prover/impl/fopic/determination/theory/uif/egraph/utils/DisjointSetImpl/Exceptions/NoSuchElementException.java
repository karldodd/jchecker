/**
 * 
 */
package prover.impl.xxx.egraph.utils.DisjointSetImpl.Exceptions;

/**
 * 试图从Disjoint Set Environment中查找不存在的元素时抛出的异常
 * @author KarlHe
 *
 */
public class NoSuchElementException extends Exception {
	public NoSuchElementException() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "No such element in current disjoint sets.";
	}
}
