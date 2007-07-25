#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 2 "jchecker2.y"
import java.lang.*;
/*import java.lang.Math;*/
import java.io.*;
import java.util.StringTokenizer;
#line 11 "y.tab.c"
#define NUM 257
#define WORD 258
#define NEG 259
#define DCL_INT 260
#define DCL_BOOL 261
#define IF 262
#define WHILE 263
#define RETURN 264
#define ERROR 265
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    1,    1,    2,    2,    2,    2,    2,    6,    6,
    7,    4,    4,    3,    5,    5,   11,   11,    8,    8,
    8,    8,    8,    8,    8,    9,    9,    9,   10,   10,
   10,   12,   12,   12,   12,   12,
};
short yylen[] = {                                         2,
    1,    0,    2,    1,    1,    1,    1,    1,    2,    3,
    2,    1,    1,    4,    3,    3,    1,    3,    1,    1,
    3,    3,    3,    2,    3,    5,    5,    7,    5,    5,
    7,    4,    4,    4,    3,    3,
};
short yydefred[] = {                                      2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    4,    5,    6,    7,    8,   12,   13,    0,   17,    0,
    0,    0,    0,   19,   20,    0,    9,    0,    0,   11,
    0,   15,    0,   16,    0,    0,    0,    0,    0,    0,
    0,    0,   10,   14,   18,    0,    0,    0,    0,    0,
   25,    0,    0,   23,    0,    0,    0,    0,    0,    2,
   26,   27,    2,   29,   30,    0,    0,    0,    0,    0,
   28,   31,
};
short yydgoto[] = {                                       1,
    2,   10,   11,   12,   13,   14,   15,   35,   16,   17,
   20,   36,
};
short yysindex[] = {                                      0,
    0, -170,  -58, -250, -250,  -20,  -16,  -40,  -33,    0,
    0,    0,    0,    0,    0,    0,    0,  -34,    0,  -31,
  -23,  -34,  -34,    0,    0,  -34,    0,  -34,   -2,    0,
   28,    0, -231,    0,   24,   -7,   -3,   16,   33,  -34,
  -34,  -34,    0,    0,    0,  -19,  -38,  -36, -107, -105,
    0,   16,   16,    0,  -34,  -34,  -28,  -34,  -28,    0,
    0,    0,    0,    0,    0,  -28,  -28,  -28, -124, -115,
    0,    0,
};
short yyrindex[] = {                                      0,
    0,   64,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -12,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -6,   20,    0,    0,    0,   27,    0,   31,    0,
    0,    0,    0,    0,    0,   42,   61,   62,    0,    0,
    0,    0,
};
short yygindex[] = {                                      0,
   36,    0,   48,   51,    0,    0,    0,    4,    0,    0,
   72,   66,
};
#define YYTABLESIZE 224
short yytable[] = {                                      28,
   71,   28,   18,   28,   26,   28,   26,   19,   26,   72,
   26,   29,   33,   42,   41,   60,   40,   63,   27,   22,
   33,   31,   56,   23,   58,   30,   45,   32,   24,   38,
   24,   39,   24,   49,   22,   34,   22,   50,   22,   42,
   41,   55,   40,   52,   53,   54,   24,   24,   24,   24,
   57,   59,   22,   22,   22,   22,   43,   42,   66,   67,
   21,   68,   21,    1,   21,   42,   41,   35,   40,   42,
   41,   36,   40,   51,   42,   41,   21,   40,   21,   21,
   21,   21,   32,   47,   46,   48,   44,    3,   37,    4,
    5,    6,    7,    8,    9,   69,   61,   64,   70,   62,
   65,   33,   34,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    3,    0,    4,    5,    6,    7,    8,
    9,    0,    3,    0,    4,    5,    6,    7,    8,    9,
    3,    0,    3,    0,    6,    7,    6,    7,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   24,   25,   24,   25,
   24,   25,   24,   25,
};
short yycheck[] = {                                      40,
  125,   40,   61,   40,   45,   40,   45,  258,   45,  125,
   45,    8,   44,   42,   43,  123,   45,  123,   59,   40,
   44,   18,   61,   40,   61,   59,  258,   59,   41,   26,
   43,   28,   45,   41,   41,   59,   43,   41,   45,   42,
   43,   61,   45,   40,   41,   42,   59,   60,   61,   62,
   47,   48,   59,   60,   61,   62,   59,   42,   55,   56,
   41,   58,   43,    0,   45,   42,   43,   41,   45,   42,
   43,   41,   45,   41,   42,   43,    5,   45,   59,   60,
   61,   62,   41,   60,   61,   62,   59,  258,   23,  260,
  261,  262,  263,  264,  265,   60,   49,   50,   63,   49,
   50,   41,   41,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  258,   -1,  260,  261,  262,  263,  264,
  265,   -1,  258,   -1,  260,  261,  262,  263,  264,  265,
  258,   -1,  258,   -1,  262,  263,  262,  263,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  257,  258,
  257,  258,  257,  258,
};
#define YYFINAL 1
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 265
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'","','","'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,
"';'","'<'","'='","'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,"'^'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'{'",0,
"'}'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,"NUM","WORD","NEG","DCL_INT","DCL_BOOL","IF",
"WHILE","RETURN","ERROR",
};
char *yyrule[] = {
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
"ifsentence : IF '(' condition ')' evaluationsentence",
"ifsentence : IF '(' condition ')' decisionsentence",
"ifsentence : IF '(' condition ')' '{' sentences '}'",
"whilesentence : WHILE '(' condition ')' evaluationsentence",
"whilesentence : WHILE '(' condition ')' decisionsentence",
"whilesentence : WHILE '(' condition ')' '{' sentences '}'",
"condition : expression '=' '=' expression",
"condition : expression '<' '=' expression",
"condition : expression '>' '=' expression",
"condition : expression '<' expression",
"condition : expression '>' expression",
};
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 87 "jchecker2.y"

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
  pout("shit!");
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
	pout("WORD from yylex: yytext:"+yytext);
	return WORD;
        //System.out.println("unknown word: "+yytext+" ,return first char.");
    }
    //else return yytext.charAt(0);
  }
  else{pout("Special char returned.")}
  return tok;
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
  int a=par.doTest(new File("source.c"));
}




#line 333 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 1:
#line 23 "jchecker2.y"
{System.out.println("Work completed.\nThe grammar of the file is correct.\n   "+sentenceNo+ " sentence(s) caught in all.  ");}
break;
case 4:
#line 29 "jchecker2.y"
{sentenceNo++;}
break;
case 6:
#line 31 "jchecker2.y"
{sentenceNo++;}
break;
case 7:
#line 32 "jchecker2.y"
{sentenceNo++;}
break;
case 8:
#line 33 "jchecker2.y"
{sentenceNo++;}
break;
case 11:
#line 40 "jchecker2.y"
{}
break;
case 17:
#line 54 "jchecker2.y"
{System.out.println("This is word:"+((String)(((ParserVal)yyvsp[0]).obj));}
break;
case 18:
#line 55 "jchecker2.y"
{System.out.println("This is word:"+((String)(((ParserVal)yyvsp[0]).obj));}
break;
#line 505 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
