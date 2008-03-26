package tokens;

import java.lang.*;
import java.util.*;

/**
 * 运算表达式类
 * 
 * poor design，主要问题是占用空间较大，应予以重构
 * 
 * @author He Kaiduo
 */
public class Expression implements Cloneable {

	/**
	 * 数字
	 */
	public int num;

	/**
	 * 变量
	 */
	public Variable v;

	/**
	 * 函数
	 */
	public FunctionCall fc;

	/**
	 * 运算表达式类型
	 */
	public ExpType type;

	/**
	 * 运算表达式的左侧表达式
	 */
	public Expression l;

	/**
	 * 运算表达式的右侧表达式
	 */
	public Expression r;

	/**
	 * 数字类型的构造函数
	 * 
	 * @param num
	 *            数字
	 */
	public Expression(int num) {
		this.num = num;
		this.v = null;
		this.type = ExpType.num;
		this.l = null;
		this.r = null;
		this.fc = null;
	}

	/**
	 * 变量类型的构造函数
	 * 
	 * @param v
	 *            变量
	 */
	public Expression(Variable v) {
		this.v = v;
		this.num = Integer.MIN_VALUE;
		this.type = ExpType.var;
		this.l = null;
		this.r = null;
		this.fc = null;
	}

	/**
	 * 函数调用类型的构造函数
	 * 
	 * @param fc
	 */
	public Expression(FunctionCall fc) {
		this.fc = fc;
		this.v = null;
		this.num = Integer.MIN_VALUE;
		this.type = ExpType.function;
		this.l = null;
		this.r = null;
	}

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
	public Expression(Expression l, Expression r, ExpType type) {
		this.num = Integer.MIN_VALUE;
		this.v = null;
		this.type = type;
		this.l = l;
		this.r = r;
		this.fc = null;
	}

	/**
	 * 将表达式中的特定变量替换为特定表达式，返回一个新的表达式
	 * 
	 * @param v
	 *            需要替换的变量
	 * @param e
	 *            替换的表达式
	 * @return 新的运算表达式
	 */
	public Expression substitute(Variable v, Expression e) {
		switch (type) {
		case num:
			return this.clone();
		case var:
			if (this.v.getName().equals(v.getName())) {
				return e.clone();
			} else {
				return this.clone();
			}
		case plus:
			return new Expression(l.substitute(v, e), r.substitute(v, e),
					ExpType.plus);
		case minus:
			return new Expression(l.substitute(v, e), r.substitute(v, e),
					ExpType.minus);
		case multiply:
			return new Expression(l.substitute(v, e), r.substitute(v, e),
					ExpType.multiply);
		case negative:
			return new Expression(l.substitute(v, e), null, ExpType.negative);
		default:
			System.err.println("Warning: unexpected ExpType");
			return new Expression(Integer.MIN_VALUE);
		}
	}

	/**
	 * 深拷贝函数
	 * 
	 * @return 运算表达式的拷贝
	 */
	protected Expression clone() {
		switch (type) {
		case num:
			return new Expression(this.num);
		case var:
			return new Expression(this.v.clone());
		case plus:
			return new Expression(l.clone(), r.clone(), ExpType.plus);
		case minus:
			return new Expression(l.clone(), r.clone(), ExpType.minus);
		case multiply:
			return new Expression(l.clone(), r.clone(), ExpType.multiply);
		case negative:
			return new Expression(l.clone(), null, ExpType.negative);
		case function:
			return new Expression(fc.clone());
		default:
			System.err.println("Warning: unexpected ExpType");
			return new Expression(Integer.MIN_VALUE);
		}
	}

	public String toString() {
		switch (type) {
		case num:
			return num + "";
		case var:
			return v.getName();
		case plus:
			return "(" + l.toString() + "+" + r.toString() + ")";
		case minus:
			return "(" + l.toString() + "-" + r.toString() + ")";
		case multiply:
			return "(" + l.toString() + "*" + r.toString() + ")";
		case negative:
			return "(-" + l.toString() + ")";
		case function:
			return fc.toString();
		default:
			System.err.println("Warning: unexpected ExpType");
			return "Expression";
		}
	}

