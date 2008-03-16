package prover.impl.xxx.simplexcore;

import tokens.*;
import tokens.prover.xxx.*;
import abstraction.*;

import java.lang.*;
import java.util.*;

/**
* 单纯形表的保存结构
*
* @author Chen Xianwen
*/
public class SimplexStruct{
    /**
     * 目标函数
     */
    private Expression goalFunc;

    /**
     * 等式组
     */
    private List<Equation> conditionList;

    /**
     * 目标函数值
     */
    double funcValue;

    /**
     * 单纯形表的主要部分，请参见说明文件
     */
    double[][] tokenValues;

    /**
     * 单纯形表中变量及其相应的值，请参见说明文件
     */
    ValueVariable[] tokens ;

    /**
     * 单纯形表中的基及其相应的值，请参见说明文件
     */
    ValueVariable[] basis ;

    /**
     * 取得目标函数
     * @return 目标函数
     */
    public Expression GetGoalFunc() {
        return goalFunc;
    }

    /**
     * 取得当前的等式组
     * @return 当前的等式组
     */
    public List<Equation> GetConditionList() {
        return conditionList;
    }

    /**
     * 取得当前的目标函数值
     * @return 当前的目标函数值
     */
    public double GetFuncValue() {
        return funcValue;
    }
}

