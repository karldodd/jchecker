package tokens;
import java.lang.*;
import java.util.*;
public class DeclarationSentence extends Sentence{
	VarType type;
	ArrayList<Variable> vars;
	public DeclarationSentence(VarType type){
		this.type=VarType.integer;
		this.vars=new ArrayList<Variable>();
	}
	public void add(Variable v){
		this.vars.add(v);
	}
	public String toString(){
		return "declaration sentence";
	}
}