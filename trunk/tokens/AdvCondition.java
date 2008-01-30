package tokens;

import prover.*;

import java.lang.*;
import java.util.*;

/**
 *高级条件表达式，除了原子逻辑表达式外还包括逻辑运算符
 *AND, OR 和 NOT，如：x>5 || x&lt;2 && x>0.
 *
 *@author He Kaiduo
 */	
public class AdvCondition implements EdgeLabel,Cloneable{

    /**
    *逻辑运算符左侧的高级条件表达式
    */
    public AdvCondition c1;

    /**
    *逻辑运算符右侧的高级条件表达式
    */
    public AdvCondition c2;
    
    /**
    *逻辑运算符与
    */
    public static int Type_AND=0;

    /**
    *逻辑运算符或
    */
    public static int Type_OR=1;

    /**
    *原子逻辑式标识符
    */
    public static int Type_ATOM=2;

    /**
    *原子逻辑式
    */
    public Condition atom;

    /**
    *逻辑连接类型，AND，OR，NOT
    */
    public int jointType;

    /**
    *给出需要连接的两个表达式和连接类型，构造新的高级条件表达式
    */
    public AdvCondition(AdvCondition c1, AdvCondition c2, int type){
	this.c1=c1;
	this.c2=c2;
	this.jointType=type;
	this.atom=null;
    }

    /**
    *给出原子逻辑表达式，构造高级条件表达式
    */
    public AdvCondition(Condition c){ 
	this.atom=c;
	this.c1=null;
	this.c2=null;
	this.jointType=AdvCondition.Type_ATOM;
    }

    /**
    *深拷贝函数
    *
    *@return 拷贝后的对象
    */
    public AdvCondition clone(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return new AdvCondition(this.atom.clone());
	}
	else{
	    return new AdvCondition(c1.clone(),c2.clone(),jointType); 
	}
    }

    /**
    *将表达式中的变量用表达式作代换
    *
    *@param v 需要作代换的变量
    *@param e 作代换的表达式
    *@return 代换后的高级条件表达式
    */
    public AdvCondition substitute(Variable v, Expression e){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return new AdvCondition(this.atom.substitute(v,e));
	}
	else{
	    return new AdvCondition(c1.substitute(v,e),c2.substitute(v,e),jointType); 
	}
    }

    public String toString(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toString();
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "("+c1.toString()+"&&"+c2.toString()+")";
	}
	else{
	    return "("+c1.toString()+"||"+c2.toString()+")";
	}
    }
    
   public String toLFString(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toLFString();
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "(/\\"+c1.toLFString()+" "+c2.toLFString()+")";
	}
	else{
	    return "(\\/"+c1.toLFString()+" "+c2.toLFString()+")";
	}
    }

    public String toFociString(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toFociString();
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "& ["+c1.toFociString()+" "+c2.toFociString()+"]";
	}
	else{
	    return "| ["+c1.toFociString()+" "+c2.toFociString()+"]";
	}
    }

    public String toFociString(Map<String,String> map, Map<String,String> revertMap){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toFociString(map);
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "& ["+c1.toFociString(map)+" "+c2.toFociString(map)+"]";
	}
	else{
	    return "| ["+c1.toFociString(map)+" "+c2.toFociString(map)+"]";
	}
    }
    
    /**
    *将表达式转换为Foci能接受的形式
    *
    *@param map 从原始名到带后缀名的映射
    *@return Foci能接受的字符串
    *@see #toFociString()
    *@see #toFociString(Map, Map)
    */
    public String toFociString(Map<String,String> map){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toFociString(map);
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "& ["+c1.toFociString(map)+" "+c2.toFociString(map)+"]";
	}
	else{
	    return "| ["+c1.toFociString(map)+" "+c2.toFociString(map)+"]";
	}
    }
    
    /**
    *对自身取反
    */
    public void negateSelf(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    this.atom.negateSelf();
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    c1.negateSelf();
	    c2.negateSelf();
	    jointType=AdvCondition.Type_OR;
	}
	else{
	    c1.negateSelf();
	    c2.negateSelf();
	    jointType=AdvCondition.Type_AND;
	}
    }
    
    /**
    *求自身和赋值语句的最弱前置条件
    *
    *@param es 赋值语句
    *@return 最弱前置条件的高级条件表达式
    */
    public AdvCondition getWeakestPrecondition(EvaluationSentence es){
	return this.substitute(es.v,es.e);
    }

    /**
    *求自身和条件语句的最弱前置条件
    *
    *@param c 条件语句
    *@return 最弱前置条件的高级条件表达式
    */
    public AdvCondition getWeakestPrecondition(AdvCondition c){
	   return new AdvCondition(this.clone(),c.clone(),Type_AND); 
    }

    public AdvCondition getNegativeCopy(){
	AdvCondition c=this.clone();
	c.negateSelf();
	return c;
    }

    /**
    *求所有条件表达式的交
    *
    *@param list 条件表达式列表
    *@return 求交后的条件表达式
    */
    public static AdvCondition intersectAll(List<AdvCondition> list){
	//if(list.size()==1)return list.get(0);
	int size=list.size();
	int index=0;
	AdvCondition left=list.get(index++);
	while(index<size){
		left=new AdvCondition(left,list.get(index++),AdvCondition.Type_AND);
	}
	return left;
    }

    /**
    *求条件表达式c1和c2的交
    *
    *@param c1 表达式c1
    *@param c2 表达式c2
    *@return 求交后的条件表达式
    */
    public static AdvCondition intersect(AdvCondition c1, AdvCondition c2)
    {
    	return new AdvCondition(c1, c2, AdvCondition.Type_AND);
    }

    /**
    *判断当前表达式是否表示全空间
    *
    *@param p 定理证明器
    *@return 如果表示全空间，返回true；否则返回false
    */
    public boolean isTrue(Prover p)
    {
	//if(jointType==Type_ATOM&&atom.type==ConType.T)return true;
	AdvCondition ctrue=new AdvCondition(new Condition(true));
	return p.imply(ctrue,this);
    }

    /**
    *判断当前空间是否是bottom
    *
    *@return 如果是bottom，返回true；否则返回false
    */
    public boolean isFalse()
    {
	return this.toString().equals("(false)");
    }

    /**
    *判断当前表达式是否与表达式c相等
    *
    *@param c 需要判断的表达式
    *@param p 定理证明器
    *@return 如果相等，返回true；否则返回false
    */
    public boolean equals(AdvCondition c, Prover p)
    {
	if(p.imply(this,c)&&p.imply(c,this))return true;
	return false;
    }
}
