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


%token DCL_INT DCL_BOOL IF ELSE WHILE RETURN ERROR
/*DCL means 'declare'*/

/* Grammar follows */
%%

/* eliminate all the '\n's and ' 's first! */

input: sentences 
{ArrayList<Sentence> al=(ArrayList<Sentence>)$1.obj;
System.out.println("Work completed.\nThe grammar of the file is correct.\n   "+sentenceNo+ " sentence(s) caught in all.  "+al.size());
	for (Sentence s: al)
	{
		System.out.println(s.toString());
	}
}
 ;

sentences:{ArrayList<Sentence> al=new ArrayList<Sentence>();$$=new ParserVal(al);} 
 | sentences sentence 
 {
 	ArrayList<Sentence> al=(ArrayList<Sentence>)$1.obj;
 	if($2!=null)
 		al.add((Sentence)$2.obj);
 	$$=new ParserVal(al);
 	//warning $1 might have changed!
 }
 ;

sentence: evaluationsentence {sentenceNo++;}
 | decisionsentence 
 | declarationsentence {sentenceNo++;}
 | returnsentence {sentenceNo++;}
 | errorsentence {sentenceNo++;}
 ;

returnsentence: RETURN ';' {$$=new ParserVal(new ReturnSentence(null));}
 | RETURN expression ';'{$$=new ParserVal(new ReturnSentence((Expression)$2.obj));}
 ;
 
errorsentence: ERROR ';' {$$=new ParserVal(new ErrorSentence());}
;

decisionsentence: ifsentence
| whilesentence
;

evaluationsentence: WORD '=' expression ';'
 {
 	Variable v=new Variable(getStringValue($1));
 	Expression e=(Expression)$3.obj;
 	$$=new ParserVal(new EvaluationSentence(v,e));
 }
 ;

//We do not care about the declaration is good or not.
//We simply ignore them.
declarationsentence: DCL_INT words ';' {$$=null;}
| DCL_BOOL words ';' {$$=null;}
;

words: WORD {}
| words ',' WORD {}
;

expression: NUM {Expression e=new Expression($1.ival);$$=new ParserVal(e);}
 | WORD {Expression e=new Expression(new Variable(getStringValue($1)));$$=new ParserVal(e);}
 | expression '+' expression
 {
 	Expression e=new Expression((Expression)$1.obj,(Expression)$3.obj,ExpType.plus);
 	$$=new ParserVal(e);
 }
 | expression '-' expression
 {
 	Expression e=new Expression((Expression)$1.obj,(Expression)$3.obj,ExpType.minus);
 	$$=new ParserVal(e);
 }
 | expression '*' expression
 {
 	Expression e=new Expression((Expression)$1.obj,(Expression)$3.obj,ExpType.multiply);
 	$$=new ParserVal(e);
 }
 //| expression '/' expression
 | '-' expression %prec NEG
 {
 	Expression e=new Expression((Expression)$2.obj,null,ExpType.negative);
 	$$=new ParserVal(e);
 }
 //| expression '^' expression
 | '(' expression ')'
 {
 	$$=$2;
 }
 ;

advcondition: condition {$$=new ParserVal(new AdvCondition((Condition)$1.obj));}
 /*expression '=' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equal);
 	$$=new ParserVal(new AdvCondition(c));
 }
 | expression '!' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.notequal);
 	$$=new ParserVal(new AdvCondition(c));
 }
 | expression '<' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equalsmaller);
 	$$=new ParserVal(new AdvCondition(c));
 }
 | expression '>' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equallarger);
 	$$=new ParserVal(new AdvCondition(c));
 }
 | expression '<' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.smaller);
 	$$=new ParserVal(new AdvCondition(c));
 }
 | expression '>' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.larger);
 	$$=new ParserVal(new AdvCondition(c));
 }
 */
| advcondition '&' '&' advcondition
{
 $$=new ParserVal(new AdvCondition((AdvCondition)$1.obj,(AdvCondition)$4.obj,AdvCondition.Type_AND)); 
}

| advcondition '|' '|' advcondition
{
 $$=new ParserVal(new AdvCondition((AdvCondition)$1.obj,(AdvCondition)$4.obj,AdvCondition.Type_OR));
}

| '(' advcondition ')'
{$$=$2;}


| '!' advcondition
{$$=$2;((AdvCondition)$$.obj).negateSelf();}

