package tokens;
import java.lang.*;
import java.util.*;

/**
*声明语句类
*
*@author He Kaiduo
*/
public class DeclarationSentence extends Sentence{
	
	/**
	*声明语句的变量
	*/
	VarType type;

	/**
	*声明语句的变量列表
	*/
	ArrayList<Variable> vars;

	/**
	*根据变量类型构造声明语句
	*
	*@param type 变量类型
	*/
	public DeclarationSentence(VarType type){
		this.type=VarType.integer;
		this.vars=new ArrayList<Variable>();
	}

	/**
	*添加变量
	*
	*@param v 需要添加的变量
	*/
	public void add(Variable v){
		this.vars.add(v);
	}

	public String toString(){
		return "declaration sentence";
	}
}
