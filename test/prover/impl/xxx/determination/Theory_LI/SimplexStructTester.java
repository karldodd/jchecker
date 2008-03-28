package prover.impl.xxx.determination.Theory_LI;

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
	}

	@After
	public void tearDown() {
        simplexStruct = null;
        conditions = null;
	}

    /*
     * The original statements are:
     *  a >= 5
     *  a <= 4
     *
     * After normalize, the statements should be:
     *   a - __s1 + __w1 = 5
     *   a + __s2 + __w2 = 4
     *
     * And the simplexStruct should be:
     *   
     *       a  __s1    __s2    __w1  __w2
     *   w1  1  -1      0      1       0   5
     *   w2  1  0       1      0       1   4
     *       2  -1      1      0       0   9
     *
     * Thus, after the constructor,
     * the tokens should be: a  __s1    __s2    __w1  __w2
     * and the basis should be: __w1  __w2
     * and the rest of the table should be stored in tokenValues.
     */
	@Test
	public void testConstructor1() throws Exception{
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

        simplexStruct = new SimplexStruct(this.conditions);
		assertNotNull(simplexStruct);

        // Assure the tokens are constructed correctly.
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
        //   2  -1       1      0       0   9
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
        assertEquals(simplexStruct.tokenValues[2][3], 0);
        assertEquals(simplexStruct.tokenValues[2][4], 0);
        assertEquals(simplexStruct.tokenValues[2][5], 9);
	}

    /*
     * The original statements are:
     *  2a + b >= 3
     *  a - 3c <= 2
     *  b + c = 6
     *
     * After normalize, the statements should be:
     *   2a + b - __s1 + __w1 = 3
     *   a - 3c + __s2 + __w2 = 2
     *   b + c + __w3 = 6
     *
     * And the simplexStruct should be:
     *   
     *       a  b   c   __s1    __s2    __w1  __w2  __w3
     *   w1  2  1   0   -1      0       1       0     0      3
     *   w2  1  0  -3   0       1       0       1     0      2
     *   w3  0  1   1   0       0       0       0     1      6
     *       3  2  -2   -1      1       1       1     1      11
     *
     * Thus, after the constructor,
     * the tokens should be: a  b   c   __s1    __s2    __w1  __w2  __w3
     * and the basis should be: __w1  __w2  __w3
     * and the rest of the table should be stored in tokenValues.
     */
    @Ignore
	@Test
	public void testConstructor2() throws Exception{
        // 2a + b >= 3
        Expression e11 = new Expression(2);
        Expression e12 = new Expression(new Variable("a"));
        Expression e1 = new Expression(e11, e12, ExpType.multiply);

        Expression e2 = new Expression(new Variable("b"));
        Expression e_left_1 = new Expression(e1, e2, ExpType.plus);
        Expression e_right_1 = new Expression(3);
        Condition condition1 = new Condition(e_left_1, e_right_1, ConType.equallarger);

        // a - 3c <= 2
        Expression e3 = new Expression(new Variable("a"));

        Expression e41 = new Expression(3);
        Expression e42 = new Expression(new Variable("c"));
        Expression e4 = new Expression(e41, e42, ExpType.multiply);

        Expression e_left_2 = new Expression(e3, e4, ExpType.minus);
        Expression e_right_2 = new Expression(2);
        Condition condition2 = new Condition(e_left_2, e_right_2, ConType.equalsmaller);

        // b + c = 6
        Expression e5 = new Expression(new Variable("b"));
        Expression e6 = new Expression(new Variable("c"));

        Expression e_left_3 = new Expression(e3, e4, ExpType.plus);
        Expression e_right_3 = new Expression(6);
        Condition condition3 = new Condition(e_left_3, e_right_3, ConType.equal);

        conditions = new Vector<Condition>();
        conditions.add(condition1);
        conditions.add(condition2);
        conditions.add(condition3);

        simplexStruct = new SimplexStruct(this.conditions);
		assertNotNull(simplexStruct);

        // The tokens should be: a  b   c   __s1    __s2    __w1  __w2  __w3
        // Assure the tokens are constructed correctly.
		assertNotNull(simplexStruct.tokens);
        assertEquals(simplexStruct.tokens.size(), 8);
        assertEquals(simplexStruct.tokens.get(0), "a");
        assertEquals(simplexStruct.tokens.get(1), "b");
        assertEquals(simplexStruct.tokens.get(2), "c");
        assertEquals(simplexStruct.tokens.get(3), "__s1");
        assertEquals(simplexStruct.tokens.get(4), "__s2");
        assertEquals(simplexStruct.tokens.get(5), "__w1");
        assertEquals(simplexStruct.tokens.get(6), "__w2");
        assertEquals(simplexStruct.tokens.get(7), "__w3");

        /*
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
        */
	}
}
