%{
//package prover;

import java.lang.*;
//import java.lang.Math;
import java.io.*;
import java.util.*;

//load self-defined token-library
//import tokens.Sentence;
import tokens.*;
import abstraction.*;

%}

/* YACC Declarations */
%token NUM WORD

%token TRUESTR FALSESTR
//%nonassoc UMINUS
//%nonassoc MINUS
%left '+'
%left '*'
%left '|' '&'
//%left '!'
%right NEG 
///* negation--unary minus */
%right '~' 
///* exponentiation */


//%token DCL_INT DCL_BOOL IF ELSE WHILE RETURN ERROR
/*DCL means 'declare'*/

/* Grammar follows */
%%

/* eliminate all the '\n's and ' 's first! */

input: formulas 
{
	ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)$1.obj;
	/*System.out.println("Work completed.\nIn all, "+formulaNo+" predicates found.");
	for (AdvCondition s: al)
	{
		System.out.println(s.toString());
	}*/
	conditionPool=al;
	//predicatePool=al;
}
;

formulas:{ArrayList<AdvCondition> al=new ArrayList<AdvCondition>();$$=new FociParserVal(al);} 
 | formulas formula 
 {
 	ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)$1.obj;
 	if($2!=null)
 		al.add((AdvCondition)$2.obj);
 	$$=new FociParserVal(al);
	//if(debugging)System.out.println("formula inserted: "+((AdvCondition)$2.obj).toString());
	formulaNo++;
 	//warning $1 might have changed!
 }
 ;

formula:TRUESTR
 {
	 Condition c = new Condition(true);
	 $$=new FociParserVal(new AdvCondition(c));
 }
 | FALSESTR
 {
	 Condition c = new Condition(false);
	 $$=new FociParserVal(new AdvCondition(c));
 }

 | '=' term term 
 {
	 Condition c=new Condition((Expression)$2.obj,(Expression)$3.obj,ConType.equal);
	 $$=new FociParserVal(new AdvCondition(c));
	 //if(debugging)System.out.println("formula got: "+c.toString());
 }
 | '<' '=' term term
 {
	 Condition c=new Condition((Expression)$3.obj,(Expression)$4.obj,ConType.equalsmaller);
	 $$=new FociParserVal(new AdvCondition(c));
	 
 }
 | '&' '[' formulaarray ']'
 {
	 ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)$3.obj;
	 boolean firstEle=true;
	 AdvCondition latestCon=null;
	 for (AdvCondition c: al )
	 {
		 if(firstEle)
		 {
			 latestCon=c;
			 firstEle=false;
		 }
		 else
			 latestCon=new AdvCondition(latestCon,c,AdvCondition.Type_AND);
	 }
	 $$=new FociParserVal(latestCon);
 }
 | '|' '[' formulaarray ']'
 {
	 ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)$3.obj;
	 boolean firstEle=true;
	 AdvCondition latestCon=null;
	 for (AdvCondition c: al )
	 {
		 if(firstEle)
		 {
			 latestCon=c;
			 firstEle=false;
		 }
		 else
			 latestCon=new AdvCondition(latestCon,c,AdvCondition.Type_OR);
	 }
	 $$=new FociParserVal(latestCon);
 }
 | '~' formula {AdvCondition c=(AdvCondition)$2.obj;c.negateSelf();$$=new FociParserVal(c);}
 | '(' formula ')' {$$=$2;}
 ;

formulaarray: formula formula
 {
	 ArrayList<AdvCondition> al=new ArrayList<AdvCondition>();
	 al.add((AdvCondition)$1.obj);
 	 al.add((AdvCondition)$2.obj);
	 $$=new FociParserVal(al);
 }
 | formulaarray formula
 {
	 ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)$1.obj;
	 al.add((AdvCondition)$2.obj);	
	 $$=new FociParserVal(al);
 }
 ;

term: NUM {Expression e=new tokens.Number($1.ival);$$=new FociParserVal(e);}
 | WORD
 {
	 String withSuffix=getStringValue($1);
	 String withoutSuffix;
	 if(revertMap.containsKey(withSuffix))
		 withoutSuffix=revertMap.get(withSuffix);
	 else
		 withoutSuffix=withSuffix;
	Expression e=new Variable(withoutSuffix);$$=new FociParserVal(e);
	if(debugging)System.out.println("term is word: "+e.toString());
 }
 | '+' '[' termarray ']'
 {
	 ArrayList<Expression> al=(ArrayList<Expression>)$3.obj;
	 boolean firstEle=true;
	 Expression latestExp=null;
	 for (Expression e: al )
	 {
		 if(firstEle)
		 {
			 latestExp=e;
			 firstEle=false;
		 }
		 else
			 latestExp=new CompositeExpression(latestExp,e,CompositeExpression.TYPE_PLUS);
	 }
	 $$=new FociParserVal(latestExp);
 }
 | NUM '*' term 
 {
	 Expression e=new CompositeExpression(new tokens.Number($1.ival),(Expression)$3.obj,CompositeExpression.TYPE_MULTIPLY);
	 $$=new FociParserVal(e);
 }
 | '(' term ')' {$$=$2;}
 ;