/* //declaration sentences may not appear in if/while sentence? */
ifsentence: IF '(' advcondition ')' evaluationsentence
{
	AdvCondition c=(AdvCondition)$3.obj;
	EvaluationSentence es=(EvaluationSentence)$5.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.add(es);
	$$=new ParserVal(ds);
}
| IF '(' advcondition ')' decisionsentence
{
	AdvCondition c=(AdvCondition)$3.obj;
	DecisionSentence old=(DecisionSentence)$5.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.add(old);
	$$=new ParserVal(ds);
}
| IF '(' advcondition ')' '{' sentences '}'
{
	AdvCondition c=(AdvCondition)$3.obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)$6.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.addAll(al);
	$$=new ParserVal(ds);
}
| IF '(' advcondition ')' '{' sentences '}' ELSE '{' sentences '}'
{
	AdvCondition c=(AdvCondition)$3.obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)$6.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.addAll(al);
	ArrayList<Sentence> al2=(ArrayList<Sentence>)$10.obj;
	ds.addAllForElse(al2);
	$$=new ParserVal(ds);
}
;

whilesentence: WHILE '(' advcondition ')' evaluationsentence
{
	AdvCondition c=(AdvCondition)$3.obj;
	EvaluationSentence es=(EvaluationSentence)$5.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.add(es);
	$$=new ParserVal(ds);
}
| WHILE '(' advcondition ')' decisionsentence
{
	AdvCondition c=(AdvCondition)$3.obj;
	DecisionSentence old=(DecisionSentence)$5.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.add(old);
	$$=new ParserVal(ds);
}
| WHILE '(' advcondition ')' '{' sentences '}'
{
	AdvCondition c=(AdvCondition)$3.obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)$6.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.addAll(al);
	$$=new ParserVal(ds);
}
;

condition: expression '=' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equal);
 	$$=new ParserVal(c);
 }
 | expression '!' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.notequal);
 	$$=new ParserVal(c);
 }
 | expression '<' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equalsmaller);
 	$$=new ParserVal(c);
 }
 | expression '>' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equallarger);
 	$$=new ParserVal(c);
 }
 | expression '<' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.smaller);
 	$$=new ParserVal(c);
 }
 | expression '>' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.larger);
 	$$=new ParserVal(c);
 }
/*
 | '(' condition ')'
 {
 	$$=$2;
 }
 | '!' condition
 {
 	$$=$2;
 	((Condition)$$.obj).negateSelf();
 }*/
 ;
%%
ArrayList<Sentence> sentencePool;

StreamTokenizer st;

boolean dflag;
int sentenceNo=0;

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
  
  pout("yytext:"+yytext+"  char"+(char)tok+" nval: "+st.nval);
  
  if(tok==st.TT_EOL)
  {
        System.out.println("end of line.");
  }
  else if(tok==st.TT_NUMBER)
  {
    yylval = new ParserVal((int)st.nval);
    return NUM;
  }
  else if(tok==st.TT_WORD)
  {
    if (yytext.equals("if"))
      return IF;
    else if (yytext.equals("else"))
      return ELSE;
    else if (yytext.equals("while"))
      return WHILE;
    else if (yytext.equals("int"))
      return DCL_INT;
    else if (yytext.equals("bool"))
      return DCL_BOOL;
    else if (yytext.equals("return"))
      return RETURN;
    else if (yytext.equals("ERROR"))
      return ERROR;
    else
    {
	yylval= new ParserVal((Object)yytext);
	//pout("WORD from yylex: yytext:"+yytext);
	return WORD;
        //System.out.println("unknown word: "+yytext+" ,return first char.");
    }
    //else return yytext.charAt(0);
  }
  //else{pout("Special char returned.")}
  return tok;
}

String getStringValue(ParserVal pv)
{
	return (String)pv.obj;
}

void initialize(){
	sentencePool=new ArrayList<Sentence>();
}

int doTest(File file)
{
  FileReader fr;
  Reader r;
  FileInputStream fileIn;
  //从字节流创建字符流
  InputStreamReader inReader;
  int ret;
  dflag=true;
  try
  {
	fileIn = new FileInputStream("source.c");
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
  return ret;
}

public static void main(String args[])
{
  Parser par = new Parser(false);
  par.initialize();
  int a=par.doTest(new File("source.c"));
}

//this program do not want to be a grammar checker although it does some check on the source.

//some problematic things:
//if(a==b&&c==d) ->condition tree
// a+b-4*5 operator priority
//if else support 
