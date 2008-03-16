package tokens;
import java.lang.*;
import java.util.*;

/**
*原子条件式的类型
*
*@author He Kaiduo
*/
public enum ConType {
	
	/**
	*永真式
	*/
	T,
	
	/**
	*永假式
	*/
	F,
	
	/**
	*等于
	*/
	equal,
	
	/**
	*不等于
	*/
	notequal,
	
	/**
	*大于
	*/
	larger,
	
	/**
	*小于
	*/
	smaller,
	
	/**
	*大于等于
	*/
	equallarger,
	
	/**
	*小于等于
	*/
	equalsmaller
}
