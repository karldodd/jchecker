//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package parsers;



//#line 1 "src/parsers/AdvConditionParser.y"


import java.lang.*;
/*import java.lang.Math;*/
import java.io.*;
import java.util.*;

/*load self-defined token-library*/
/*import tokens.Sentence;*/
import tokens.*;
//#line 28 "AdvConditionParser.java"




public class AdvConditionParser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class AdvConditionParserVal is defined in AdvConditionParserVal.java


String   yytext;//user variable to return contextual strings
AdvConditionParserVal yyval; //used to return semantic vals from action routines
AdvConditionParserVal yylval;//the 'lval' (result) I got from yylex()
AdvConditionParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new AdvConditionParserVal[YYSTACKSIZE];
  yyval=new AdvConditionParserVal();
  yylval=new AdvConditionParserVal();
  valptr=-1;
}
void val_push(AdvConditionParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
AdvConditionParserVal val_pop()
{
  if (valptr<0)
    return new AdvConditionParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
AdvConditionParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new AdvConditionParserVal();
  return valstk[ptr];
}
//#### end semantic value section ####
public final static short NUM=257;
public final static short WORD=258;
public final static short NEG=259;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    2,    2,    2,    2,    2,    2,    1,    1,
    1,    1,    1,    3,    3,    3,    3,    3,    3,
};
final static short yylen[] = {                            2,
    1,    1,    1,    3,    3,    3,    2,    3,    1,    4,
    4,    3,    2,    4,    4,    4,    4,    3,    3,
};
final static short yydefred[] = {                         0,
    2,    3,    0,    0,    0,    0,    0,    0,    9,    0,
    7,   13,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   12,    8,    0,    0,    0,    0,
    6,    0,    0,    0,    0,    0,    0,   11,   10,    0,
    0,    0,    0,
};
final static short yydgoto[] = {                          6,
    7,    8,    9,
};
final static short yysindex[] = {                       -33,
    0,    0,  -35,  -33,  -33,    0,  -34,   58,    0,  -35,
    0,    0,  -25,   71, -115,  -20,  -35,  -35,  -35,  -36,
  -28,  -39,  -37,  -11,    0,    0,  -33,  -33,  -16,  -16,
    0,  -35,  -35,  -35,  -22,  -35,  -22,    0,    0,  -22,
  -22,  -22,  -22,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   39,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    2,   11,
    0,    0,    0,    0,   17,    0,   19,    0,    0,   27,
   28,   29,   36,
};
final static short yygindex[] = {                         0,
   10,   75,    0,
};
final static int YYTABLESIZE=225;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          4,
   10,    5,   10,   16,   10,    3,    5,    3,   27,    3,
    4,    3,   16,   12,   13,   25,   18,   28,   19,   19,
   18,   34,   17,   36,   32,   19,   15,   14,   16,   26,
   19,   18,   33,   17,    5,   17,   38,   39,    1,    5,
    0,    0,    5,    4,    5,    0,    5,    0,    4,    0,
    0,    4,    0,    4,   18,    4,   19,   18,    0,   19,
    0,    5,    5,    5,   15,   14,   16,   15,   14,   16,
    4,    4,    4,   17,    0,    0,   17,   11,    0,   14,
    0,    0,    0,    0,   24,    0,    0,    0,    0,   15,
   20,   29,   30,   31,    0,    0,   35,   37,   15,   19,
   18,    0,   17,   20,    0,    0,   40,   41,   42,    0,
   43,   26,   19,   18,    0,   17,    0,   22,   21,   23,
    0,    0,    0,    0,    0,    5,    0,    0,    0,    0,
   22,   21,   23,    0,    4,    0,    0,    0,    0,    0,
   18,    0,   19,    0,    0,    0,    0,    0,    0,    0,
   15,   14,   16,    0,    0,    0,    0,    0,    0,   17,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    2,    1,
    2,    1,    2,    1,    2,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   40,    0,   40,   38,   40,   45,   40,   45,  124,   45,
    0,   45,   38,    4,    5,   41,    0,   38,    0,   42,
   43,   61,   45,   61,   61,   42,    0,    0,    0,   41,
   42,   43,   61,   45,   33,    0,   27,   28,    0,   38,
   -1,   -1,   41,   33,   43,   -1,   45,   -1,   38,   -1,
   -1,   41,   -1,   43,   38,   45,   38,   41,   -1,   41,
   -1,   60,   61,   62,   38,   38,   38,   41,   41,   41,
   60,   61,   62,   38,   -1,   -1,   41,    3,   -1,    5,
   -1,   -1,   -1,   -1,   10,   -1,   -1,   -1,   -1,  124,
   33,   17,   18,   19,   -1,   -1,   22,   23,  124,   42,
   43,   -1,   45,   33,   -1,   -1,   32,   33,   34,   -1,
   36,   41,   42,   43,   -1,   45,   -1,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,  124,   -1,   -1,   -1,   -1,
   60,   61,   62,   -1,  124,   -1,   -1,   -1,   -1,   -1,
  124,   -1,  124,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  124,  124,  124,   -1,   -1,   -1,   -1,   -1,   -1,  124,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  257,
  258,  257,  258,  257,  258,
};
}
final static short YYFINAL=6;
final static short YYMAXTOKEN=259;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",
null,"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
null,"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'|'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"NUM","WORD","NEG",
};
final static String yyrule[] = {
"$accept : input",
"input : advcondition",
"expression : NUM",
"expression : WORD",
"expression : expression '+' expression",
"expression : expression '-' expression",
"expression : expression '*' expression",
"expression : '-' expression",
"expression : '(' expression ')'",
"advcondition : condition",
"advcondition : advcondition '&' '&' advcondition",
"advcondition : advcondition '|' '|' advcondition",
"advcondition : '(' advcondition ')'",
"advcondition : '!' advcondition",
"condition : expression '=' '=' expression",
"condition : expression '!' '=' expression",
"condition : expression '<' '=' expression",
"condition : expression '>' '=' expression",
"condition : expression '<' expression",
"condition : expression '>' expression",
};

