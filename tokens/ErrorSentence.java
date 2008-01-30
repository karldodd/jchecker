package tokens;
import java.lang.*;
import java.util.*;

/**
*错误语句类
*
*@author He Kaiduo
*/
public class ErrorSentence extends Sentence{

	/**
	*表示错误的字符串
	*/
	String errorString;

	/**
	*空构造函数
	*/
	public ErrorSentence(){}

	public String toString(){return "ERROR;";}
}
