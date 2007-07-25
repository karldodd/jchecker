package tokens;
import java.lang.*;
import java.util.*;
public class EvaluationSentence extends Sentence{
	Variable v;
	Expression e;
	public EvaluationSentence(Variable v, Expression e){
		this.v=v;
		this.e=e;
	}
	public String toString(){
		return v.getName()+"="+e.toString()+";";
	}
}