package tokens;
import java.lang.*;
import java.util.*;

/**
*条件判断语句类
*
*@author He Kaiduo
*/
public class DecisionSentence extends Sentence{
	
	/**
	*条件判断语句主体
	*/
	public AdvCondition c;

	/**
	*条件判断后的语句列表
	*/
	public ArrayList<Sentence> sentences;

	/**
	*条件判断else后的语句列表
	*/
	public ArrayList<Sentence> elsesentences;

	/**
	*条件判断的类型
	*/
	public DecisionType type;

	/**
	*根据主体和条件判断类型构造条件判断式
	*
	*@param c 条件判断式主体
	*@param type 条件判断类型
	*/
	public DecisionSentence(AdvCondition c, DecisionType type){
		this.c=c;
		this.type=type;
		this.sentences=new ArrayList<Sentence>();
		if(type==DecisionType.ifsentence)this.elsesentences=new ArrayList<Sentence>();
	}

	/**
	*添加一条语句
	*
	*@param s 需要添加的语句
	*/
	public void add(Sentence s)
	{
		this.sentences.add(s);
		//this.sentences.Add(s);
		//sentences.Add(s);
	}

	/**
	*添加一组语句
	*
	*@param al 需要添加的一组语句
	*/
	public void addAll(ArrayList<Sentence> al)
	{
		this.sentences.addAll(al);
	}

	/**
	*添加else后的一条语句
	*
	*@param s 需要添加的语句
	*/
	public void addForElse(Sentence s)
	{
		this.elsesentences.add(s);
		//this.sentences.Add(s);
		//sentences.Add(s);
	}

	/**
	*添加else后的一组语句
	*
	*@param al 需要添加的一组语句
	*/
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
