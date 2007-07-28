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



package prover;



//#line 1 "FociParser.y"

/*package prover;*/

import java.lang.*;
/*import java.lang.Math;*/
import java.io.*;
import java.util.*;

/*load self-defined token-library*/
/*import tokens.Sentence;*/
import tokens.*;
import abstraction.*;

//#line 31 "FociParser.java"




public class FociParser
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
//public class FociParserVal is defined in FociParserVal.java


String   yytext;//user variable to return contextual strings
FociParserVal yyval; //used to return semantic vals from action routines
FociParserVal yylval;//the 'lval' (result) I got from yylex()
FociParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new FociParserVal[YYSTACKSIZE];
  yyval=new FociParserVal();
  yylval=new FociParserVal();
  valptr=-1;
}
void val_push(FociParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
FociParserVal val_pop()
{
  if (valptr<0)
    return new FociParserVal();
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
FociParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new FociParserVal();
  return valstk[ptr];
}
//#### end semantic value section ####
public final static short NUM=257;
public final static short WORD=258;
public final static short NEG=259;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    2,    2,    2,    4,
    4,    3,    3,    3,    3,    3,    5,    5,
};
final static short yylen[] = {                            2,
    1,    0,    2,    3,    4,    4,    4,    2,    3,    2,
    2,    1,    1,    4,    3,    3,    2,    2,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    0,    0,    0,    3,    0,
    0,    8,    0,   13,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    4,    0,    9,   10,    7,
   11,    6,   15,    0,    0,   16,    5,   17,   14,   18,
};
final static short yydgoto[] = {                          1,
    2,   20,   17,   21,   35,
};
final static short yysindex[] = {                         0,
    0,  -26,  -84,  -83,  -26,  -38,  -46,  -26,    0,  -26,
  -26,    0,  -25,    0,  -73,  -38,  -38,  -38,  -22,  -26,
  -34,  -29,  -38,  -38,  -21,    0,  -38,    0,    0,    0,
    0,    0,    0,  -38,  -40,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,   21,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    8,   31,   11,    0,
};
final static int YYTABLESIZE=259;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
   12,   16,   15,    4,   15,    8,   10,   11,    4,    9,
    8,    4,   12,    8,   18,   19,   23,   24,   28,   36,
    1,   22,    0,    0,    0,    7,    6,   29,   31,   31,
    7,    6,    0,    7,    6,    0,    0,    0,   12,    0,
   12,   12,    0,   12,    0,    0,   25,   26,   27,    0,
    0,    0,   39,   33,   34,    0,    0,   37,   30,    0,
   12,   12,    0,   32,   38,   40,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    0,    5,    0,   12,    3,    0,    5,    3,    0,    5,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   12,    0,   12,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   13,   14,   13,   14,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   12,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   43,   38,   43,   40,   91,   91,   38,    2,
   40,   38,    5,   40,   61,    8,   42,   91,   41,   41,
    0,   11,   -1,   -1,   -1,   60,   61,   20,   21,   22,
   60,   61,   -1,   60,   61,   -1,   -1,   -1,   38,   -1,
   40,   41,   -1,   43,   -1,   -1,   16,   17,   18,   -1,
   -1,   -1,   93,   23,   24,   -1,   -1,   27,   93,   -1,
   60,   61,   -1,   93,   34,   35,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  124,
   -1,  126,   -1,   93,  124,   -1,  126,  124,   -1,  126,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  124,   -1,  126,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  257,  258,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=259;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'<'","'='",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'|'",null,"'~'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"NUM","WORD","NEG",
};
final static String yyrule[] = {
"$accept : input",
"input : formulas",
"formulas :",
"formulas : formulas formula",
"formula : '=' term term",
"formula : '<' '=' term term",
"formula : '&' '[' formulaarray ']'",
"formula : '|' '[' formulaarray ']'",
"formula : '~' formula",
"formula : '(' formula ')'",
"formulaarray : formula formula",
"formulaarray : formulaarray formula",
"term : NUM",
"term : WORD",
"term : '+' '[' termarray ']'",
"term : NUM '*' term",
"term : '(' term ')'",
"termarray : term term",
"termarray : termarray term",
};

//#line 174 "FociParser.y"

boolean loaded=false;
ArrayList<AdvCondition> conditionPool;

StreamTokenizer st;

boolean dflag;
boolean debugging=true;
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
	//yylval=new FociParserVal(yytext);
	yylval=new FociParserVal((Object)yytext);
	//pout("yytext: "+yytext);
	if(debugging)pout("TT_WORD recognized: "+(String)yylval.obj);
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

