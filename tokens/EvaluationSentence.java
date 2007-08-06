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

	public String toFociString(Map<String,String> map,Map<String,String> revertMap){

		if(map==null)map=new TreeMap<String,String>();
		if(revertMap==null)map=new TreeMap<String,String>();

		//use the old map to replace the e part
		String exp=e.toFociString(map);

		//generate the new suffix and reflesh the map
		String withNewSuffix;
		do{
			withNewSuffix=v.getName()+"suf"+Variable.getRandomIntSuffix();
		}while(map.containsKey(withNewSuffix)||revertMap.containsKey(withNewSuffix));

		map.put(v.getName(),withNewSuffix);
		revertMap.put(withNewSuffix,v.getName());

		//the weakness of this approach is:
		//there might be some unforeseen variable whose name is "xxx_sufxxxxxx", just same as what you got.
		//then in foci result, if there is the "xxx_sufxxxxxx", we cannot tell whether it is real "xxx_sufxxxxxx" or "xxx".

		//In order to check it, when bumping into new variables in Exp of EvaluationSentence or AdvCondition, check if
		//their name are in revertMap. If so, regenerate the suffix for old items.

		return "= "+map.get(v.getName())+" "+exp;
	}

	public AdvCondition toAdvCondition(){
		Expression l=new Expression(this.v);
		Expression r=this.e;
		Condition c=new Condition(l,r,ConType.equal);
		return (new AdvCondition(c)).clone();
	}

	public AdvCondition getNegativeCopy(){
		return this.toAdvCondition().getNegativeCopy();
	}
}
