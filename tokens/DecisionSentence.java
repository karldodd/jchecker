package tokens;
import java.lang.*;
import java.util.*;
public class DecisionSentence extends Sentence{
	public AdvCondition c;
	public ArrayList<Sentence> sentences;
	public ArrayList<Sentence> elsesentences;
	public DecisionType type;
	public DecisionSentence(AdvCondition c, DecisionType type){
		this.c=c;
		this.type=type;
		this.sentences=new ArrayList<Sentence>();
		if(type==DecisionType.ifsentence)this.elsesentences=new ArrayList<Sentence>();
	}
	public void add(Sentence s)
	{
		this.sentences.add(s);
		//this.sentences.Add(s);
		//sentences.Add(s);
	}
	public void addAll(ArrayList<Sentence> al)
	{
		this.sentences.addAll(al);
	}
	public void addForElse(Sentence s)
	{
		this.elsesentences.add(s);
		//this.sentences.Add(s);
		//sentences.Add(s);
	}
	public void addAllForElse(ArrayList<Sentence> al)
	{
		this.elsesentences.addAll(al);
	}
	public String toString(){
		StringBuilder temp;
		if(this.type==DecisionType.ifsentence)
			temp=new StringBuilder("if("+c.toString()+")"+"{");
		else
			temp=new StringBuilder("while("+c.toString()+")"+"{");
		for (Sentence s: sentences)
		{
			temp.append(s.toString());
		}
		temp.append("}");
		if((this.type==DecisionType.ifsentence)&&(this.elsesentences.size()>0))
		{
			temp.append("else{");
			for (Sentence s: elsesentences)
			{
				temp.append(s.toString());
			}
			temp.append("}");
		}
		return temp.toString();
	}
}