int parseFile(File file)
{
  initialize();
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
  FociParser par = new FociParser(false);
  par.parseFile(new File(args[0]));
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
//#line 403 "FociParser.java"
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
//#line 40 "FociParser.y"
{
	ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)val_peek(0).obj;
	System.out.println("Work completed.\nThe grammar of the foci output file is correct.\n In all, "+formulaNo+" predicates found.");
	for (AdvCondition s: al)
	{
		System.out.println(s.toString());
	}
	/*predicatePool=al;*/
}
break;
case 2:
//#line 51 "FociParser.y"
{ArrayList<AdvCondition> al=new ArrayList<AdvCondition>();yyval=new FociParserVal(al);}
break;
case 3:
//#line 53 "FociParser.y"
{
 	ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)val_peek(1).obj;
 	if(val_peek(0)!=null)
 		al.add((AdvCondition)val_peek(0).obj);
 	yyval=new FociParserVal(al);
	/*if(debugging)System.out.println("formula inserted: "+((AdvCondition)$2.obj).toString());*/
	formulaNo++;
 	/*warning $1 might have changed!*/
 }
break;
case 4:
//#line 65 "FociParser.y"
{
	 Condition c=new Condition((Expression)val_peek(1).obj,(Expression)val_peek(0).obj,ConType.equal);
	 yyval=new FociParserVal(new AdvCondition(c));
	 /*if(debugging)System.out.println("formula got: "+c.toString());*/
 }
break;
case 5:
//#line 71 "FociParser.y"
{
	 Condition c=new Condition((Expression)val_peek(1).obj,(Expression)val_peek(0).obj,ConType.equalsmaller);
	 yyval=new FociParserVal(new AdvCondition(c));
	 
 }
break;
case 6:
//#line 77 "FociParser.y"
{
	 ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)val_peek(1).obj;
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
	 yyval=new FociParserVal(latestCon);
 }
break;
case 7:
//#line 94 "FociParser.y"
{
	 ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)val_peek(1).obj;
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
	 yyval=new FociParserVal(latestCon);
 }
break;
case 8:
//#line 110 "FociParser.y"
{AdvCondition c=(AdvCondition)val_peek(0).obj;c.negateSelf();yyval=new FociParserVal(c);}
break;
case 9:
//#line 111 "FociParser.y"
{yyval=val_peek(1);}
break;
case 10:
//#line 115 "FociParser.y"
{
	 ArrayList<AdvCondition> al=new ArrayList<AdvCondition>();
	 al.add((AdvCondition)val_peek(1).obj);
 	 al.add((AdvCondition)val_peek(0).obj);
	 yyval=new FociParserVal(al);
 }
break;
case 11:
//#line 122 "FociParser.y"
{
	 ArrayList<AdvCondition> al=(ArrayList<AdvCondition>)val_peek(1).obj;
	 al.add((AdvCondition)val_peek(0).obj);	
	 yyval=new FociParserVal(al);
 }
break;
case 12:
//#line 129 "FociParser.y"
{Expression e=new Expression(val_peek(0).ival);yyval=new FociParserVal(e);}
break;
case 13:
//#line 131 "FociParser.y"
{
	Expression e=new Expression(new Variable(getStringValue(val_peek(0))));yyval=new FociParserVal(e);
	if(debugging)System.out.println("term is word: "+e.toString());
 }
break;
case 14:
//#line 136 "FociParser.y"
{
	 ArrayList<Expression> al=(ArrayList<Expression>)val_peek(1).obj;
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
			 latestExp=new Expression(latestExp,e,ExpType.plus);
	 }
	 yyval=new FociParserVal(latestExp);
 }
break;
case 15:
//#line 153 "FociParser.y"
{
	 Expression e=new Expression(new Expression(val_peek(2).ival),(Expression)val_peek(0).obj,ExpType.multiply);
	 yyval=new FociParserVal(e);
 }
break;
case 16:
//#line 157 "FociParser.y"
{yyval=val_peek(1);}
break;
case 17:
//#line 161 "FociParser.y"
{
	 ArrayList<Expression> al=new ArrayList<Expression>();
	 al.add((Expression)val_peek(1).obj);
 	 al.add((Expression)val_peek(0).obj);
	 yyval=new FociParserVal(al);
 }
break;
case 18:
//#line 168 "FociParser.y"
{
	 ArrayList<Expression> al=(ArrayList<Expression>)val_peek(1).obj;
	 al.add((Expression)val_peek(0).obj);	
	 yyval=new FociParserVal(al);
 }
break;
//#line 716 "FociParser.java"
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
public FociParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public FociParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
