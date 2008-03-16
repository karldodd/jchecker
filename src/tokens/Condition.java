package tokens;

import java.lang.*;
import java.util.*;

/**
*原子条件式类
*
*@author He Kaiduo
*/
public class Condition extends Sentence implements Cloneable{
	
	/**
	*原子条件式的左侧表达式
	*/
	public Expression l;

	/**
	*原子条件式的右侧表达式
	*/
	public Expression r;

	/**
	*原子条件式的类型
	*/
	public ConType type;

	/**
	*构造永真式或永假式
	*
	*@param c 永真式或永假式标识
	*/
	public Condition(boolean c){
		if(c)this.type=ConType.T;
		else this.type=ConType.F;
	}
	
	/**
	*构造原子条件式
	*
	*@param l 左侧表达式
	*@param r 右侧表达式
	*@param type 条件式类型
	*/
	public Condition(Expression l, Expression r, ConType type) {
		this.l = l;
		this.r = r;
		this.type = type;
	}

	/**
	*深拷贝函数
	*
	*@return 原子条件式拷贝
	*/
	public Condition clone(){
		if(this.type==ConType.T||this.type==ConType.F)
			return new Condition(null,null,type);
		return new Condition(l.clone(),r.clone(),type);
	}

	/**
	*对原子条件式中的变量作代换
	*
	*@param v 需要代换的变量
	*@param e 代换的表达式
	*@return 代换后的原子条件式
	*/
	public Condition substitute(Variable v, Expression e){
		if(this.type==ConType.T||this.type==ConType.F)return this.clone();
		return new Condition(l.substitute(v,e),r.substitute(v,e),type);
	}

	public String toString() {
		switch (type) {
		// public enum ConType
		// {equal,notequal,larger,smaller,equallarger,equalsmaller}
		case T:
			return "(true)";
		case F:
			return "(false)";
		case equal:
			return "(" + l.toString() + "==" + r.toString() + ")";
		case notequal:
			return "(" + l.toString() + "!=" + r.toString() + ")";
		case larger:
			return "(" + l.toString() + ">" + r.toString() + ")";
		case smaller:
			return "(" + l.toString() + "<" + r.toString() + ")";
		case equallarger:
			return "(" + l.toString() + ">=" + r.toString() + ")";
		case equalsmaller:
			return "(" + l.toString() + "<=" + r.toString() + ")";
		default:
			return "condition";
		}
	}

	public String toLFString() {
		switch (type) {
		// public enum ConType
		// {T,F,equal,notequal,larger,smaller,equallarger,equalsmaller}
		case T:
			return "(true)";
		case F:
			return "(false)";
		case equal:
			return "(" + "= " + l.toLFString() + " " + r.toLFString() + ")";
		case notequal:
			return "(" + "<> " + l.toLFString() + " " + r.toLFString() + ")";
		case larger:
			return "(" + "> " + l.toLFString() + " " + r.toLFString() + ")";
		case smaller:
			return "(" + "< " + l.toLFString() + " " + r.toLFString() + ")";
		case equallarger:
			return "(" + ">= " + l.toLFString() + " " + r.toLFString() + ")";
		case equalsmaller:
			return "(" + "<= " + l.toLFString() + " " + r.toLFString() + ")";
		default:
			return "condition";
		}
	}

	public String toFociString() {
		switch (type) {
		// public enum ConType
		// {T,F,equal,notequal,larger,smaller,equallarger,equalsmaller}
		case T:
			return "true";
		case F:
			return "false";
		case equal:
			return "" + "= " + l.toFociString() + " " + r.toFociString() + " ";
		case notequal:
			return "" + "~ =" + l.toFociString() + " " + r.toFociString() + "";
		case larger:
			return "" + "<= " + r.toFociString() + " + [" + l.toFociString() + " -1 ]";
		case smaller:
			return "" + "<= " + l.toFociString() + " + [" + r.toFociString() + " -1 ]";
		case equallarger:
			return "" + "<= " + r.toFociString() + " " + l.toFociString() + "";
		case equalsmaller:
			return "" + "<= " + l.toFociString() + " " + r.toFociString() + "";
		default:
			return "condition";
		}
	}

	public String toFociString(Map<String,String> map) {
		switch (type) {
		// public enum ConType
		// {T,F,equal,notequal,larger,smaller,equallarger,equalsmaller}
		case T:
			return "true";
		case F:
			return "false";
		case equal:
			return "" + "= " + l.toFociString(map) + " " + r.toFociString(map) + " ";
		case notequal:
			return "" + "~ =" + l.toFociString(map) + " " + r.toFociString(map) + "";
		case larger:
			return "" + "<= " + r.toFociString(map) + " + [" + l.toFociString(map) + " -1 ]";
		case smaller:
			return "" + "<= " + l.toFociString(map) + " + [" + r.toFociString(map) + " -1 ]";
		case equallarger:
			return "" + "<= " + r.toFociString(map) + " " + l.toFociString(map) + "";
		case equalsmaller:
			return "" + "<= " + l.toFociString(map) + " " + r.toFociString(map) + "";
		default:
			return "condition";
		}
	}

	public void negateSelf() {
		// public enum ConType
		// {equal,notequal,larger,smaller,equallarger,equalsmaller}
		if (this.type == ConType.equal)
			this.type = ConType.notequal;
		else if (this.type == ConType.notequal)
			this.type = ConType.equal;
		else if (this.type == ConType.larger)
			this.type = ConType.equalsmaller;
		else if (this.type == ConType.smaller)
			this.type = ConType.equallarger;
		else if (this.type == ConType.equallarger)
			this.type = ConType.smaller;
		else if (this.type == ConType.equalsmaller)
			this.type = ConType.larger;
		else if (this.type == ConType.T)
			this.type = ConType.F;
		else if (this.type == ConType.F)
			this.type = ConType.T;
		else{
			System.err.println("Unexpected condition type. Exit");
			System.exit(1);
		}
	}
}
