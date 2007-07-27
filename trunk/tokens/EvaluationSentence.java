package tokens;
import java.lang.*;
import java.util.*;
public class EvaluationSentence extends Sentence implements EdgeLabel{
	Variable v;
	Expression e;
	public EvaluationSentence(Variable v, Expression e){
		this.v=v;
		this.e=e;
	}
	public String toString(){
		return v.getName()+"="+e.toString()+";";
	}
	public String toLFString(){
		return "(= "+v.getName()+" "+e.toLFString()+" )";
	}

	public String toFociString(){
		return "= "+v.getName()+" "+e.toFociString();
	}
}