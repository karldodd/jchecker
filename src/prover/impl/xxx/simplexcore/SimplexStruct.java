package prover.impl.xxx.simplexcore;

import tokens.*;
import tokens.prover.xxx.simplexcore.*;
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
    public double getFuncValue() {
        return tokenValues[height-1][width-1];
    }

    /**
     * 单纯形表中的所有变量
     * 保存顺序为：原始变量， 冗余变量, 人工变量
     */
    List<String> tokens;

    /**
     * 原始变量的数量
     */
    int originalVarCount = 0;

    /**
     * 人工变量在tokens中的起始位置
     */
    int manualVarStartPoint;

    /**
     * 单纯形表中的基
     */
    List<String> basis;

    /**
     * 单纯形表的主要部分，请参见说明文件
     */
    double[][] tokenValues;

    /**
     * 单纯形表的宽度
     */
    int width = 0;

    /**
     * 单纯形表的高度
     */
    int height = 0;

    /**
     * 从现有的conditionList构造出单纯形结构
     *
     * @return 目标函数
     */
    public SimplexStruct(List<Condition> conditionList) throws Exception{
        this.tokens = new Vector<String>();
        this.basis = new Vector<String>();

        // 单纯形表的高度比等式数多一
        // 多的一行用于存储单纯形表底部的检验数及目标函数值
        this.height = conditionList.size() + 1;
        this.tokenValues = new double[height][];

        // 冗余变量数
        int spaceVarCount = 0;

        // 包含表达式中出现过的变量名字, 用于保证tokens中变量名字的唯一性
        Set<String> varNameSet = new HashSet<String>();

        // 添加表达式中所含变量及冗余变量
        for(Condition condition : conditionList) {
            if (condition.type == ConType.equal) {
                if(!varNameSet.contains(condition.l.v.getName())) {
                    varNameSet.add(condition.l.v.getName());
                    tokens.add(originalVarCount++, condition.l.v.getName());
                }
            } else if (condition.type == ConType.equalsmaller) {
                if(!varNameSet.contains(condition.l.v.getName())) {
                    varNameSet.add(condition.l.v.getName());
                    tokens.add(originalVarCount++, condition.l.v.getName());
                }
                tokens.add("__s" + (++spaceVarCount));
            } else if (condition.type == ConType.equallarger) {
                if(!varNameSet.contains(condition.l.v.getName())) {
                    varNameSet.add(condition.l.v.getName());
                    tokens.add(originalVarCount++, condition.l.v.getName());
                }
                tokens.add("__s" + (++spaceVarCount));
            } else {
                throw new Exception("The argument is illegal");
            }
        }

        manualVarStartPoint = tokens.size();

        // 添加人工变量
        for(int i = 1; i <= conditionList.size(); ++i) {
            String manualVar = "__w" + i;
            tokens.add(manualVar);
            basis.add(manualVar);
        }

        // 单纯形表的宽度为变量数加1
        // 多出的一列用于放置基的值
        this.width = tokens.size() + 1;

        // 填写单纯形表格
        for(int i = 0; i < conditionList.size(); ++i) {
            Condition condition = conditionList.get(i);
            tokenValues[i] = new double[width];
            int index = tokens.indexOf(condition.l.v.getName());

            // fake
            // 设置相应位置的变量值
            tokenValues[i][index] = 1;

            // 设置冗余变量的值
            // 若是大于等于号，则为-1
            // 若是小于等于号，则为+1
            // 等号则无冗余变量
            if(condition.type == ConType.equallarger) {
                tokenValues[i][originalVarCount + i] = -1;
            } else if (condition.type == ConType.equalsmaller) {
                tokenValues[i][originalVarCount + i] = 1;
            }

            // 设置人工变量 (__w1, __w2等)
            // 由于人式变量为初始时的基，因而设为1即可
            tokenValues[i][manualVarStartPoint + i] = 1;

            // 设置相应行基的值, 此时应为等式右端的值
            tokenValues[i][width - 1] = condition.r.num;
        }

        // 填写单纯形表格检验数与目标函数值
        tokenValues[height - 1] = new double[tokens.size() + 1];

        // 每一个非基变量的检验数为相应列所有数的和
        for(int i = 0; i < manualVarStartPoint; ++i) {
            tokenValues[height - 1][i] = 0;
            for(int j = 0; j < height - 1; ++ j) {
                tokenValues[height - 1][i] += tokenValues[j][i];
            }
        }
        // 目标函数为当前所有基的和
        tokenValues[height - 1][width - 1] = 0;
        for(int j = 0; j < height - 1; ++ j) {
            tokenValues[height - 1][width - 1] += tokenValues[j][width - 1];
        }
    }

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
        //return funcValue;
        return 1;
    }
}

