package prover.impl.fopic.determination.theory.li.simplexcore;

import tokens.*;
import tokens.prover.fopic.simplexcore.*;
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

    public static boolean Satisfiable(SimplexStruct simplexStruct) {
        while(StepSimplex(simplexStruct))
            ;
        return simplexStruct.getFuncValue() == 0;
    }

    /**
     * 对当前的单纯形结构体进行一次出基入基的单纯形表运算
     *
     * @param simplexStruct 单纯形结构体
     * @return 是否可以继续进行单纯形运算
     */
    public static boolean StepSimplex(SimplexStruct simplexStruct) {
        int inBasis;
        int lastLine = simplexStruct.height - 1;
        int basisColumn = simplexStruct.width - 1;

        // 进基符号取最大正数
        inBasis = max(simplexStruct.tokenValues[lastLine]);
        if(simplexStruct.tokenValues[lastLine][inBasis] <= 0) {
            // 单纯形表检验数不存在正数
            return false;
        }

        // 出基符号取进基变量所属列最小的正数
        int outBasis = -1;
        for(int i = 0; i < simplexStruct.tokenValues.length - 1; ++i) {
            double value = simplexStruct.tokenValues[i][basisColumn] / simplexStruct.tokenValues[i][inBasis];
            if (value > 0 && (outBasis == -1 
                        || value < simplexStruct.tokenValues[outBasis][basisColumn] / simplexStruct.tokenValues[outBasis][inBasis]))
                outBasis = i;
        }

        // 若进基变量所属列不含正数, 说明该式不可满足.
        if(outBasis < 0)
            return false;
        
        // 进行出基入基运算
        // 本行归一化
        double rate = 1.0 / simplexStruct.tokenValues[outBasis][inBasis];
        for(int i = 0; i < simplexStruct.tokenValues[outBasis].length; ++i)
            simplexStruct.tokenValues[outBasis][i] *= rate;

        // 行变换使其他行的相应列消为0
        for(int i = 0; i < simplexStruct.tokenValues.length; ++i) {
            if(i == outBasis || simplexStruct.tokenValues[i][inBasis] == 0)
                continue;

            rate = simplexStruct.tokenValues[i][inBasis];
            for(int j = 0; j < simplexStruct.tokenValues[i].length; ++j) {
                simplexStruct.tokenValues[i][j] -= simplexStruct.tokenValues[outBasis][j] * rate;
            }
        }

        // 执行出基入基操作
        //simplexStruct.basis.get(outBasis) = simplexStruct.tokens.get(inBasis);

        return true;
    }

    /**
     * 求数组中的最大元素
     *
     * @param theArray 输入的数组
     * @return 除最后一个元素以外，最大元素的序号
     */
    private static int max(double[] theArray) {
        int result = 0;
        for(int i = 1; i < theArray.length-1; ++i) {
            if (theArray[i] > theArray[result]) {
                result = i;
            }
        }
        return result;
    }

}
