package tokens;
import java.lang.*;
import java.util.*;

/**
*运算表达式的类型
*
*@author He Kaiduo
*/
public enum ExpType {

	/**
	*数字
	*/
	num,
	
	/**
	*变量
	*/
	var,
	
	/**
	*加
	*/
	plus,
	
	/**
	*减
	*/
	minus,
	
	/**
	*乘
	*/
	multiply,
	
	/**
	*非
	*/
	negative
}
