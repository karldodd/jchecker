package tokens;
import java.lang.*;
import java.util.*;

/**
*变量类
*
*@author He Kaiduo
*/
public class Variable implements Cloneable{

	/**
	*变量名
	*/
	private String name;

	/**
	*变量值
	*/
	private Object value;

	/**
	*根据名字构造值为空的变量
	*
	*@param name 变量名字
	*/
	public Variable(String name){
		this.name=name;
		value=null;
	}

	/**
	*根据变量名和值构造变量
	*
	*@param name 变量名
	*@param value 变量值
	*/
	public Variable(String name, Object value){
		this.name=name;
		this.value=value;
	}

	/**
	*获取变量名
	*
	*@return 变量名
	*/
	public String getName(){return name;}
	
	/**
	*设置变量的值
	*
	*@param value 需要设置的值
	*/
	public void setValue(Object value){
		this.value=value;
	}	

	/**
	*获取变量的值
	*
	*@return 变量的值
	*/
	public Object getValue(){
		return this.value;
	}

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
	public Variable clone(){
		return new Variable(this.name,this.value);
	}
	
	/*
	//'a' to 'a_suf'
	public void addSuffix(String suf){
		
	}*/
	
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
}
