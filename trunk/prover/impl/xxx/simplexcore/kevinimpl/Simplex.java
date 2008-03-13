package prover.impl.kevinimpl;

import tokens.*;
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
     * @param simplexStruct 单纯形结构体
     * @return 是否可以进行下一次的单纯形运算
     */
    public boolean StepSimplex(SimplexStruct simplexStruct) {
        return false;
    }
}
