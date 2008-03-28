/**
 * 
 */
package tokens;

/**
 * @author KarlHe
 * 
 */
public class CompositeExpression extends Expression {
	public static final short TYPE_PLUS = 0;
	public static final short TYPE_MINUS = 1;
	public static final short TYPE_MULTIPLY = 2;

	/**
	 * 运算表达式类型
	 */
	public short type;

	/**
	 * 运算表达式的左侧表达式
	 */
	public Expression l;

	/**
	 * 运算表达式的右侧表达式
	 */
	public Expression r;

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
	public CompositeExpression(Expression l, Expression r, short type) {
		this.type = type;
		this.l = l;
		this.r = r;
	}

	@Override
	public CompositeExpression substitute(Variable v, Expression e) {
		// TODO Auto-generated method stub
		return new CompositeExpression(l.substitute(v, e), r.substitute(v, e),
				type);
	}

	@Override
	public CompositeExpression clone() {
		// TODO Auto-generated method stub
		return new CompositeExpression(l.clone(), r.clone(), type);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		switch (type) {
		case TYPE_PLUS:
			return "(" + l.toString() + "+" + r.toString() + ")";
		case TYPE_MINUS:
			return "(" + l.toString() + "-" + r.toString() + ")";
		case TYPE_MULTIPLY:
			return "(" + l.toString() + "*" + r.toString() + ")";
		default:
			return "WRONG COMPOSITE EXP";
		}
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof CompositeExpression) {
			CompositeExpression ce = (CompositeExpression) obj;
			return type == ce.type && l.equals(ce.l) && r.equals(ce.r);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.toString().hashCode();
	}
}
