%{
import java.lang.*;
//import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
%}

/* YACC Declarations */
%token NUM WORD
%left '-' '+'
%left '*' '/'
%left NEG /* negation--unary minus */
%right '^' /* exponentiation */

%token DCL_INT DCL_BOOL IF WHILE
/*DCL means 'declare'*/

/* Grammar follows */
%%

/* eliminate all the '\n's and ' 's first! */

input: /*empty*/
 | block {System.out.println("block detected");}
 ;

block: block sentence
 ;

sentence: evaluationsentence
 | decisionsentence
 | declarationsentence
 ;

evaluationsentence: WORD '=' expression ';'
 ;

expression: NUM
 | WORD
 | expression '+' expression
 | expression '-' expression
 | expression '*' expression
 | expression '/' expression
 | '-' expression
 | expression '^' expression
 | '(' expression ')'
 ;

decisionsentence: ifsentence
| whilesentence
;

words: WORD
| words ',' WORD
;

declarationsentence: DCL_INT words ';'
| DCL_BOOL words ';'
;

condition: expression '=' '=' expression
 | expression '<' '=' expression
 | expression '>' '=' expression
 | expression '<' expression
 | expression '>' expression
;

ifsentence: IF '(' condition ')' evaluationsentence
| IF '(' condition ')' decisionsentence
| IF '(' condition ')' '{' block '}'
;

whilesentence: WHILE '(' condition ')' evaluationsentence
| WHILE '(' condition ')' decisionsentence
| WHILE '(' condition ')' '{' block '}'

	       /*
input: /* empty string */
	       /*| input line
 ;

line: '\n'
 | exp '\n' { System.out.println(" " + $1.dval + " "); }
 ;

exp: NUM { $$ = $1; }
 | exp '+' exp { $$ = new ParserVal($1.dval + $3.dval); }
 | exp '-' exp { $$ = new ParserVal($1.dval - $3.dval); }
 | exp '*' exp { $$ = new ParserVal($1.dval * $3.dval); }
 | exp '/' exp { $$ = new ParserVal($1.dval / $3.dval); }
 | '-' exp %prec NEG { $$ = new ParserVal(-$2.dval); }
 | exp '^' exp { $$ = new ParserVal(Math.pow($1.dval, $3.dval)); }
 | '(' exp ')' { $$ = $2; }
 ;*/
%%

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

  //pout("yy:"+yytext);
  if  (tok==st.TT_NUMBER)
  {
    yylval = new ParserVal((int)st.nval);
    return NUM;
  }
  else if  (tok==st.TT_WORD)
  {
    pout("yytext: "+yytext);
    char head=yytext.charAt(0);

    if (yytext.equals("if"))
      return IF;
    else if (yytext.equals("while"))
      return WHILE;
    else if (yytext.equals("int"))
      return DCL_INT;
    else if (yytext.equals("bool"))
      return DCL_BOOL;
    else
    {
	yylval= new ParserVal((Object)yytext);
	return WORD;
        //System.out.println("unknown word: "+yytext+" ,return first char.");
    }
    //return yytext.charAt(0);
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
    st.eolIsSignificant(false);
    //设置引号的符号表示
    st.quoteChar('"');
    //将ASCII码为0-32的字符设为空白符
    st.whitespaceChars(0, 32);
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

