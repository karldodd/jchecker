package tokens;

import java.lang.*;
import java.util.*;


public class Condition extends Sentence{
	public Expression l;

	public Expression r;

	public ConType type;

	public Condition(boolean c){
		if(c)this.type=ConType.T;
		else this.type=ConType.F;
	}
	
	public Condition(Expression l, Expression r, ConType type) {
		this.l = l;
		this.r = r;
		this.type = type;
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
		if (this.type == ConType.notequal)
			this.type = ConType.equal;
		if (this.type == ConType.larger)
			this.type = ConType.equalsmaller;
		if (this.type == ConType.smaller)
			this.type = ConType.equallarger;
		if (this.type == ConType.equallarger)
			this.type = ConType.smaller;
		if (this.type == ConType.equalsmaller)
			this.type = ConType.larger;
	}
}