package tokens;

import java.util.*;

/**
*边上的标记
*
*@author He Kaiduo
*/
public interface EdgeLabel{

	/**
    	*将表达式转换为字符串形式
    	*
	*@return 转换后的字符串
	*/
	String toString();
	
	 /**
	 *将表达式转换为定理证明器接受的左侧字符串形式
	 *
	 *@return 左侧字符串
	 */
	 String toLFString();

	 /**
	 *将表达式转换为Foci能接受的字符串形式
	 *
	 *@return Foci能接受的字符串
	 *@see #toFociString(Map, Map)
	 */
	 String toFociString();
	 
	 //map is to save info: primitive var => var with suffix
	 //revertMap is to save info: var with suffix => primitive var
	 /**
	 *若字符串中有同名变量，将同名变量添加后缀
	 *
	 *@param map 从原始名到带后缀名的映射
	 *@param revertMap 从带后缀名到原始名的映射
	 *@return Foci能接受的字符串
	 *@see #toFociString()
	 */
	 String toFociString(Map<String,String> map, Map<String,String> revertMap);
	 
	 /**
	 *获取边上表达式的非的拷贝
	 *
	 *@return 表达式的非的拷贝
	 */
	 AdvCondition getNegativeCopy();
}
