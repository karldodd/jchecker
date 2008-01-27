package abstraction;

/**
*枚举变量State的四个值分别对应谓词的四个状态：
*<ul>
*<li><code>STATE_FALSE</code> 表示谓词的bottom状态</li>
*<li><code>STATE_POS</code> 表示谓词的true状态</li>
*<li><code>STATE_NEG</code> 表示谓词的false状态</li>
*<li><code>STATE_TRUE</code> 表示谓词的top状态</li>
*</ul>
*
*@author Li Jiang
*/

public enum State {
	STATE_FALSE, 
	STATE_POS, 
	STATE_NEG, 
	STATE_TRUE
}

