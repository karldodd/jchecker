package prover.impl.xxx.simplexcore;

import tokens.*;
import tokens.prover.xxx.*;
import abstraction.*;

import java.lang.*;
import java.util.*;

/**
* 实现单纯形算法的主要函数
*
* @author Chen Xianwen
*/
public class Simplex{
	
    /**
     * 构建单纯形运算结构
     * @param equationList 一组经过标准化后的等式
     * @return 构建出来的单纯形结构体
     */
    public SimplexStruct MakeSimplexStruct(List<Equation> equationList) {
        return null;
    }

    /**
     * 对当前的单纯形结构体进行一次出基入基的单纯形表运算
     *
     * @param simplexStruct 单纯形结构体
     * @param wantMin 是否要求最小值
     * @return 是否已终止
     */
    public boolean StepSimplex(SimplexStruct simplexStruct, boolean wantMin) {
        int inBasis;

        if(wantMin) {
            // 进基符号取最大正数
            inBasis = max(simplexStruct.tokens);
            if(simplexStruct.tokens[inBasis].Value <= 0) {
                // 单纯形表检验数不存在正数
                return true;
            }
        } else {
            // 进基符号取最小负数
            inBasis = min(simplexStruct.tokens);
            if(simplexStruct.tokens[inBasis].Value >= 0) {
                // 单纯形表检验数不存在负数
                return true;
            }
        }

        // 出基符号取进基变量所属列最小的正数
        int outBasis = -1;
        for(int i = 0; i < simplexStruct.tokenValues.length; ++i) {
            double value = simplexStruct.tokenValues[i][inBasis];
            if (value > 0 && (outBasis == -1 || value < simplexStruct.tokenValues[outBasis][inBasis]))
                outBasis = i;
        }

        // 若进基变量所属列不含正数, 说明该式不可满足.
        if(outBasis < 0)
            return true;
        
        // 进行出基入基运算
        // 本行归一化
        double rate = 1.0 / simplexStruct.tokenValues[outBasis][inBasis];
        for(int i = 0; i < simplexStruct.tokenValues[outBasis].length; ++i)
            simplexStruct.tokenValues[outBasis][i] *= rate;
        simplexStruct.basis[outBasis].Value *= rate;

        // 行变换使其他行的相应列消为0
        for(int i = 0; i < simplexStruct.tokenValues.length; ++i) {
            if(i == outBasis || simplexStruct.tokenValues[i][inBasis] == 0)
                continue;

            rate = simplexStruct.tokenValues[i][inBasis];
            for(int j = 0; j < simplexStruct.tokenValues[i].length; ++j) {
                simplexStruct.tokenValues[i][j] -= simplexStruct.tokenValues[outBasis][j] * rate;
            }
            simplexStruct.basis[i].Value -= simplexStruct.basis[outBasis].Value * rate;
        }

        // 检验数的相应列也消为0
        rate = simplexStruct.tokens[inBasis].Value;
        if(rate != 0) {
            for(int i = 0; i < simplexStruct.tokens.length; ++i) {
                simplexStruct.tokens[i].Value -= simplexStruct.tokenValues[outBasis][i] * rate;
            }
            simplexStruct.funcValue -= simplexStruct.basis[outBasis].Value * rate;
        }

        // 执行出基入基操作
        simplexStruct.basis[outBasis].name = simplexStruct.tokens[inBasis].name;

        return false;
    }

    /**
     * 求数组中的最小元素
     *
     * @param theArray 输入的数组
     * @return 最小元素的序号
     */
    private int min(ValueVariable[] theArray) {
        int result = 0;
        for(int i = 1; i < theArray.length; ++i) {
            if (theArray[i].Value < theArray[result].Value) {
                result = i;
            }
        }
        return result;
    }

    /**
     * 求数组中的最大元素
     *
     * @param theArray 输入的数组
     * @return 最大元素的序号
     */
    private int max(ValueVariable[] theArray) {
        int result = 0;
        for(int i = 1; i < theArray.length; ++i) {
            if (theArray[i].Value > theArray[result].Value) {
                result = i;
            }
        }
        return result;
    }

}
