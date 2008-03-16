package tokens;
import java.lang.*;
import java.util.*;

/**
*返回语句类
*
*@author He Kaiduo
*/
public class ReturnSentence extends Sentence{
	
	/**
	*返回语句的表达式
	*/
	Expression e;

	/**
	*根据主体构造返回语句
	*/
	public ReturnSentence(Expression e){
		this.e=e;
	}

	public String toString(){
		return "return "+e.toString()+";";
	}
}
