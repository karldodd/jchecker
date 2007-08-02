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
	sentencePool=al;
	System.out.println("Sentence pool is ready.");
}
 ;

sentences:{ArrayList<Sentence> al=new ArrayList<Sentence>();$$=new SourceParserVal(al);} 
 | sentences sentence 
 {
 	ArrayList<Sentence> al=(ArrayList<Sentence>)$1.obj;
 	if($2!=null)
 		al.add((Sentence)$2.obj);
 	$$=new SourceParserVal(al);
 	//warning $1 might have changed!
 }
 ;

sentence: evaluationsentence {sentenceNo++;}
 | decisionsentence 
 | declarationsentence {sentenceNo++;}
 | returnsentence {sentenceNo++;}
 | errorsentence {sentenceNo++;}
 ;

returnsentence: RETURN ';' {$$=new SourceParserVal(new ReturnSentence(null));}
 | RETURN expression ';'{$$=new SourceParserVal(new ReturnSentence((Expression)$2.obj));}
 ;
 
errorsentence: ERROR ';' {$$=new SourceParserVal(new ErrorSentence());}
;

decisionsentence: ifsentence
| whilesentence
;

evaluationsentence: WORD '=' expression ';'
 {
 	Variable v=new Variable(getStringValue($1));
 	Expression e=(Expression)$3.obj;
 	$$=new SourceParserVal(new EvaluationSentence(v,e));
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

expression: NUM {Expression e=new Expression($1.ival);$$=new SourceParserVal(e);}
 | WORD {Expression e=new Expression(new Variable(getStringValue($1)));$$=new SourceParserVal(e);}
 | expression '+' expression
 {
 	Expression e=new Expression((Expression)$1.obj,(Expression)$3.obj,ExpType.plus);
 	$$=new SourceParserVal(e);
 }
 | expression '-' expression
 {
 	Expression e=new Expression((Expression)$1.obj,(Expression)$3.obj,ExpType.minus);
 	$$=new SourceParserVal(e);
 }
 | expression '*' expression
 {
 	Expression e=new Expression((Expression)$1.obj,(Expression)$3.obj,ExpType.multiply);
 	$$=new SourceParserVal(e);
 }
 //| expression '/' expression
 | '-' expression %prec NEG
 {
 	Expression e=new Expression((Expression)$2.obj,null,ExpType.negative);
 	$$=new SourceParserVal(e);
 }
 //| expression '^' expression
 | '(' expression ')'
 {
 	$$=$2;
 }
 ;

advcondition: condition {$$=new SourceParserVal(new AdvCondition((Condition)$1.obj));}
 /*expression '=' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equal);
 	$$=new SourceParserVal(new AdvCondition(c));
 }
 | expression '!' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.notequal);
 	$$=new SourceParserVal(new AdvCondition(c));
 }
 | expression '<' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equalsmaller);
 	$$=new SourceParserVal(new AdvCondition(c));
 }
 | expression '>' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equallarger);
 	$$=new SourceParserVal(new AdvCondition(c));
 }
 | expression '<' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.smaller);
 	$$=new SourceParserVal(new AdvCondition(c));
 }
 | expression '>' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.larger);
 	$$=new SourceParserVal(new AdvCondition(c));
 }
 */
| advcondition '&' '&' advcondition
{
 $$=new SourceParserVal(new AdvCondition((AdvCondition)$1.obj,(AdvCondition)$4.obj,AdvCondition.Type_AND)); 
}

| advcondition '|' '|' advcondition
{
 $$=new SourceParserVal(new AdvCondition((AdvCondition)$1.obj,(AdvCondition)$4.obj,AdvCondition.Type_OR));
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
	$$=new SourceParserVal(ds);
}
| IF '(' advcondition ')' decisionsentence
{
	AdvCondition c=(AdvCondition)$3.obj;
	DecisionSentence old=(DecisionSentence)$5.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.add(old);
	$$=new SourceParserVal(ds);
}
| IF '(' advcondition ')' '{' sentences '}'
{
	AdvCondition c=(AdvCondition)$3.obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)$6.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.addAll(al);
	$$=new SourceParserVal(ds);
}
| IF '(' advcondition ')' '{' sentences '}' ELSE '{' sentences '}'
{
	AdvCondition c=(AdvCondition)$3.obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)$6.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.addAll(al);
	ArrayList<Sentence> al2=(ArrayList<Sentence>)$10.obj;
	ds.addAllForElse(al2);
	$$=new SourceParserVal(ds);
}
;

whilesentence: WHILE '(' advcondition ')' evaluationsentence
{
	AdvCondition c=(AdvCondition)$3.obj;
	EvaluationSentence es=(EvaluationSentence)$5.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.add(es);
	$$=new SourceParserVal(ds);
}
| WHILE '(' advcondition ')' decisionsentence
{
	AdvCondition c=(AdvCondition)$3.obj;
	DecisionSentence old=(DecisionSentence)$5.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.add(old);
	$$=new SourceParserVal(ds);
}
| WHILE '(' advcondition ')' '{' sentences '}'
{
	AdvCondition c=(AdvCondition)$3.obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)$6.obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.addAll(al);
	$$=new SourceParserVal(ds);
}
;

condition: expression '=' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equal);
 	$$=new SourceParserVal(c);
 }
 | expression '!' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.notequal);
 	$$=new SourceParserVal(c);
 }
 | expression '<' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equalsmaller);
 	$$=new SourceParserVal(c);
 }
 | expression '>' '=' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$4.obj,ConType.equallarger);
 	$$=new SourceParserVal(c);
 }
 | expression '<' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.smaller);
 	$$=new SourceParserVal(c);
 }
 | expression '>' expression
 {
 	Condition c=new Condition((Expression)$1.obj,(Expression)$3.obj,ConType.larger);
 	$$=new SourceParserVal(c);
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
boolean sentenceLoaded=false;
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
    yylval = new SourceParserVal((int)st.nval);
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
	yylval= new SourceParserVal((Object)yytext);
	//pout("WORD from yylex: yytext:"+yytext);
	return WORD;
        //System.out.println("unknown word: "+yytext+" ,return first char.");
    }
    //else return yytext.charAt(0);
  }
  //else{pout("Special char returned.")}
  return tok;
}

String getStringValue(SourceParserVal pv)
{
	return (String)pv.obj;
}

private void initialize(){
	sentenceLoaded=false;
	sentencePool=new ArrayList<Sentence>();
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
  sentenceLoaded=true;
  return ret;
}

public ArrayList<Sentence> getSentencePool(){
	if(sentenceLoaded)
		return sentencePool;
	else{
		System.err.println("The parser has not parsed any file yet.");
		return new ArrayList<Sentence>();
	}
}
/*
public static void main(String args[])
{
  Parser par = new Parser(false);
  int a=par.parseFile(new File("source.c"));

}
*/

//this program do not want to be a grammar checker although it does some check on the source.

//some problematic things:
//if(a==b&&c==d) ->condition tree
// a+b-4*5 operator priority
//if else support 
