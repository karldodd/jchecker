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



//#line 2 "parsers/jchecker2.y"
import java.lang.*;
/*import java.lang.Math;*/
import java.io.*;
import java.util.*;

/*load self-defined token-library*/
/*import tokens.Sentence;*/
import tokens.*;

//#line 27 "SourceParser.java"


/**
*对Java源程序进行解析
*@author He Kaiduo
*/
public class SourceParser
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
//public class SourceParserVal is defined in SourceParserVal.java


String   yytext;//user variable to return contextual strings
SourceParserVal yyval; //used to return semantic vals from action routines
SourceParserVal yylval;//the 'lval' (result) I got from yylex()
SourceParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new SourceParserVal[YYSTACKSIZE];
  yyval=new SourceParserVal();
  yylval=new SourceParserVal();
  valptr=-1;
}
void val_push(SourceParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
SourceParserVal val_pop()
{
  if (valptr<0)
    return new SourceParserVal();
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
SourceParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new SourceParserVal();
  return valstk[ptr];
}
//#### end semantic value section ####
public final static short NUM=257;
public final static short WORD=258;
public final static short NEG=259;
public final static short DCL_INT=260;
public final static short DCL_BOOL=261;
public final static short IF=262;
public final static short ELSE=263;
public final static short WHILE=264;
public final static short RETURN=265;
public final static short ERROR=266;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    2,    2,    6,    6,
    7,    4,    4,    3,    5,    5,   11,   11,    8,    8,
    8,    8,    8,    8,    8,   12,   12,   12,   12,   12,
    9,    9,    9,    9,   10,   10,   10,   13,   13,   13,
   13,   13,   13,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,    1,    1,    2,    3,
    2,    1,    1,    4,    3,    3,    1,    3,    1,    1,
    3,    3,    3,    2,    3,    1,    4,    4,    3,    2,
    5,    5,    7,   11,    5,    5,    7,    4,    4,    4,
    4,    3,    3,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    4,    5,    6,    7,    8,   12,   13,    0,   17,    0,
    0,    0,    0,   19,   20,    0,    9,    0,    0,   11,
    0,   15,    0,   16,    0,    0,    0,    0,   26,    0,
   24,    0,    0,    0,    0,   10,   14,   18,   30,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   25,
    0,    0,   23,   29,    0,    0,    0,    0,    0,    0,
    0,    0,    2,   31,   32,    2,   35,   36,    0,    0,
    0,    0,   28,   27,    0,    0,    0,   37,    0,    2,
    0,   34,
};
final static short yydgoto[] = {                          1,
    2,   10,   11,   12,   13,   14,   15,   37,   16,   17,
   20,   38,   39,
};
final static short yysindex[] = {                         0,
    0,  -84,  -55, -233, -233,  -13,  -11,  -37,  -20,    0,
    0,    0,    0,    0,    0,    0,    0,  -29,    0,  -40,
   25,  -33,  -33,    0,    0,  -29,    0,  -29,   43,    0,
   47,    0, -206,    0,  -33,  -33,   38,  -23,    0,  -21,
    0,   69,  -29,  -29,  -29,    0,    0,    0,    0,   34,
  -17,    7,    9,  -35,  -31,  -50,   44,  -92,  -91,    0,
   36,   36,    0,    0,  -29,  -29,  -29,   75,  -29,   75,
  -33,  -33,    0,    0,    0,    0,    0,    0,   75,   75,
   75,   75,    0,    0, -123, -112, -176,    0,  -32,    0,
 -102,    0,
};
final static short yyrindex[] = {                         0,
    0,   93,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   -5,    4,    0,    0,    0,    0,    0,    3,    0,    5,
    0,    0,    0,    0,    0,    0,    0,    0,   10,   12,
   20,   21,    0,    0,    0,    0,    1,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
   40,    0,  -24,   46,    0,    0,    0,  228,    0,    0,
   92,   37,    0,
};
final static int YYTABLESIZE=297;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         35,
   33,   87,   28,   33,   28,   18,   36,   26,   28,   26,
   28,   26,   88,   26,   57,   26,   57,   58,   32,   59,
   57,   27,   92,   64,   19,   67,   22,   22,   23,   69,
   73,   76,   22,   74,   77,   22,   21,   22,   30,   22,
   42,   21,   43,   42,   21,   43,   21,   39,   21,   38,
   39,   48,   38,   22,   22,   22,   22,   40,   41,   40,
   40,   41,   21,   21,   21,   21,   52,   65,   33,   66,
   52,   49,   51,   71,   60,   45,   44,   45,   43,   45,
   44,   72,   43,   34,   45,   44,   89,   43,   45,   44,
   90,   43,    1,   54,   53,   55,   21,   54,   53,   55,
   56,   46,   56,   75,   78,   47,   56,   83,   84,   60,
   45,   44,   85,   43,    0,   86,   45,   44,   22,   43,
    0,    0,    0,    0,    0,   33,   42,   21,   43,   91,
    0,    0,    0,   39,    3,   38,    4,    5,    6,    0,
    7,    8,    9,   40,   41,    3,    0,    4,    5,    6,
    0,    7,    8,    9,    0,    3,    0,    4,    5,    6,
    0,    7,    8,    9,    0,    3,    3,    0,    0,    6,
    6,    7,    7,    3,    0,    4,    5,    6,    0,    7,
    8,    9,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   24,
   25,   24,   25,   24,   25,   24,   25,   24,   25,    0,
    0,    0,    0,    0,    0,   29,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   31,    0,    0,    0,    0,
    0,    0,    0,   41,    0,   42,    0,    0,   33,    0,
   33,   33,   33,   50,   33,   33,   33,    0,    0,    0,
   61,   62,   63,    0,    0,    0,    0,    0,    0,    0,
    0,   68,   70,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   79,   80,   81,    0,   82,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
    0,  125,   40,   44,   40,   61,   40,   45,   40,   45,
   40,   45,  125,   45,   38,   45,   38,   41,   59,   41,
   38,   59,  125,   41,  258,   61,   40,   33,   40,   61,
  123,  123,   38,   58,   59,   41,   33,   43,   59,   45,
   38,   38,   38,   41,   41,   41,   43,   38,   45,   38,
   41,  258,   41,   59,   60,   61,   62,   38,   38,   23,
   41,   41,   59,   60,   61,   62,   33,   61,   44,   61,
   33,   35,   36,  124,   41,   42,   43,   42,   45,   42,
   43,   38,   45,   59,   42,   43,  263,   45,   42,   43,
  123,   45,    0,   60,   61,   62,    5,   60,   61,   62,
  124,   59,  124,   58,   59,   59,  124,   71,   72,   41,
   42,   43,   73,   45,   -1,   76,   42,   43,  124,   45,
   -1,   -1,   -1,   -1,   -1,  125,  124,  124,  124,   90,
   -1,   -1,   -1,  124,  258,  124,  260,  261,  262,   -1,
  264,  265,  266,  124,  124,  258,   -1,  260,  261,  262,
   -1,  264,  265,  266,   -1,  258,   -1,  260,  261,  262,
   -1,  264,  265,  266,   -1,  258,  258,   -1,   -1,  262,
  262,  264,  264,  258,   -1,  260,  261,  262,   -1,  264,
  265,  266,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  257,  258,  257,  258,  257,  258,  257,  258,   -1,
   -1,   -1,   -1,   -1,   -1,    8,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   18,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   26,   -1,   28,   -1,   -1,  258,   -1,
  260,  261,  262,   36,  264,  265,  266,   -1,   -1,   -1,
   43,   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   54,   55,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   65,   66,   67,   -1,   69,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=266;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'","'|'","'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"NUM","WORD","NEG","DCL_INT",
