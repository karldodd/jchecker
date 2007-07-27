package tokens;
import java.lang.*;
import java.util.*;
public class Expression{
	public int num;
	public Variable v;
	public ExpType type;
	public Expression l;//left
	public Expression r;//right
	
	public Expression(int num){
		this.num=num;
		this.v=null;
		this.type=ExpType.num;
		this.l=null;
		this.r=null;
	}
	public Expression(Variable v){
		this.v=v;
		this.num=Integer.MIN_VALUE;
		this.type=ExpType.var;
		this.l=null;
		this.r=null;
	}
	public Expression(Expression l,Expression r, ExpType type)
	{
		this.num=Integer.MIN_VALUE;
		this.v=null;
		this.type=type;
		this.l=l;
		this.r=r;
	}
	public String toString(){
		switch(type){
			case num:return num+"";
			case var:return v.getName();
			case plus:return "("+l.toString()+"+"+r.toString()+")";
			case minus:return "("+l.toString()+"-"+r.toString()+")";
			case multiply:return "("+l.toString()+"*"+r.toString()+")";
			case negative:return "(-"+l.toString()+")";
			default:
				System.err.println("Warning: unexpected ExpType"); 
				return "Expression";
		}
	}
	
	public String toLFString(){
		switch(type){
			case num:return num+"";
			case var:return v.getName();
			case plus:return "(+ "+l.toLFString()+" "+r.toLFString()+")";
			case minus:return "(- "+l.toLFString()+" "+r.toLFString()+")";
			case multiply:return "(* "+l.toLFString()+" "+r.toLFString()+")";
			case negative:return "(- "+l.toLFString()+")";
			default:
				System.err.println("Warning: unexpected ExpType"); 
				return "Expression";
		}
	}

	public String toFociString(){
		switch(type){
			case num:return num+"";
			case var:return v.getName();
			case plus:return "+ ["+l.toFociString()+" "+r.toFociString()+"]";
			case minus:
				//System.err.println("Warning: foci might not support minus expression.");
				return "+ ["+l.toFociString()+" * -1 "+r.toFociString()+"]";
			case multiply:
				if(l.type==ExpType.num)
					return "* "+l.toFociString()+" "+r.toFociString();
				else if(r.type==ExpType.num)
					return "* "+r.toFociString()+" "+l.toFociString();
				else{
					System.err.println("Warning: foci might not support complex multiply expression.");
					return "* "+l.toFociString()+" "+r.toFociString();
				}
			case negative:
				//System.err.println("Warning: foci might not support negative expression.");
				return "* -1 "+l.toFociString()+" ";
			default: return "Expression";
		}
	}
	/*case var:return v.getName();break;
			case plus:return "("+l.toString()+"+"+r.toString()+")";break;
			case minus:return "("+l.toString()+"-"+r.toString()+")";break;
			case multiply:return "("+l.toString()+"-"+r.toString()+")";break;
			case negative:return "(-"+l.toString()+")";break;*/
}