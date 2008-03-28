/**
 * 
 */
package tokens;

import prover.impl.fopic.determination.theory.uif.egraph.core.AtomicElement;
import prover.impl.fopic.determination.theory.uif.egraph.core.FunctionalElement;

/**
 * @author KarlHe
 *
 */
public class SimpleFunctionCall extends FunctionCall implements FunctionalElement{
	FunctionCall fc;
	Variable[] vars;//Compromise to efficiency
	public SimpleFunctionCall(String functionName, Variable[] vars){
		super(functionName,vars);
	}
	
	@Override
	public String getFunctionName() {
		// TODO Auto-generated method stub
		return fc.getFunctionName();
	}
	
	@Override
	public int getParamSize() {
		// TODO Auto-generated method stub
		return fc.getParamSize();
	}
	
	@Override
	public AtomicElement[] getParams() {
		// TODO Auto-generated method stub
		return this.vars;
	}
}