"DCL_BOOL","IF","ELSE","WHILE","RETURN","ERROR",
};
final static String yyrule[] = {
"$accept : input",
"input : sentences",
"sentences :",
"sentences : sentences sentence",
"sentence : evaluationsentence",
"sentence : decisionsentence",
"sentence : declarationsentence",
"sentence : returnsentence",
"sentence : errorsentence",
"returnsentence : RETURN ';'",
"returnsentence : RETURN expression ';'",
"errorsentence : ERROR ';'",
"decisionsentence : ifsentence",
"decisionsentence : whilesentence",
"evaluationsentence : WORD '=' expression ';'",
"declarationsentence : DCL_INT words ';'",
"declarationsentence : DCL_BOOL words ';'",
"words : WORD",
"words : words ',' WORD",
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
"ifsentence : IF '(' advcondition ')' evaluationsentence",
"ifsentence : IF '(' advcondition ')' decisionsentence",
"ifsentence : IF '(' advcondition ')' '{' sentences '}'",
"ifsentence : IF '(' advcondition ')' '{' sentences '}' ELSE '{' sentences '}'",
"whilesentence : WHILE '(' advcondition ')' evaluationsentence",
"whilesentence : WHILE '(' advcondition ')' decisionsentence",
"whilesentence : WHILE '(' advcondition ')' '{' sentences '}'",
"condition : expression '=' '=' expression",
"condition : expression '!' '=' expression",
"condition : expression '<' '=' expression",
"condition : expression '>' '=' expression",
"condition : expression '<' expression",
"condition : expression '>' expression",
};

