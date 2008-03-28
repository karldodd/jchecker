/**
 * 
 */
package tokens;

/**
 * @author KarlHe
 * 
 */
public class NegativeExpression extends Expression {
	/**
	 * 运算表达式的左侧表达式
	 */
	public Expression innerExp;

	/**
	 * 运算类型的构造函数
	 * 
	 * @param l
	 *            运算的左侧表达式
	 * @param r
	 *            运算的右侧表达式
	 * @param type
	 *            运算类型
	 */
	public NegativeExpression(Expression innerExp) {
		this.innerExp = innerExp;
	}

	@Override
	public NegativeExpression substitute(Variable v, Expression e) {
		// TODO Auto-generated method stub
		return new NegativeExpression(innerExp.substitute(v, e));
	}

	@Override
	public NegativeExpression clone() {
		// TODO Auto-generated method stub
		return new NegativeExpression(innerExp.clone());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(-" + innerExp.toString() + ")";
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof NegativeExpression) {
			NegativeExpression ne = (NegativeExpression) obj;
			return innerExp.equals(ne.innerExp);
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.toString().hashCode();
	}
}
