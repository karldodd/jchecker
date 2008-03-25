/**
 * 
 */
package prover.impl.xxx.egraph.utils.DisjointSetImpl.Exceptions;

/**
 * 试图在DisjointSetEnvironment中加入为null的对象时抛出此异常
 * @author KarlHe
 *
 */
public class NullElementException extends Exception {
	public NullElementException() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "DisjointSet does not support null elements.";
	}
}
