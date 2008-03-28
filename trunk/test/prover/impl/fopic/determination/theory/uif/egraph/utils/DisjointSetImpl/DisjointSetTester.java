/**
 * 
 */
package prover.impl.fopic.determination.theory.uif.egraph.utils.DisjointSetImpl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

/**
 * @author Kaiduo He
 * 
 */
public class DisjointSetTester {

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

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		try {
			DisjointSetEnvironment<String> dse1 = new DisjointSetEnvironment<String>();
			DisjointSet<String> dsa1 = dse1.makeSet("a");
			dse1.makeSet("b");
			DisjointSetEnvironment<String> dse2 = new DisjointSetEnvironment<String>();
			DisjointSet<String> dsa2 = dse2.makeSet("a");
			dse2.makeSet("b");
			assertEquals(dsa1.hashCode(), dsa2.hashCode());

			DisjointSet<String> dsab1 = dse1.union(dse1.findSet("a"), dse1
					.findSet("b"));
			DisjointSet<String> dsab2 = dse2.findSet("a");
			assertNotSame(dsab1.hashCode(), dsab2.hashCode());
			dsab2 = dse2.union(dse2.findSet("a"), dse2.findSet("b"));
			assertEquals(dsab1.hashCode(), dsab2.hashCode());
			assertEquals(dse1.findSet("b").hashCode(), dse2.findSet("a")
					.hashCode());
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		try {
			DisjointSetEnvironment<String> dse1 = new DisjointSetEnvironment<String>();
			DisjointSet<String> dsa1 = dse1.makeSet("a");
			dse1.makeSet("b");
			DisjointSetEnvironment<String> dse2 = new DisjointSetEnvironment<String>();
			DisjointSet<String> dsa2 = dse2.makeSet("a");
			dse2.makeSet("b");
			assertEquals(dsa1, dsa2);

			DisjointSet<String> dsab1 = dse1.union(dse1.findSet("a"), dse1
					.findSet("b"));
			DisjointSet<String> dsab2 = dse2.findSet("a");
			assertNotSame(dsab1, dsab2);
			dsab2 = dse2.union(dse2.findSet("a"), dse2.findSet("b"));
			assertEquals(dsab1, dsab2);
			assertEquals(dse1.findSet("b"), dse2.findSet("a"));

			assertNotSame(dse2.union(dsab2, dse2.makeSet("c")), dsab1);
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet#DisjointSet(java.lang.Object)}.
	 */
	@Test
	public void testDisjointSet() {
		try {
			DisjointSetEnvironment<String> dse1 = new DisjointSetEnvironment<String>();
			DisjointSet<String> dsa1 = dse1.makeSet("a");

			assertNotNull(dse1);
			assertNotNull(dsa1);
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet#getHeadNode()}.
	 */
	@Test
	public void testGetHeadNode() {
		try {
			DisjointSetEnvironment<String> dse1 = new DisjointSetEnvironment<String>();
			DisjointSet<String> dsa1 = dse1.makeSet("a");

			assertNotNull(dsa1.getHeadNode());
			assertEquals(dsa1.getHeadNode().getK(), "a");
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet#getLastNode()}.
	 */
	@Test
	public void testGetLastNode() {
		try {
			DisjointSetEnvironment<String> dse1 = new DisjointSetEnvironment<String>();
			DisjointSet<String> dsa1 = dse1.makeSet("a");

			assertNotNull(dsa1.getLastNode());
			assertEquals(dsa1.getLastNode().getK(), "a");
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet#getSize()}.
	 */
	@Test
	public void testGetSize() {
		try {
			DisjointSetEnvironment<String> dse1 = new DisjointSetEnvironment<String>();
			DisjointSet<String> dsa1 = dse1.makeSet("a");
			assertEquals(dsa1.getSize(), 1);
			DisjointSet<String> dsa2 = dse1.makeSet("b");
			assertEquals(dse1.union(dsa1, dsa2).getSize(), 2);
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet#setSize(int)}.
	 */
	@Ignore
	@Test
	public void testSetSize() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSet#iterator()}.
	 */
	@Test
	public void testIterator() {
		try {
			DisjointSetEnvironment<String> dse1 = new DisjointSetEnvironment<String>();
			dse1.makeSet("a");
			dse1.makeSet("b");
			dse1.makeSet("c");
			while (dse1.getCurrentSets().size() > 1) {
				dse1.union(dse1.getCurrentSets().get(0), dse1.getCurrentSets()
						.get(1));
			}
			int counter=0;
			for ( Node<String> n : dse1.getCurrentSets().get(0))
			{
				counter++;
			}
			assertEquals(counter, 3);
		} catch (Exception e) {
			fail("No exception should be thrown.");
		}
	}

}
