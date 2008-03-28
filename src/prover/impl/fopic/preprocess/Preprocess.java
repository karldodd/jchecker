package prover.impl.fopic.preprocess;

import java.util.*;
import prover.impl.fopic.*;
import tokens.*;

public class Preprocess {
	
	static public List<Formula> doPreprocess(List<AdvCondition> cList, VariableTable vTable) {
		
		List<Formula> fList = new List<Formula>();
		conversion(cList, fList); //change cList to flist
		mark(fList); //给公式打标
		theoryDevision(fList); //理论域分离
		generateVariableTable(fList, vTable); //产生全局变量表
		
		return fList;
	}
	
	/**
	 * 将cList改为fList
	 * 
	 * @param cList
	 * @param fList
	 */
	static public void conversion(List<AdvCondition> cList, List<Formula> fList) {} 
	
	/**
	 * 给传入的公式打标
	 * 
	 * @param fList 输入的公式列表
	 */
	static public void mark(List<Formula> fList) {}
	
	/**
	 * 理论域分离
	 * 
	 * @param fList
	 */
	static public void theoryDevision(List<Formula> fList) {}
	
	/**
	 * 产生全局变量表
	 * 
	 * @param fList
	 * @param vTable
	 */
	static public void generateVariableTable(List<Formula> fList, VariableTable vTable) {} 
}