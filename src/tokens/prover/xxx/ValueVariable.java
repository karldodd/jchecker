package tokens.prover.xxx;
import java.lang.*;
import java.util.*;
import tokens.*;

/**
*值变量类, 其值为double类型
*
*@author Chen Xianwen
*/
public class ValueVariable implements Cloneable{

	/**
	*变量名
	*/
	private String name;

	/**
	*变量值
	*/
	public double Value;

	/**
	*根据名字构造值为空的变量
	*
	*@param name 变量名字
	*/
	public ValueVariable(String name){
		this.name=name;
		Value=0;
	}

	/**
	*根据变量名和值构造变量
	*
	*@param name 变量名
	*@param value 变量值
	*/
	public ValueVariable(String name, double value){
		this.name=name;
		this.Value=value;
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
	public void setValue(double value){
		this.Value=value;
	}	

	/**
	*获取变量的值
	*
	*@return 变量的值
	*/
	public double getValue(){
		return this.Value;
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
	public ValueVariable clone(){
		return new ValueVariable(this.name,this.Value);
	}
	
}

