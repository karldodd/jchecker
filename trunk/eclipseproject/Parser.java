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






//#line 2 "jchecker.y"
import java.lang.*;
/*import java.lang.Math;*/
import java.io.*;
import java.util.StringTokenizer;
//#line 22 "Parser.java"




public class Parser
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
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
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
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
//#### end semantic value section ####
public final static short NUM=257;
public final static short CHAR=258;
public final static short NEG=259;
public final static short DCL_INT=260;
public final static short DCL_BOOL=261;
public final static short IF=262;
public final static short WHILE=263;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    2,    2,    2,    3,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    4,    4,    6,    6,
   10,   10,    5,    5,   11,   11,   11,   11,   11,    8,
    8,    8,    9,    9,    9,
};
final static short yylen[] = {                            2,
    0,    1,    2,    1,    1,    1,    4,    1,    1,    3,
    3,    3,    3,    2,    3,    3,    1,    1,    1,    2,
    1,    3,    3,    3,    4,    4,    4,    3,    3,    5,
    5,    7,    5,    5,    7,
};
final static short yydefred[] = {                         1,
    0,    0,   19,    0,    0,    0,    0,    3,    4,    5,
    6,    0,   17,   18,    0,    0,    0,    0,    0,   20,
    0,   23,    0,   24,    8,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    7,   16,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   30,   31,
    0,   33,   34,    0,    0,    0,    0,    0,   32,   35,
};
final static short yydgoto[] = {                          1,
    2,    8,    9,   10,   11,   28,   29,   13,   14,   16,
   30,
};
final static short yysindex[] = {                         0,
    0, -173,    0, -240, -240,  -18,  -13,    0,    0,    0,
    0,  -60,    0,    0, -222,   -3,    3,  -36,  -36,    0,
  -36,    0, -240,    0,    0,  -36,  -36, -222,   10,   -1,
    5,   16, -222,   -8,  -10,  -36,  -36,  -36,  -36,  -36,
  -23,  -40,  -38, -120, -117,    0,    0,   -8,   -8,  -34,
  -34,  -34,  -36,  -36,  -30,  -36,  -30,    0,    0,    0,
    0,    0,    0,  -30,  -30,  -30, -111, -105,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,   54,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   21,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -17,    0,    0,
    0,    0,   24,   71,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  130,  152,   58,
  118,  125,    0,    0,   28,    0,   37,    0,    0,    0,
    0,    0,    0,   38,   61,   65,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -42,    0,    4,   22,    0,    6,   55,    0,    0,   91,
   88,
};
final static int YYTABLESIZE=222;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
   21,   27,   58,   27,   26,   61,   26,   12,   26,   15,
   15,   38,   37,   69,   36,   67,   39,    3,   68,   70,
   54,   18,   56,    9,    9,    9,   19,    9,   33,    9,
   47,   38,   37,   38,   36,   20,   39,   53,   39,   44,
   23,    9,    9,    9,    9,   45,   23,   59,   62,   12,
   12,   38,   37,    2,   36,   22,   39,   38,   37,   40,
   36,   24,   39,   40,   21,   60,   63,   22,   28,   42,
   41,   43,   12,   12,   46,   32,    9,   29,   25,   21,
   34,   35,   22,   40,    3,   40,    4,    5,    6,    7,
   48,   49,   50,   51,   52,   17,   55,   57,   12,   12,
   12,   26,   12,   40,   12,   27,   31,   64,   65,   40,
   66,   14,    0,   14,    0,   14,   12,   12,   12,   12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   14,
   14,   14,   14,    0,    0,    0,    0,    3,    0,    0,
    3,    6,    7,    0,    6,    7,    3,    0,    4,    5,
    6,    7,    3,    0,    4,    5,    6,    7,   13,   13,
   13,    0,   13,    0,   13,   15,   15,   15,    0,   15,
   11,   15,   11,    0,   11,    0,   13,   13,   13,   13,
    0,    0,    0,   15,   15,   15,   15,    0,   11,   11,
   11,   11,   10,    0,   10,    0,   10,   20,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,   10,   10,   10,    0,    0,   25,    3,   25,    3,
   25,    3,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   61,   40,  123,   40,   45,  123,   45,    2,   45,    4,
    5,   42,   43,  125,   45,   58,   47,  258,   61,  125,
   61,   40,   61,   41,   42,   43,   40,   45,   23,   47,
   41,   42,   43,   42,   45,  258,   47,   61,   47,   41,
   44,   59,   60,   61,   62,   41,   44,   44,   45,   44,
   45,   42,   43,    0,   45,   59,   47,   42,   43,   94,
   45,   59,   47,   94,   44,   44,   45,   44,   41,   60,
   61,   62,   67,   68,   59,   21,   94,   41,   41,   59,
   26,   27,   59,   94,  258,   94,  260,  261,  262,  263,
   36,   37,   38,   39,   40,    5,   42,   43,   41,   42,
   43,   41,   45,   94,   47,   41,   19,   53,   54,   94,
   56,   41,   -1,   43,   -1,   45,   59,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   60,   61,   62,   -1,   -1,   -1,   -1,  258,   -1,   -1,
  258,  262,  263,   -1,  262,  263,  258,   -1,  260,  261,
  262,  263,  258,   -1,  260,  261,  262,  263,   41,   42,
   43,   -1,   45,   -1,   47,   41,   42,   43,   -1,   45,
   41,   47,   43,   -1,   45,   -1,   59,   60,   61,   62,
   -1,   -1,   -1,   59,   60,   61,   62,   -1,   59,   60,
   61,   62,   41,   -1,   43,   -1,   45,  258,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   60,   61,   62,   -1,   -1,  257,  258,  257,  258,
  257,  258,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=263;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"NUM","CHAR","NEG","DCL_INT","DCL_BOOL",
