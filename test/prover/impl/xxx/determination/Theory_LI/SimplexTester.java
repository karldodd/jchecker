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

public class SimplexTester {

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
     * Construct a simplex using the following statements:
     *  a >= 5
     *  a <= 4
     *
     * They are not satisfiable.
     */
	@Test
	public void testSimplex1() throws Exception{
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
        assertTrue(!Simplex.Satisfiable(simplexStruct));
	}

    /*
     * Construct a simplex using the following statements:
     *  a >= 3
     *  a <= 4
     *
     * They are not satisfiable.
     */
	@Test
	public void testSimplex2() throws Exception{
        // a >= 3
        Expression e1 = new Expression(new Variable("a"));
        Expression e2 = new Expression(3);
        Condition condition1 = new Condition(e1, e2, ConType.equallarger);

        // a <= 4
        Expression e3 = new Expression(new Variable("a"));
        Expression e4 = new Expression(4);
        Condition condition2 = new Condition(e3, e4, ConType.equalsmaller);

        conditions = new Vector<Condition>();
        conditions.add(condition1);
        conditions.add(condition2);

        simplexStruct = new SimplexStruct(this.conditions);

        // The table:
        //   1  -1      0       1       0   3
        //   1  0       1      0       1   4
        //   2  -1       1      0       0   9
        assertTrue(Simplex.Satisfiable(simplexStruct));
	}

    /*
     * Construct a simplex using the following statements:
     *  a - 2b >= 3
     *  a + b <= 2
     *
     * They are not satisfiable.
     */
	@Test
	public void testSimplex3() throws Exception{
        // a - 2b >= 3
        Expression e1 = new Expression(new Variable("a"));

        Expression e21 = new Expression(2);
        Expression e22 = new Expression(new Variable("b"));
        Expression e2 = new Expression(e21, e22, ExpType.multiply);

        Expression e_left_1 = new Expression(e1, e2, ExpType.minus);
        Expression e_right_1 = new Expression(3);
        Condition condition1 = new Condition(e_left_1, e_right_1, ConType.equallarger);

        // a + b <= 2
        Expression e3 = new Expression(new Variable("a"));
        Expression e4 = new Expression(new Variable("b"));

        Expression e_left_2 = new Expression(e3, e4, ExpType.plus);
        Expression e_right_2 = new Expression(2);
        Condition condition2 = new Condition(e_left_2, e_right_2, ConType.equalsmaller);

        conditions = new Vector<Condition>();
        conditions.add(condition1);
        conditions.add(condition2);

        simplexStruct = new SimplexStruct(this.conditions);

        assertTrue(!Simplex.Satisfiable(simplexStruct));
	}

    /*
     * Construct a simplex using the following statements:
     *  a - 2b >= 3
     *  a + b <= 3
     *
     * They are satisfiable.
     */
	@Test
	public void testSimplex4() throws Exception{
        // a - 2b >= 3
        Expression e1 = new Expression(new Variable("a"));

        Expression e21 = new Expression(2);
        Expression e22 = new Expression(new Variable("b"));
        Expression e2 = new Expression(e21, e22, ExpType.multiply);

        Expression e_left_1 = new Expression(e1, e2, ExpType.minus);
        Expression e_right_1 = new Expression(3);
        Condition condition1 = new Condition(e_left_1, e_right_1, ConType.equallarger);

        // a + b <= 3
        Expression e3 = new Expression(new Variable("a"));
        Expression e4 = new Expression(new Variable("b"));

        Expression e_left_2 = new Expression(e3, e4, ExpType.plus);
        Expression e_right_2 = new Expression(3);
        Condition condition2 = new Condition(e_left_2, e_right_2, ConType.equalsmaller);

        conditions = new Vector<Condition>();
        conditions.add(condition1);
        conditions.add(condition2);

        simplexStruct = new SimplexStruct(this.conditions);

        assertTrue(Simplex.Satisfiable(simplexStruct));
	}
}
