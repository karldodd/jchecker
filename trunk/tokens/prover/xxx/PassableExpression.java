package tokens;
import java.lang.*;
import java.util.*;

/**
 * 可传递的表达式，包含有其所属域的信息
 *
 * @author Chen Xianwen
 */
public class PassableExpression extends Expression{
    /**
     * 变量
     */
    public PassableVariable v;

    /**
     * 运算表达式的左侧表达式
     */
    public PassableExpression l;

    /**
     * 运算表达式的右侧表达式
     */
    public PassableExpression r;

    /**
     * 该表达式的所属域
     */
    public int belongFieldId;

	/**
	*数字类型的构造函数
	*
	*@param num 数字
	*/
	public PassableExpression(int num){
        super(num);
	}

	/**
	*变量类型的构造函数
	*
	*@param v 变量
	*/
	public PassableExpression(Variable v){
        super(v);
	}

	/**
	*运算类型的构造函数
	*
	*@param l 运算的左侧表达式
	*@param r 运算的右侧表达式
	*@param type 运算类型
	*/
	public PassableExpression(Expression l,Expression r, ExpType type)
	{
        super(l, r, type);
	}
}