"IF","WHILE",
};
final static String yyrule[] = {
"$accept : input",
"input :",
"input : block",
"block : block sentence",
"sentence : evaluationsentence",
"sentence : decisionsentence",
"sentence : declarationsentence",
"evaluationsentence : word '=' expression ';'",
"expression : NUM",
"expression : word",
"expression : expression '+' expression",
"expression : expression '-' expression",
"expression : expression '*' expression",
"expression : expression '/' expression",
"expression : '-' expression",
"expression : expression '^' expression",
"expression : '(' expression ')'",
"decisionsentence : ifsentence",
"decisionsentence : whilesentence",
"word : CHAR",
"word : word CHAR",
"words : word",
"words : words ',' word",
"declarationsentence : DCL_INT words ';'",
"declarationsentence : DCL_BOOL words ';'",
"condition : expression '=' '=' expression",
"condition : expression '<' '=' expression",
"condition : expression '>' '=' expression",
"condition : expression '<' expression",
"condition : expression '>' expression",
"ifsentence : IF '(' condition ')' evaluationsentence",
"ifsentence : IF '(' condition ')' decisionsentence",
"ifsentence : IF '(' condition ')' '{' block '}'",
"whilesentence : WHILE '(' condition ')' evaluationsentence",
"whilesentence : WHILE '(' condition ')' decisionsentence",
"whilesentence : WHILE '(' condition ')' '{' block '}'",
};

//#line 100 "jchecker.y"

StreamTokenizer st;
boolean dflag;

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

  pout("yy:"+yytext);
  if  (tok==st.TT_NUMBER)
  {
    yylval = new ParserVal((int)st.nval);
    return NUM;
  }
  else if  (tok==st.TT_WORD)
  {
    if (yytext.equals("if"))
      return IF;
    else if (yytext.equals("while"))
      return WHILE;
    else if (yytext.equals("int"))
      return DCL_INT;
    else if (yytext.equals("bool"))
      return DCL_BOOL;
    //else return word;
    else
      return yytext.charAt(0);
  }
  return tok;
}

int doTest(File file)
{
  FileReader fr;
  Reader r;
  int ret;
  dflag=true;
  try
  {
    fr=new FileReader(file);
    r = new BufferedReader(fr);
    st = new StreamTokenizer(r); 
    st.slashSlashComments(true);
    st.slashStarComments(true);
  }
  catch (Exception e)
  {
    yyerror("could not open "+file.getName());
    return 0;
  }
  ret=yyparse();
  try
  {
    r.close();
    fr.close();
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
  int a=par.doTest(new File("source.c"));
}

//#line 361 "Parser.java"
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
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
