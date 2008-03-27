package prover.impl.xxx;

import java.util.List;

import abstraction.Predicate;
import prover.*;
import tokens.AdvCondition;
import tokens.EdgeLabel;

public class xxxProver implements Prover {
	
	/**
	*获取插值
	*
	*@param edges 计算插值的所有边
	*@exception Exception 如果所有边的交可满足，抛出异常
	*/
	//if the edges are satisfiable, throw Exception
	public List<Predicate> getInterpolation(List<EdgeLabel> edges) throws Exception {
		
		preprocess();
		findincache();
		List<Predicate> l = interpolation();
		return l;
	}

	/**
	*判断两个高级条件式是否蕴含，即c1是否蕴含c2
	*
	*@param c1 蕴含符号左侧高级条件式
	*@param c2 蕴含符号右侧高级条件式
	*/
	public boolean imply(AdvCondition c1, AdvCondition c2) {
		
		preprocess();
		findincache();
		boolean result = determination();
		return result;
	}

	/**
	*判断高级条件式的交是否可满足
	*
	*@param clist 高级条件式的交
	*/
	public boolean isSatisfiable(List<AdvCondition> clist) {
		
		preprocess();
		findincache();
		boolean result = determination();
		return result;
	}
	
} 