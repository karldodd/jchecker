/**
 * 
 */
package tokens;

/**
 * 表示函数调用的token
 * 
 * @author KarlHe
 * 
 */
public class FunctionCall implements Cloneable {
	protected String functionName;
	protected Variable[] params;
	protected int paramSize = -1;

	/**
	 * 函数调用类实例的构造函数
	 * 
	 * @param functionName
	 *            函数名
	 * @param vars
	 *            参数名
	 */
	public FunctionCall(String functionName, String[] vars) {
		this.functionName = functionName;
		params = new Variable[vars.length];
		for (int i = 0; i < vars.length; i++) {
			params[i] = new Variable(vars[i]);
		}
	}
	
	public FunctionCall(String functionName, Variable[] vars) {
		this.functionName = functionName;
		params = new Variable[vars.length];
		for (int i = 0; i < vars.length; i++) {
			params[i] = vars[i].clone();
		}
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public Variable[] getVariables() {
		return this.params;
	}

	public String[] getVariableStrings(){
		Variable[] vars=getVariables();
		String[] strs=new String[this.paramSize];
		for(int i=0;i<this.paramSize;i++){
			strs[i]=vars[i].getName();
		}
		return strs;
	}
	/**
	 * 获取函数的参数个数
	 * 
	 * @return 函数参数个数
	 */
	public int getParamSize() {
		if (paramSize == -1) {
			paramSize = this.params.length;
		}
		return paramSize;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int counter = 0;
		for (Variable v : params) {
			counter += v.hashCode();
		}
		return this.functionName.hashCode() + getParamSize() + counter;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof FunctionCall) {
			FunctionCall o = (FunctionCall) obj;
			if (o.getFunctionName().equals(this.getFunctionName())
					&& o.getParamSize() == this.getParamSize()) {
				for(int i=0;i<o.getParamSize();i++){
					if(!o.params[i].equals(this.params[i]))
						return false;
				}
				return true;
			}
		}
		return false;
	}
}
