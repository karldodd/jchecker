package prover;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import prover.impl.xxx.determination.Theory_LI.SimplexStructTester;
import prover.impl.xxx.determination.Theory_LI.SimplexTester;

import prover.impl.xxx.determination.Theory_LI.SimplexStruct;
//import prover.impl.xxx.egraph.utils.DisjointSetImpl.*;
/**
 * 单元测试的集合，将各TestCase组合成为TestSuite
 * 
 * @author Kaiduo He
 *
 */


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ProverFactoryTester.class,
	ProverImplFociVampyreTester.class,
//	impl.xxx.egraph.utils.DisjointSetImpl.DisjointSetEnvironmentTester.class
//	DisjointSetEnvironmentTester.class,
//	DisjointSetTester.class,

    // Add tests for SimplexCore
    SimplexStructTester.class,
    SimplexTester.class
})


public class AllTests {
	//public static Test suite() {
		//TestSuite suite = new TestSuite("Test for prover");
		//$JUnit-BEGIN$	
		//$JUnit-END$
		//return suite;
	//}
}
