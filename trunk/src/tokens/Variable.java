package tokens;
import java.lang.*;
import java.util.*;

import prover.impl.fopic.determination.theory.uif.egraph.core.AtomicElement;

/**
*变量类
*
*@author He Kaiduo
*/
public class Variable extends Expression implements AtomicElement{

	/**
	*变量名
	*/
	private String name;

	/**
	*根据名字构造值为空的变量
	*
	*@param name 变量名字
	*/
	public Variable(String name){
		this.name=name;
	}

	/**
	*获取变量名
	*
	*@return 变量名
	*/
	public String getName(){return name;}
	
	/**
	*获取随机的变量后缀
	*
	*@return 变量的随机后缀
	*/
	public static int getRandomIntSuffix(){
		return (int)(100000.00*Math.random());
	}

	/**
	*深拷贝函数
	*
	*@return 变量的拷贝
	*/
	@Override
	public Variable clone(){
		return new Variable(this.name);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Variable){
			return this.getName().equals(((Variable)obj).getName());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.getName().hashCode();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}
	
	@Override
	public Expression substitute(Variable v, Expression e) {
		// TODO Auto-generated method stub
		if(this.getName().equals(v.getName())){
			return e.clone();
		}
		else
			return this.clone();
	}
}
