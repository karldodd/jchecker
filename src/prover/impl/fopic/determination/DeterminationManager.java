package prover.impl.fopic.determination;

import java.util.*;
import tokens.*;
import prover.impl.fopic.*;

public class DeterminationManager {
	
	/**
	 * 判断子式列表的交是否可满足
	 * 
	 * @param fList 子式列表
	 * @return 可满足性
	 */
	public boolean isSatisfiable(List<Formula> fList) {}
	
	/**
	 * 判断子式列表的交是否可满足，并产生等式来源表
	 * 
	 * @param fList 子式列表
	 * @param est 等式来源表
	 * @return 可满足性
	 */
	public boolean isSatisfiable(List<Formula> fList, EquationSourceTable est) {}
		
}