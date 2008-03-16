package tokens.prover.xxx;

import java.lang.*;
import java.util.*;
import tokens.*;

/**
 * 可传递的表达式，包含有其所属域的信息
 *
 * @author Chen Xianwen
 */
public class Equation{
    /**
     * 等式左端的表达式
     */
    public PassableExpression leftExpression;

    /**
     * 等式右端的常数
     */
    public double rightValue;

    /**
     * 该表达式的所属域
     */
    public int belongFieldId;
}

