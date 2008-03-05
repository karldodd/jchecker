//#############################################
//## file: FociParser.java
//## Generated by Byacc/j
//#############################################
package parsers;

/**
 * BYACC/J Semantic Value for parser: FociParser
 * This class provides some of the functionality
 * of the yacc/C 'union' directive
 * 
 * @author He Kaiduo
 */
public class FociParserVal
{
/**
 * integer value of this 'union'
 */
public int ival;

/**
 * double value of this 'union'
 */
public double dval;

/**
 * string value of this 'union'
 */
public String sval;

/**
 * object value of this 'union'
 */
public Object obj;

//#############################################
//## C O N S T R U C T O R S
//#############################################
/**
 * Initialize me without a value
 */
public FociParserVal()
{
}
/**
 * Initialize me as an int
 */
public FociParserVal(int val)
{
  ival=val;
}

/**
 * Initialize me as a double
 */
public FociParserVal(double val)
{
  dval=val;
}

/**
 * Initialize me as a string
 */
public FociParserVal(String val)
{
  sval=val;
}

/**
 * Initialize me as an Object
 */
public FociParserVal(Object val)
{
  obj=val;
}
}//end class

//#############################################
//## E N D    O F    F I L E
//#############################################
