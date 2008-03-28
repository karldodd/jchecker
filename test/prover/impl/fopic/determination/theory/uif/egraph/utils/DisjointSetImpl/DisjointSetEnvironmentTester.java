/**
 * 
 */
package prover.impl.fopic.determination.theory.uif.egraph.utils.DisjointSetImpl;

import prover.impl.fopic.determination.theory.uif.egraph.utils.DisjointSetImpl.Exceptions.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Kaiduo He
 * 
 */
public class DisjointSetEnvironmentTester {

	DisjointSetEnvironment<String> dse;
	DisjointSet<String> ds1;
	DisjointSet<String> ds2;
	DisjointSet<String> ds3;
	String a, b, c;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dse = new DisjointSetEnvironment<String>();
		a = "a";
		b = "b";
		c = "c";
		ds1 = dse.makeSet(a);
		ds2 = dse.makeSet(b);
		ds3 = dse.makeSet(c);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSetEnvironment#DisjointSetEnvironment()}.
	 */
	@Test
	public void testDisjointSetEnvironment() {
		assertNotNull(dse);
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSetEnvironment#makeSet(java.lang.Object)}.
	 */
	@Test
	public void testMakeSet() {
		assertNotNull(ds1);
		assertNotNull(ds2);
		assertNotNull(ds3);
		assertEquals(ds1.getHeadNode().getK(), new String("a"));
		assertEquals(ds2.getLastNode().getK(), new String("b"));
		assertNotSame(ds3.getLastNode().getK(), "c1");
		
		try{
			dse.makeSet(null);
			fail();
		}catch(Exception e){
			if(!(e instanceof NullElementException))
				fail();
		}
		
		try{
			dse.makeSet("c");
			fail();
		}catch(Exception e){
			if(!(e instanceof DuplicateElementException))
				fail();
		}
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSetEnvironment#findSet(java.lang.Object)}.
	 */
	@Test
	public void testFindSet() {
		try {

			assertEquals(ds1, dse.findSet(a));
			assertEquals(ds2, dse.findSet(b));
			assertEquals(ds3, dse.findSet(c));

			DisjointSet<String> ds12 = dse.union(ds1, ds2);
			assertEquals(dse.findSet(a), dse.findSet("b"));
			assertNotSame(dse.findSet(new String("a")), dse.findSet(c));

			DisjointSet<String> ds4 = dse.makeSet("d");
			DisjointSet<String> ds5 = dse.makeSet("e");

			DisjointSet<String> ds34 = dse.union(ds4, ds3);
			DisjointSet<String> ds125 = dse.union(ds5, ds12);
			assertEquals(dse.findSet("d"), dse.findSet("c"));
			assertEquals(dse.findSet("e"), dse.findSet("a"));
			assertEquals(dse.findSet("e"), dse.findSet("b"));

			DisjointSet<String> ds12345 = dse.union(ds125, ds34);
			assertEquals(dse.findSet("e"), dse.findSet("a"));
			assertEquals(dse.findSet("e"), dse.findSet("b"));
			assertEquals(dse.findSet("e"), dse.findSet("c"));
			assertEquals(ds12345, dse.findSet("d"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			fail("No exception should be thrown.");
		}
		
		try{
			dse.findSet("c1");
			fail();
		}catch(Exception e){
			if(!(e instanceof NoSuchElementException))
				fail();
		}
		
		try{
			dse.findSet(null);
			fail();
		}catch(Exception e){
			if(!(e instanceof NullElementException))
				fail();
		}
		
		// assertEquals(dse.findSet("d"), dse.findSet("c"));
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSetEnvironment#union(prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet, prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet)}.
	 */
	@Test
	public void testUnion() {
		try {
			DisjointSet<String> ds4 = dse.makeSet("d");

			DisjointSet<String> ds12 = dse.union(ds1, ds2);
			assertEquals(dse.findSet("a"), dse.findSet("b"));
			DisjointSet<String> ds34 = dse.union(ds3, ds4);
			assertEquals(dse.findSet("d"), dse.findSet("c"));
			DisjointSet<String> ds1234 = dse.union(ds12, ds34);
			assertEquals(dse.findSet("a"), dse.findSet("d"));
			assertEquals(dse.findSet("b"), dse.findSet("c"));

			dse = new DisjointSetEnvironment<String>();

			dse.makeSet("1");
			dse.makeSet("3");
			dse.makeSet("5");
			dse.makeSet("7");
			dse.makeSet("9");

			System.out.println(dse.getCurrentSets().size());
			System.out.println("Go Go Go!");

			while (dse.getCurrentSets().size() > 1) {
				DisjointSet<String> ds0 = dse.getCurrentSets().get(0);
				DisjointSet<String> ds1 = dse.getCurrentSets().get(1);
				dse.union(ds0, ds1);
			}

			assertTrue(dse.getCurrentSets().get(0).getSize() == 5);

			int sum = 0;
			for (Node<String> s : dse.getCurrentSets().get(0)) {
				sum += Integer.parseInt(s.getK());
			}
			assertEquals(sum, 25);
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}

}
