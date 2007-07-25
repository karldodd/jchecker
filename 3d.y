%{
import java.lang.Math;
import java.io.*;
import java.net.URL;
%}
     
/* YACC Declarations */
%token NUM WORD
%left '-' '+'
%left '*' '/'
%left NEG     /* negation--unary minus */
%right '^'    /* exponentiation        */
 
%token FILE_COOKIE CAMERA COLOR NORM DIRECTION UP LEFT SCENE SPHERE PLANE
%token GROUP POSITION ROTATE SCALE TRANSLATE
     
/* Grammar follows */
%%
world:   FILE_COOKIE { pout("got cookie"); } '{' camera scene '}'
       { pout("parsed correctly"); }
       ;

/* lets do the camera first*/
camera: CAMERA '{' camitems '}'
       ;
       
camitems: /*nil*/
       |
       camitems camitem
       ;
       
camitem: pos
       |
       DIRECTION vector
       | 
       UP vector
       |
       LEFT vector
       ;
       
scene: SCENE '{' objects '}'
       ;

objects: /*nil*/
       |
       objects object
       ;
       
object: group
       |
       sphere
       |
       plane
       ;

group: GROUP '{' transforms objects '}'
      { pout("group"); }
       ;

transforms: /*nil*/
       |
       transforms transform;

transform: rotate
       |
       scale
       |
       translate
       ;

rotate: ROTATE vector
      { pout("rotate"); }
      ;
      
scale: SCALE vector
      { pout("scale"); }
      ;
      
translate: TRANSLATE vector
      { pout("translate"); }
      ;
      
sphere: SPHERE '{' pos scalar color '}'
      { pout("sphere"); }
       ;
       
plane: PLANE '{' pos  norm '}'
      { pout("Plane"); }
      ;
      
norm: NORM vector
      { pout("norm"); }
      ;
      
pos: POSITION vector
      { pout("pos"); }
      ;
      
color: COLOR vector
      { pout("color"); }
      ;
      
vector: '<' scalar scalar scalar '>'
     { pout("vector"); }  
      ;

scalar:  exp
     { pout("exp"); }  
      ;

exp:      NUM                { $$ = $1;         }
             | exp '+' exp        { $$ = $1 + $3;    }
             | exp '-' exp        { $$ = $1 - $3;    }
             | exp '*' exp        { $$ = $1 * $3;    }
             | exp '/' exp        { $$ = $1 / $3;    }
             | '-' exp  %prec NEG { $$ = -$2;        }
             | exp '^' exp        { $$ = Math.pow($1, $3); }
             | '(' exp ')'        { $$ = $2;         }
     ;
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
  yytext=st.sval;
  pout("yy:"+yytext);
  if  (tok==st.TT_NUMBER)
    {
    yylval = st.nval;
    return NUM;
    }
  else if  (tok==st.TT_WORD)
    {
    if (yytext.equals("objectviewer"))
      return FILE_COOKIE;
    else if (yytext.equals("camera"))
      return CAMERA;
    else if (yytext.equals("position") || yytext.equals("pos"))
      return POSITION;
    else if (yytext.equals("normal") || yytext.equals("norm"))
      return NORM;
    else if (yytext.equals("color") || yytext.equals("col"))
      return COLOR;
    else if (yytext.equals("direction") || yytext.equals("dir"))
      return DIRECTION;
    else if (yytext.equals("up"))
      return UP;
    else if (yytext.equals("left"))
      return LEFT;
    else if (yytext.equals("scene"))
      return SCENE;
    else if (yytext.equals("sphere"))
      return SPHERE;
    else if (yytext.equals("plane"))
      return PLANE;
    else if (yytext.equals("group"))
      return GROUP;
    else if (yytext.equals("rotate"))
      return ROTATE;
    else if (yytext.equals("scale"))
      return SCALE;
    else if (yytext.equals("translate"))
      return TRANSLATE;
    else
      return yytext.charAt(0);
    }
  return tok;
}

int parseURL(URL url)
{
DataInputStream in;
InputStream ins;
int ret;
  dflag=true;
  try
    {
    ins = url.openStream();
    st = new StreamTokenizer(ins);
    st.slashSlashComments(true);
    st.slashStarComments(true);
    }
  catch (Exception e)
    {
    yyerror("could not open "+url.toString());
    return 0;
    }
  ret=yyparse();
  try
    {
    ins.close();
    }
  catch (Exception e)
    {
    yyerror("could not open source data");
    return 0;
    }
  return ret;
}

int parseURL(String urlspec)
{
int ret;
  try
    {
    URL url = new URL(urlspec);
    ret=parseURL(url);
    }
  catch (Exception e)
    {
    yyerror("invalid URL format");
    return 0;
    }
  return ret;
}


public static void main(String args[])
{
  yaccpar par = new yaccpar(false);
  par.parseURL("file:/kraken_home/rjamison/byacc/3d.db");
}