termarray: term term 
 {
	 ArrayList<Expression> al=new ArrayList<Expression>();
	 al.add((Expression)$1.obj);
 	 al.add((Expression)$2.obj);
	 $$=new FociParserVal(al);
 }
 | termarray term
 {
	 ArrayList<Expression> al=(ArrayList<Expression>)$1.obj;
	 al.add((Expression)$2.obj);	
	 $$=new FociParserVal(al);
 }
 ;
%%
boolean loaded=false;
ArrayList<AdvCondition> conditionPool;
Map<String,String> revertMap;

StreamTokenizer st;

boolean dflag;
boolean debugging=false;
int formulaNo=0;

void pout(String s)
{
  if (dflag)
    System.out.println(s);
}

void yyerror(String s)
{
  System.out.println("par:"+s);
}

int yylex()
{
  int tok;
  try
  {
     tok = st.nextToken();
  }
  catch (Exception e)
  {
    yyerror("tokenizer:"+e.toString());
    return -1;  //indicates error
  }
  if (tok==st.TT_EOF)
    return 0;  //done

  yytext=st.sval;//string value
  
  if(debugging)pout("yytext:"+yytext+"  char"+(char)tok+" nval: "+st.nval);
  
  if(tok==st.TT_EOL)
  {
        System.out.println("end of line.");
  }
  else if(tok==st.TT_NUMBER)
  {
    yylval = new FociParserVal((int)st.nval);
    return NUM;
  }
  else if(tok==st.TT_WORD)
  {
	//yylval=new FociParserVal(yytext);
	yylval=new FociParserVal((Object)yytext);
	//pout("yytext: "+yytext);
	if(debugging)pout("TT_WORD recognized: "+(String)yylval.obj);
	if(yytext.equals("true")){
		return TRUESTR;
	}
	else if(yytext.equals("false")){
		return FALSESTR;
	}
	//pout("WORD from yylex: yytext:"+yytext);
	return WORD;
        //System.out.println("unknown word: "+yytext+" ,return first char.");
    
    //else return yytext.charAt(0);
  }
  //else{pout("Special char returned.")}
  return tok;
}

String getStringValue(FociParserVal pv)
{
	return (String)pv.obj;
}

void initialize(){
	loaded=false;
	formulaNo=0;
	conditionPool=new ArrayList<AdvCondition>();
}

public int parseFile(File file, Map<String,String> revertMap)
{
  initialize();
  this.revertMap=revertMap;

  FileReader fr;
  Reader r;
  FileInputStream fileIn;
  InputStreamReader inReader;
  int ret;
  dflag=true;
  try
  {
	fileIn = new FileInputStream(file);
	inReader = new InputStreamReader(fileIn);
    st = new StreamTokenizer(inReader); 

    st.slashStarComments(true);
	st.slashSlashComments(true);
	st.eolIsSignificant(false);
	st.quoteChar('"');
	//st.ordinaryChar('-');
	//st.quoteChar('-');
	st.whitespaceChars(0, 32);
        //st.whitespaceChars(st.TT_EOL,st.TT_EOL);
  }
  catch (Exception e)
  {
    yyerror("could not open "+file.getName());
    return 0;
  }
  ret=yyparse();
  try
  {
    inReader.close();
    fileIn.close();
  }
  catch (Exception e)
  {
    yyerror("could not open source data");
    return 0;
  }
  loaded=true;
  return ret;
}

public ArrayList<AdvCondition> getConditionPool()
{
	if(loaded)return conditionPool;
	else
	{
		System.err.println("The parser has not parsed any file yet.");
		return new ArrayList<AdvCondition>();
	}
}

public static void main(String args[])
{
  //FociParser par = new FociParser(false,map);
  //par.parseFile(new File(args[0]));
}

//this program do not want to be a grammar checker although it does some check on the source.

//some problematic things:
//if(a==b&&c==d) ->condition tree
// a+b-4*5 operator priority
//if else support 

/*
	 AdvCondition latestCon;
	 for (AdvCondition c: al )
	 {
		 if(firstEle)
		 {
			 latestCon=c;
			 firstEle=false;
		 }
		 else
			 latestCon=new AdvCondition(latestCon,c,AdvCondition.Type_AND);
	 }
	 
	 The above can be determined by model checker to help compiler learn the reachability.
*/
