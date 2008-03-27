/**
 * 
 */
package parsers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author KarlHe
 *
 */
public class AdvConditionParserTester {

	AdvConditionParser acp;
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
	 * Test method for {@link parsers.AdvConditionParser#parseString(java.lang.String)}.
	 */
	@Test
	public void testParseString() {		
		acp=new AdvConditionParser(false);
		acp.parseString("2*a+3*b-6*y==2&&t==7");
		assertNotNull(acp.getAdvCondition());
				
		acp.parseString("3*x1+4*x2-6*x3==7");
		assertNotNull(acp.getAdvCondition());
		
		acp.parseString("a1==b&&c==d!");
		assertNull(acp.getAdvCondition());
	}

	/**
	 * Test method for {@link parsers.AdvConditionParser#parseFile(java.io.File)}.
	 */
	@Test
	public void testParseFile() {
		acp=new AdvConditionParser(false);
		acp.parseFile(new File("test/parsers/forACPTest"));
		assertNotNull(acp.getAdvCondition());
		System.out.println(acp.getAdvCondition().toString());
	}

	/**
	 * Test method for {@link parsers.AdvConditionParser#getAdvCondition()}.
	 */
	@Ignore
	@Test
	public void testGetAdvCondition() {
		fail("Not yet implemented");
	}

}
