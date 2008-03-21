package prover;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tokens.*;

import java.util.*;

public class ProverImplFociVampyreTester {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		assertNotNull(prover.ProverImplFociVampyre.getInstance());
		assertNotNull(prover.ProverImplFociVampyre.getInstance());
		// fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetInterpolation() {

		// fail("Not yet implemented"); // TODO
	}

	@Test
	public void testImply() {
		// fail("Not yet implemented"); // TODO
	}
	
	@Ignore ("not implemented.")
	@Test
	public void testIsSatisfiable() {

		Variable a = new Variable("a");
		Variable b = new Variable("b");

		Condition cc1 = new Condition(new Expression(a), new Expression(b),
				ConType.larger);

		Condition cc2 = new Condition(new Expression(a), new Expression(
				new Expression(b), new Expression(1), ExpType.minus),
				ConType.equalsmaller);

		AdvCondition c1 = new AdvCondition(cc1);
		AdvCondition c2 = new AdvCondition(cc2);

		AdvCondition cc = new AdvCondition(c1, c2, AdvCondition.Type_AND);

		Condition cc3 = new Condition(new Expression(a), new Expression(b),
				ConType.equal);
		AdvCondition c3 = new AdvCondition(cc3);
		EvaluationSentence es = new EvaluationSentence(a, new Expression(
				new Expression(a), new Expression(1), ExpType.plus));
		Condition cc4 = new Condition(new Expression(a), new Expression(b),
				ConType.equal);
		AdvCondition c4 = new AdvCondition(cc4);

		ArrayList<EdgeLabel> al = new ArrayList<EdgeLabel>();
		al.add(c3);
		al.add(es);
		al.add(c4);

		ArrayList<AdvCondition> array = new ArrayList<AdvCondition>();
		array.add(cc.substitute(a, new Expression(new Expression(a),
				new Expression(b), ExpType.plus)));
		boolean im = ProverImplFociVampyre.getInstance().isSatisfiable(array);

		assertTrue(im);

		// fail("Not yet implemented"); // TODO
	}

}
