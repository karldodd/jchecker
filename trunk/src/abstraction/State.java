package abstraction;

/**
*枚举变量State的四个值分别对应谓词的四个状态
*
*@author Li Jiang
*/

public enum State {	
	
	/**
	*表示谓词的bottom状态
	*/
	STATE_FALSE, 
	
	/**
	*表示谓词的true状态
	*/
	STATE_POS, 
	
	/**
	*表示谓词的false状态
	*/
	STATE_NEG,
	
	/**
	*表示谓词的top状态
	*/ 
	STATE_TRUE
}

