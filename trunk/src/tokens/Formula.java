package tokens;

public class Formula {
	
	/**
	 * 公式原形
	 */
	AdvCondition body;
	
	/**
	 * 公式序号
	 */
	int formulaIndex;
	
	/**
	 * 属于公式中的子序号
	 */
	int subIndex;
	
	/**
	 * 所属理论域
	 */
	TheoryType tType;
}