	public String toLFString() {
		switch (type) {
		case num:
			if (num >= 0)
				return num + "";
			else
				return "(- 0 " + num * (-1) + ")";
		case var:
			return v.getName();
		case plus:
			return "(+ " + l.toLFString() + " " + r.toLFString() + ")";
		case minus:
			return "(- " + l.toLFString() + " " + r.toLFString() + ")";
		case multiply:
			return "(* " + l.toLFString() + " " + r.toLFString() + ")";
		case negative:
			return "(- " + l.toLFString() + ")";
		default:
			System.err.println("Warning: unexpected ExpType");
			return "Expression";
		}
	}

	public String toFociString() {
		switch (type) {
		case num:
			return num + "";
		case var:
			return v.getName();
		case plus:
			return "+ [" + l.toFociString() + " " + r.toFociString() + "]";
		case minus:
			// System.err.println("Warning: foci might not support minus
			// expression.");
			return "+ [" + l.toFociString() + " * -1 " + r.toFociString() + "]";
		case multiply:
			if (l.type == ExpType.num)
				return "* " + l.toFociString() + " " + r.toFociString();
			else if (r.type == ExpType.num)
				return "* " + r.toFociString() + " " + l.toFociString();
			else {
				System.err
						.println("Warning: foci might not support complex multiply expression.");
				return "* " + l.toFociString() + " " + r.toFociString();
			}
		case negative:
			// System.err.println("Warning: foci might not support negative
			// expression.");
			return "* -1 " + l.toFociString() + " ";
		default:
			return "Expression";
		}
	}

	public String toFociString(Map<String, String> map) {
		switch (type) {
		case num:
			return num + "";
		case var:
			return map.containsKey(v.getName()) ? map.get(v.getName()) : v
					.getName();
		case plus:
			return "+ [" + l.toFociString(map) + " " + r.toFociString(map)
					+ "]";
		case minus:
			// System.err.println("Warning: foci might not support minus
			// expression.");
			return "+ [" + l.toFociString(map) + " * -1 " + r.toFociString(map)
					+ "]";
		case multiply:
			if (l.type == ExpType.num)
				return "* " + l.toFociString(map) + " " + r.toFociString(map);
			else if (r.type == ExpType.num)
				return "* " + r.toFociString(map) + " " + l.toFociString(map);
			else {
				System.err
						.println("Warning: foci might not support complex multiply expression.");
				return "* " + l.toFociString(map) + " " + r.toFociString(map);
			}
		case negative:
			// System.err.println("Warning: foci might not support negative
			// expression.");
			return "* -1 " + l.toFociString(map) + " ";
		default:
			return "Expression";
		}
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Expression))
			return false;
		Expression e = (Expression) obj;
		if (this.type != e.type)
			return false;
		switch (this.type) {
		case num:
			return this.num == e.num;
		case var:
			return this.v.equals(e.v);
		case plus:
		case minus:
		case multiply:
			return this.l.equals(e.l) && this.r.equals(e.r);
		case negative:
			return this.l.equals(e.l);
		case function:
			return this.fc.equals(e.fc);
		default:
			return false;
		}
	}
	// change all variables 'a' to 'a_suf'
	/*
	 * public void addSuffix(String suf) {
	 * //num,var,plus,minus,multiply,negative switch(type){ case num:break;//do
	 * nothing as a number case var:v.addSuffix(suf);break; case
	 * negative:l.addSuffix(suf);break; default: //plus,minus,multiply
	 * l.addSuffix(suf); r.addSuffix(suf); } }
	 */
	/*
	 * case var:return v.getName();break; case plus:return
	 * "("+l.toString()+"+"+r.toString()+")";break; case minus:return
	 * "("+l.toString()+"-"+r.toString()+")";break; case multiply:return
	 * "("+l.toString()+"-"+r.toString()+")";break; case negative:return
	 * "(-"+l.toString()+")";break;
	 */
}
