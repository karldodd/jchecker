package tokens.prover.xxx.simplexcore;
import java.lang.*;
import java.util.*;
import tokens.*;

/**
 * 可传递的变量类，包含有其所属域的信息
 *
 * @author Chen Xianwen
 */
public class PassableVariable extends Variable{
    /**
     * 该表达式的所属域
     */
    public int belongFieldId;

    PassableVariable(String name) {
        super(name);
    }

    PassableVariable(String name, Object value) {
        super(name, value);
    }

}

