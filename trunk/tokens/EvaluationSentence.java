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

	public String toFociString(Map<String,String> map){
		String exp=e.toFociString(map);
		if(map.containsKey(v.getName()))
			map.put(v.getName(),map.get(v.getName())+"_suf");
		else
			map.put(v.getName(),v.getName()+"_suf");
		return "= "+map.get(v.getName())+" "+exp;
	}
}