//#line 278 "parsers/jchecker2.y"
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
  
  //pout("yytext:"+yytext+"  char"+(char)tok+" nval: "+st.nval);
  
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
//#line 466 "SourceParser.java"
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
//#line 36 "parsers/jchecker2.y"
{ArrayList<Sentence> al=(ArrayList<Sentence>)val_peek(0).obj;
System.out.println("Work completed.\nThe grammar of the file is correct.\n   "+sentenceNo+ " sentence(s) caught in all.  "+al.size());
	for (Sentence s: al)
	{
		System.out.println(s.toString());
	}
	sentencePool=al;
	System.out.println("Sentence pool is ready.");
}
break;
case 2:
//#line 47 "parsers/jchecker2.y"
{ArrayList<Sentence> al=new ArrayList<Sentence>();yyval=new SourceParserVal(al);}
break;
case 3:
//#line 49 "parsers/jchecker2.y"
{
 	ArrayList<Sentence> al=(ArrayList<Sentence>)val_peek(1).obj;
 	if(val_peek(0)!=null)
 		al.add((Sentence)val_peek(0).obj);
 	yyval=new SourceParserVal(al);
 	/*warning $1 might have changed!*/
 }
break;
case 4:
//#line 58 "parsers/jchecker2.y"
{sentenceNo++;}
break;
case 6:
//#line 60 "parsers/jchecker2.y"
{sentenceNo++;}
break;
case 7:
//#line 61 "parsers/jchecker2.y"
{sentenceNo++;}
break;
case 8:
//#line 62 "parsers/jchecker2.y"
{sentenceNo++;}
break;
case 9:
//#line 65 "parsers/jchecker2.y"
{yyval=new SourceParserVal(new ReturnSentence(null));}
break;
case 10:
//#line 66 "parsers/jchecker2.y"
{yyval=new SourceParserVal(new ReturnSentence((Expression)val_peek(1).obj));}
break;
case 11:
//#line 69 "parsers/jchecker2.y"
{yyval=new SourceParserVal(new ErrorSentence());}
break;
case 14:
//#line 77 "parsers/jchecker2.y"
{
 	Variable v=new Variable(getStringValue(val_peek(3)));
 	Expression e=(Expression)val_peek(1).obj;
 	yyval=new SourceParserVal(new EvaluationSentence(v,e));
 }
