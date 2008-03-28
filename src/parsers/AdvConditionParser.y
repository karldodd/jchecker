%{

import java.lang.*;
//import java.lang.Math;
import java.io.*;
import java.util.*;

//load self-defined token-library
//import tokens.Sentence;
import tokens.*;
%}

/* YACC Declarations */
%token NUM WORD
//%nonassoc UMINUS
//%nonassoc MINUS
%left '-' '+'
%left '*' '/'
%left '|' '&'
%left '!'
%right NEG 
///* negation--unary minus */
%right '^' 
///* exponentiation */


//%token DCL_INT DCL_BOOL IF ELSE WHILE RETURN ERROR
/*DCL means 'declare'*/

/* Grammar follows */
%%

/* eliminate all the '\n's and ' 's first! */

input: advcondition
{
	topAdvCondition=(AdvCondition)$1.obj;
	System.out.println("AdvConditionParser work completed. "); 
}
;

expression: NUM {Expression e=new Number($1.ival);$$=new AdvConditionParserVal(e);}
 | WORD {Expression e=new Variable(getStringValue($1));$$=new AdvConditionParserVal(e);}
 | expression '+' expression
 {
 	Expression e=new CompositeExpression((Expression)$1.obj,(Expression)$3.obj,CompositeExpression.TYPE_PLUS);
 	$$=new AdvConditionParserVal(e);
 }
 | expression '-' expression
 {
 	Expression e=new CompositeExpression((Expression)$1.obj,(Expression)$3.obj,CompositeExpression.TYPE_MINUS);
 	$$=new AdvConditionParserVal(e);
 }
 | expression '*' expression
 {
 	Expression e=new CompositeExpression((Expression)$1.obj,(Expression)$3.obj,CompositeExpression.TYPE_MULTIPLY);
 	$$=new AdvConditionParserVal(e);
 }
 //| expression '/' expression
 | '-' expression %prec NEG
 {
 	Expression e=new NegativeExpression((Expression)$2.obj);
 	$$=new AdvConditionParserVal(e);
 }
 //| expression '^' expression
 | '(' expression ')'
 {
 	$$=$2;
 }
 ;

advcondition: condition {$$=new AdvConditionParserVal(new AdvCondition((Condition)$1.obj));}
| advcondition '&' '&' advcondition
{
 $$=new AdvConditionParserVal(new AdvCondition((AdvCondition)$1.obj,(AdvCondition)$4.obj,AdvCondition.Type_AND)); 
}

| advcondition '|' '|' advcondition
{
 $$=new AdvConditionParserVal(new AdvCondition((AdvCondition)$1.obj,(AdvCondition)$4.obj,AdvCondition.Type_OR));
}

| '(' advcondition ')'
{$$=$2;}


| '!' advcondition
{$$=$2;((AdvCondition)$$.obj).negateSelf();}

condition: expression '=' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equal);
 	$$=new AdvConditionParserVal(c);
 }
 | expression '!' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.notequal);
 	$$=new AdvConditionParserVal(c);
 }
 | expression '<' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equalsmaller);
 	$$=new AdvConditionParserVal(c);
 }
 | expression '>' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equallarger);
 	$$=new AdvConditionParserVal(c);
 }
 | expression '<' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.smaller);
 	$$=new AdvConditionParserVal(c);
 }
 | expression '>' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.larger);
 	$$=new AdvConditionParserVal(c);
 }
 ;
%%

boolean jobDone=false;
AdvCondition topAdvCondition;

StreamTokenizer st;

boolean dflag;

void pout(String s)
{
  if (dflag)
    System.out.println(s);
}

void yyerror(String s)
{
  System.out.println("Error in AdvConditionParser: "+s);
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
  
  //pout("yytext:"+yytext+"  char"+(char)tok+" nval: "+st.nval);
  
  if(tok==st.TT_EOL)
  {
        System.out.println("end of line.");
  }
  else if(tok==st.TT_NUMBER)
  {
    yylval = new AdvConditionParserVal((int)st.nval);
    return NUM;
  }
  else if(tok==st.TT_WORD)
  {
	yylval= new AdvConditionParserVal((Object)yytext);
	//pout("WORD from yylex: yytext:"+yytext);
	return WORD;
        //System.out.println("unknown word: "+yytext+" ,return first char.");
    //else return yytext.charAt(0);
  }
  //else{pout("Special char returned.")}
  return tok;
}

String getStringValue(AdvConditionParserVal pv)
{
	return (String)pv.obj;
}

private void initialize(){
	jobDone=false;
	topAdvCondition=null;
}

public int parseString(String str)
{
  initialize();

  StringReader sr;
  int ret;
  dflag=true;
  try
  {
  	sr=new StringReader(str);
    st = new StreamTokenizer(sr);

    st.slashStarComments(true);
	st.slashSlashComments(true);
	//识别行结束符;参数为假，将行结束符视作空白符
	st.eolIsSignificant(false);
	//设置引号的符号表示
	st.quoteChar('"');
	st.ordinaryChar('-');
	//st.quoteChar('-');
	//将ASCII码为0-32的字符设为空白符
	st.whitespaceChars(0, 32);
        //st.whitespaceChars(st.TT_EOL,st.TT_EOL);
  }
  catch (Exception e)
  {
    yyerror("could not open stringreader");
    return 0;
  }
  ret=yyparse();
  try
  {
    sr.close();
  }
  catch (Exception e)
  {
    yyerror("could not open source data");
    return 0;
  }
  jobDone=true;
  return ret;
}

public int parseFile(File file)
{
  initialize();

  FileReader fr;
  Reader r;
  FileInputStream fileIn;
  //从字节流创建字符流
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
	//识别行结束符;参数为假，将行结束符视作空白符
	st.eolIsSignificant(false);
	//设置引号的符号表示
	st.quoteChar('"');
	st.ordinaryChar('-');
	//st.quoteChar('-');
	//将ASCII码为0-32的字符设为空白符
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
  jobDone=true;
  return ret;
}

public AdvCondition getAdvCondition(){
	if(jobDone)
		return topAdvCondition;
	else{
		yyerror("The parser has not parsed any input yet.");
		return null;
	}
}

/*
public static void main(String args[])
{
  Parser par = new Parser(false);
  int a=par.parseFile(new File("source.c"));
}
*/

//测试时搞点错误的类型看看
//this program do not want to be a grammar checker although it does some check on the source.

//some problematic things:
//if(a==b&&c==d) ->condition tree
// a+b-4*5 operator priority
//if else support 
