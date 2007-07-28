%{
package prover;

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
	System.out.println("Work completed.\nThe grammar of the foci output file is correct.\n In all, "+formulaNo+" predicates found.");
	for (Predicate s: al)
	{
		System.out.println(s.toString());
	}
	//predicatePool=al;
}
;

formulas:{ArrayList<AdvCondition> al=new ArrayList<AdvCondition>();$$=new FociParserVal(al);} 
 | formulas formula 
 {
 	ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)$1.obj;
 	if($2!=null)
 		al.add((Sentence)$2.obj);
 	$$=new FociParserVal(al);
	formulaNo++;
 	//warning $1 might have changed!
 }
 ;

formula: '=' term term 
 {
	 Condition c=new Condition((Expression)$2.obj,(Expression)$3.obj,ConType.equal);
	 $$=new FociParserVal(new AdvCondition(c));
 }
 | '<' '=' term term {}
 {
	 Condition c=new Condition((Expression)$2.obj,(Expression)$3.obj,ConType.equalsmaller);
	 $$=new FociParserVal(new AdvCondition(c));
 }
 | '&' '[' formulaarray ']'
 {
	 ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)$3.obj;
	 boolean firstEle=true;
	 AdvCondtion latestCon;
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
	 AdvCondtion latestCon;
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

term: NUM {Expression e=new Expression($1.ival);$$=new FociParserVal(e);}
 | WORD {Expression e=new Expression(new Variable(getStringValue($1)));$$=new FociParserVal(e);}
 | '+' '[' termarray ']'
 {
	 ArrayList<Expression> al=(ArrayList<Expression>)$3.obj;
	 boolean firstEle=true;
	 Expression latestExp;
	 for (Expression e: al )
	 {
		 if(firstEle)
		 {
			 latestExp=e;
			 firstEle=false;
		 }
		 else
			 latestExp=new Expression(latestExp,e,ExpType.plus);
	 }
	 $$=new FociParserVal(latestExp);
 }
 | '*' NUM term 
 {
	 Expression e=new Expression(new Expression($2.ival),(Expression)$3.obj,ExpType.multiply);
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

ArrayList<AdvCondition> predicatePool;

StreamTokenizer st;

boolean dflag;
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
  
  pout("yytext:"+yytext+"  char"+(char)tok+" nval: "+st.nval);
  
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
	yylval= new FociParserVal((Object)yytext);
	//pout("WORD from yylex: yytext:"+yytext);
	return WORD;
        //System.out.println("unknown word: "+yytext+" ,return first char.");
    }
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
	sentencePool=new ArrayList<AdvCondition>();
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