break;
case 15:
//#line 86 "parsers/jchecker2.y"
{yyval=null;}
break;
case 16:
//#line 87 "parsers/jchecker2.y"
{yyval=null;}
break;
case 17:
//#line 90 "parsers/jchecker2.y"
{}
break;
case 18:
//#line 91 "parsers/jchecker2.y"
{}
break;
case 19:
//#line 94 "parsers/jchecker2.y"
{Expression e=new Expression(val_peek(0).ival);yyval=new SourceParserVal(e);}
break;
case 20:
//#line 95 "parsers/jchecker2.y"
{Expression e=new Expression(new Variable(getStringValue(val_peek(0))));yyval=new SourceParserVal(e);}
break;
case 21:
//#line 97 "parsers/jchecker2.y"
{
 	Expression e=new Expression((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ExpType.plus);
 	yyval=new SourceParserVal(e);
 }
break;
case 22:
//#line 102 "parsers/jchecker2.y"
{
 	Expression e=new Expression((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ExpType.minus);
 	yyval=new SourceParserVal(e);
 }
break;
case 23:
//#line 107 "parsers/jchecker2.y"
{
 	Expression e=new Expression((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ExpType.multiply);
 	yyval=new SourceParserVal(e);
 }
break;
case 24:
//#line 113 "parsers/jchecker2.y"
{
 	Expression e=new Expression((Expression)val_peek(0).obj,null,ExpType.negative);
 	yyval=new SourceParserVal(e);
 }
break;
case 25:
//#line 119 "parsers/jchecker2.y"
{
 	yyval=val_peek(1);
 }
break;
case 26:
//#line 124 "parsers/jchecker2.y"
{yyval=new SourceParserVal(new AdvCondition((Condition)val_peek(0).obj));}
break;
case 27:
//#line 157 "parsers/jchecker2.y"
{
 yyval=new SourceParserVal(new AdvCondition((AdvCondition)val_peek(3).obj,(AdvCondition)val_peek(0).obj,AdvCondition.Type_AND)); 
}
break;
case 28:
//#line 162 "parsers/jchecker2.y"
{
 yyval=new SourceParserVal(new AdvCondition((AdvCondition)val_peek(3).obj,(AdvCondition)val_peek(0).obj,AdvCondition.Type_OR));
}
break;
case 29:
//#line 167 "parsers/jchecker2.y"
{yyval=val_peek(1);}
break;
case 30:
//#line 171 "parsers/jchecker2.y"
{yyval=val_peek(0);((AdvCondition)yyval.obj).negateSelf();}
break;
case 31:
//#line 175 "parsers/jchecker2.y"
{
	AdvCondition c=(AdvCondition)val_peek(2).obj;
	EvaluationSentence es=(EvaluationSentence)val_peek(0).obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.add(es);
	yyval=new SourceParserVal(ds);
}
break;
case 32:
//#line 183 "parsers/jchecker2.y"
{
	AdvCondition c=(AdvCondition)val_peek(2).obj;
	DecisionSentence old=(DecisionSentence)val_peek(0).obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.add(old);
	yyval=new SourceParserVal(ds);
}
break;
case 33:
//#line 191 "parsers/jchecker2.y"
{
	AdvCondition c=(AdvCondition)val_peek(4).obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)val_peek(1).obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.addAll(al);
	yyval=new SourceParserVal(ds);
}
break;
case 34:
//#line 199 "parsers/jchecker2.y"
{
	AdvCondition c=(AdvCondition)val_peek(8).obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)val_peek(5).obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.ifsentence);
	ds.addAll(al);
	ArrayList<Sentence> al2=(ArrayList<Sentence>)val_peek(1).obj;
	ds.addAllForElse(al2);
	yyval=new SourceParserVal(ds);
}
break;
case 35:
//#line 211 "parsers/jchecker2.y"
{
	AdvCondition c=(AdvCondition)val_peek(2).obj;
	EvaluationSentence es=(EvaluationSentence)val_peek(0).obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.add(es);
	yyval=new SourceParserVal(ds);
}
break;
case 36:
//#line 219 "parsers/jchecker2.y"
{
	AdvCondition c=(AdvCondition)val_peek(2).obj;
	DecisionSentence old=(DecisionSentence)val_peek(0).obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.add(old);
	yyval=new SourceParserVal(ds);
}
break;
case 37:
//#line 227 "parsers/jchecker2.y"
{
	AdvCondition c=(AdvCondition)val_peek(4).obj;
	ArrayList<Sentence> al=(ArrayList<Sentence>)val_peek(1).obj;
	DecisionSentence ds=new DecisionSentence(c,DecisionType.whilesentence);
	ds.addAll(al);
	yyval=new SourceParserVal(ds);
}
break;
case 38:
//#line 237 "parsers/jchecker2.y"
{
 	Condition c=new Condition((Expression)val_peek(3).obj,(Expression)val_peek(0).obj,ConType.equal);
 	yyval=new SourceParserVal(c);
 }
break;
case 39:
//#line 242 "parsers/jchecker2.y"
{
 	Condition c=new Condition((Expression)val_peek(3).obj,(Expression)val_peek(0).obj,ConType.notequal);
 	yyval=new SourceParserVal(c);
 }
break;
case 40:
//#line 247 "parsers/jchecker2.y"
{
 	Condition c=new Condition((Expression)val_peek(3).obj,(Expression)val_peek(0).obj,ConType.equalsmaller);
 	yyval=new SourceParserVal(c);
 }
break;
case 41:
//#line 252 "parsers/jchecker2.y"
{
 	Condition c=new Condition((Expression)val_peek(3).obj,(Expression)val_peek(0).obj,ConType.equallarger);
 	yyval=new SourceParserVal(c);
 }
break;
case 42:
//#line 257 "parsers/jchecker2.y"
{
 	Condition c=new Condition((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ConType.smaller);
 	yyval=new SourceParserVal(c);
 }
break;
case 43:
//#line 262 "parsers/jchecker2.y"
{
 	Condition c=new Condition((Expression)val_peek(2).obj,(Expression)val_peek(0).obj,ConType.larger);
 	yyval=new SourceParserVal(c);
 }
break;
//#line 872 "SourceParser.java"
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
public SourceParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public SourceParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