//#line 121 "src/parsers/AdvConditionParser.y"


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
//#line 426 "AdvConditionParser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 36 "src/parsers/AdvConditionParser.y"
{
	topAdvCondition=(AdvCondition)val_peek(0).obj;
	System.out.println("AdvConditionParser work completed. "); 
}
break;
case 2:
//#line 42 "src/parsers/AdvConditionParser.y"
{Expression e=new Expression(val_peek(0).ival);yyval=new AdvConditionParserVal(e);}
break;
case 3:
//#line 43 "src/parsers/AdvConditionParser.y"
{Expression e=new Expression(new Variable(getStringValue(val_peek(0))));yyval=new AdvConditionParserVal(e);}
break;
case 4:
//#line 45 "src/parsers/AdvConditionParser.y"
{
 	Expression e=new Expression((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ExpType.plus);
 	yyval=new AdvConditionParserVal(e);
 }
break;
case 5:
//#line 50 "src/parsers/AdvConditionParser.y"
{
 	Expression e=new Expression((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ExpType.minus);
 	yyval=new AdvConditionParserVal(e);
 }
break;
case 6:
//#line 55 "src/parsers/AdvConditionParser.y"
{
 	Expression e=new Expression((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ExpType.multiply);
 	yyval=new AdvConditionParserVal(e);
 }
break;
case 7:
//#line 61 "src/parsers/AdvConditionParser.y"
{
 	Expression e=new Expression((Expression)val_peek(0).obj,null,ExpType.negative);
 	yyval=new AdvConditionParserVal(e);
 }
break;
case 8:
//#line 67 "src/parsers/AdvConditionParser.y"
{
 	yyval=val_peek(1);
 }
break;
case 9:
//#line 72 "src/parsers/AdvConditionParser.y"
{yyval=new AdvConditionParserVal(new AdvCondition((Condition)val_peek(0).obj));}
break;
case 10:
//#line 74 "src/parsers/AdvConditionParser.y"
{
 yyval=new AdvConditionParserVal(new AdvCondition((AdvCondition)val_peek(3).obj,(AdvCondition)val_peek(0).obj,AdvCondition.Type_AND)); 
}
break;
case 11:
//#line 79 "src/parsers/AdvConditionParser.y"
{
 yyval=new AdvConditionParserVal(new AdvCondition((AdvCondition)val_peek(3).obj,(AdvCondition)val_peek(0).obj,AdvCondition.Type_OR));
}
break;
case 12:
//#line 84 "src/parsers/AdvConditionParser.y"
{yyval=val_peek(1);}
break;
case 13:
//#line 88 "src/parsers/AdvConditionParser.y"
{yyval=val_peek(0);((AdvCondition)yyval.obj).negateSelf();}
break;
case 14:
//#line 91 "src/parsers/AdvConditionParser.y"
{
 	Condition c=new Condition((Expression)val_peek(3).obj,(Expression)val_peek(0).obj,ConType.equal);
 	yyval=new AdvConditionParserVal(c);
 }
break;
case 15:
//#line 96 "src/parsers/AdvConditionParser.y"
{
 	Condition c=new Condition((Expression)val_peek(3).obj,(Expression)val_peek(0).obj,ConType.notequal);
 	yyval=new AdvConditionParserVal(c);
 }
break;
case 16:
//#line 101 "src/parsers/AdvConditionParser.y"
{
 	Condition c=new Condition((Expression)val_peek(3).obj,(Expression)val_peek(0).obj,ConType.equalsmaller);
 	yyval=new AdvConditionParserVal(c);
 }
break;
case 17:
//#line 106 "src/parsers/AdvConditionParser.y"
{
 	Condition c=new Condition((Expression)val_peek(3).obj,(Expression)val_peek(0).obj,ConType.equallarger);
 	yyval=new AdvConditionParserVal(c);
 }
break;
case 18:
//#line 111 "src/parsers/AdvConditionParser.y"
{
 	Condition c=new Condition((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ConType.smaller);
 	yyval=new AdvConditionParserVal(c);
 }
break;
case 19:
//#line 116 "src/parsers/AdvConditionParser.y"
{
 	Condition c=new Condition((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ConType.larger);
 	yyval=new AdvConditionParserVal(c);
 }
break;
//#line 689 "AdvConditionParser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public AdvConditionParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public AdvConditionParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
