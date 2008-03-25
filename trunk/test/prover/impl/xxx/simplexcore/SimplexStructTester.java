package prover.impl.xxx.simplexcore;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tokens.*;
import java.lang.*;
import java.util.*;

/*
 * The test for SimplexStruct
 * Author: Chen Xianwen
 */

public class SimplexStructTester {

    private SimplexStruct simplexStruct = null;
    private List<Condition> conditions = null;

	@Before
	public void setUp() {
        // a >= 5
        Expression e1 = new Expression(new Variable("a"));
        Expression e2 = new Expression(5);
        Condition condition1 = new Condition(e1, e2, ConType.equallarger);

        // a <= 4
        Expression e3 = new Expression(new Variable("a"));
        Expression e4 = new Expression(4);
        Condition condition2 = new Condition(e3, e4, ConType.equalsmaller);

        conditions = new Vector<Condition>();
        conditions.add(condition1);
        conditions.add(condition2);
	}

	@After
	public void tearDown() {
        simplexStruct = null;
        conditions = null;
	}

    /*
     * After normalize, the statements should be:
     *   a - __s1 + __w1 = 5
     *   a + __s2 + __w2 = 4
     *
     * And the simplexStruct should be:
     *   
     *       a  __s1    __s2    __w1  __w2
     *   w1  1  -1      0       1       0   5
     *   w2  1  0       1      0       1   4
     *       2  1       1      1       1   9
     *
     * Thus, after the constructor,
     * the tokens should be: a  __s1    __s2    __w1  __w2
     *    the 
     * and the basis should be: w1 w2.
     * and the rest of the table should be stored in tokenValues.
     */
	@Test
	public void testConstructor() throws Exception{
        simplexStruct = new SimplexStruct(this.conditions);
		assertNotNull(simplexStruct);

        // Assure the variables are constructed correctly.
		assertNotNull(simplexStruct.tokens);
        assertEquals(simplexStruct.tokens.size(), 5);
        assertEquals(simplexStruct.tokens.get(0), "a");
        assertEquals(simplexStruct.tokens.get(1), "__s1");
        assertEquals(simplexStruct.tokens.get(2), "__s2");
        assertEquals(simplexStruct.tokens.get(3), "__w1");
        assertEquals(simplexStruct.tokens.get(4), "__w2");

        // Assure the basises are constructed correctly.
		assertNotNull(simplexStruct.basis);
        assertEquals(simplexStruct.basis.size(), 2);
        assertEquals(simplexStruct.basis.get(0), "__w1");
        assertEquals(simplexStruct.basis.get(1), "__w2");

        // Assure the values within the table are constructed correctly.
		assertNotNull(simplexStruct.tokenValues);
        assertEquals(simplexStruct.tokenValues.length, 3);
		assertNotNull(simplexStruct.tokenValues[0]);
        assertEquals(simplexStruct.tokenValues[0].length, 6);
		assertNotNull(simplexStruct.tokenValues[1]);
        assertEquals(simplexStruct.tokenValues[1].length, 6);

        // The table:
        //   1  -1      0       1       0   5
        //   1  0       1      0       1   4
        //   2  -1       1      1       1   9
        assertEquals(simplexStruct.tokenValues[0][0], 1);
        assertEquals(simplexStruct.tokenValues[0][1], -1);
        assertEquals(simplexStruct.tokenValues[0][2], 0);
        assertEquals(simplexStruct.tokenValues[0][3], 1);
        assertEquals(simplexStruct.tokenValues[0][4], 0);
        assertEquals(simplexStruct.tokenValues[0][5], 5);

        assertEquals(simplexStruct.tokenValues[1][0], 1);
        assertEquals(simplexStruct.tokenValues[1][1], 0);
        assertEquals(simplexStruct.tokenValues[1][2], 1);
        assertEquals(simplexStruct.tokenValues[1][3], 0);
        assertEquals(simplexStruct.tokenValues[1][4], 1);
        assertEquals(simplexStruct.tokenValues[1][5], 4);

        assertEquals(simplexStruct.tokenValues[2][0], 2);
        assertEquals(simplexStruct.tokenValues[2][1], -1);
        assertEquals(simplexStruct.tokenValues[2][2], 1);
        assertEquals(simplexStruct.tokenValues[2][3], 1);
        assertEquals(simplexStruct.tokenValues[2][4], 1);
        assertEquals(simplexStruct.tokenValues[2][5], 9);
	}

}
