/**
 * 
 */
package prover.impl.fopic.determination.theory.uif.egraph.core;
import prover.impl.fopic.determination.theory.uif.egraph.utils.DisjointSetImpl.*;
/**
 * @author KarlHe
 * 
 */
public interface FunctionalElement extends IEgraphElement {
	public String getFunctionName();
	public int getParamSize();
	public AtomicElement[] getParams();	
}
