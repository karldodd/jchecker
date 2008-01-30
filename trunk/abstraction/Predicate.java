package abstraction;

import tokens.*;
import prover.*;

import java.lang.*;
import java.util.*;

/**
*Predicate类表示的是一个不带状态的谓词，如x&lt;1, x==2, x&lt;3/\x>1
*
*@author Li Jiang
*/
public class Predicate
{
	/**
	*c表示的是构成谓词的高级条件式
	*/
	private AdvCondition c;

	/**
	*Predicate类的构造函数
	*
	*@param c 构成谓词的高级条件式
	*/
	public Predicate(AdvCondition c)
	{
		this.c = c;
	}
	
	/**
	*Predicate类的深拷贝函数
	*
	*@return 谓词的深拷贝
	*/
	public Predicate clone()
	{
		return new Predicate(c.clone());
	}

	/**
	*获取谓词的主体
	*
	*@return 谓词主体高级条件式的拷贝
	*/
	public AdvCondition getAdvCondition()
	{
		return c.clone();
	}
	
	/**
	*将谓词转换成字符串表示形式
	*
	*@return 谓词主体的字符串表达
	*/
	public String toString()
	{
		return c.toString();
	}

	/**
	*将谓词转换为符合vampyre格式需要的左侧字符串
	*
	*@return 谓词主体的左侧字符串表达
	*/
	public String toLFString()
	{
		return c.toLFString();
	}

	/**
	*在终端显示谓词
	*/
	void display()
	{
		System.out.println("predicate is: " + c.toString());
	}
	
//	public boolean equals(Predicate p, Prover prover){
//		return this.getAdvCondition().equals(p.getAdvCondition(),prover);
//	}

	/**
	*比较两个谓词是否相等
	*
	*@param o 需要比较的第二个对象
	*@return 如果o是谓词且两个谓词相等，则返回<code>true</code>，否则返回<code>false</code>
	*@see #equals(Predicate)
	*/
	public boolean equals(Object o)
	{
		if(o instanceof Predicate)
		{
			Predicate p=(Predicate)o;
			return c.toString().equals(p.getAdvCondition().toString());
		}
		else return false;
		//return c.toString().equals(p.getAdvCondition().toString());
	}
	
	/**
	*比较两个谓词是否相等
	*
	*@param p 需要比较的第二个谓词
	*@return 如果两个谓词相等，则返回<code>true</code>，否则返回<code>false</code>
	*@see #equals(Object)
	*/
	public boolean equals(Predicate p)
	{
		return c.toString().equals(p.getAdvCondition().toString());
	}

	/**
	*获取谓词的Hash码
	*
	*@return 谓词的Hash码
	*/
	public int hashCode()
	{
		return this.toString().hashCode();
	}
}
