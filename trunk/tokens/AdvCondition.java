package tokens;

import java.lang.*;
import java.util.*;

//a more powerful class for condition expressions
public class AdvCondition implements EdgeLabel,Cloneable{
    public AdvCondition c1;
    public AdvCondition c2;
    
    public static int Type_AND=0;
    public static int Type_OR=1;
    public static int Type_ATOM=2;

    public Condition atom;

    public int jointType;

    public AdvCondition(AdvCondition c1, AdvCondition c2, int type){
	this.c1=c1;
	this.c2=c2;
	this.jointType=type;
	this.atom=null;
    }
    public AdvCondition(Condition c){ 
	this.atom=c;
	this.c1=null;
	this.c2=null;
	this.jointType=AdvCondition.Type_ATOM;
    }
    public AdvCondition clone(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return new AdvCondition(this.atom.clone());
	}
	else{
	    return new AdvCondition(c1.clone(),c2.clone(),jointType); 
	}
    }
    public AdvCondition substitute(Variable v, Expression e){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return new AdvCondition(this.atom.substitute(v,e));
	}
	else{
	    return new AdvCondition(c1.substitute(v,e),c2.substitute(v,e),jointType); 
	}
    }

    public String toString(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toString();
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "("+c1.toString()+"&&"+c2.toString()+")";
	}
	else{
	    return "("+c1.toString()+"||"+c2.toString()+")";
	}
    }
    
    public String toLFString(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toLFString();
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "(/\\"+c1.toLFString()+" "+c2.toLFString()+")";
	}
	else{
	    return "(\\/"+c1.toLFString()+" "+c2.toLFString()+")";
	}
    }

    public String toFociString(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toFociString();
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "& ["+c1.toFociString()+" "+c2.toFociString()+"]";
	}
	else{
	    return "| ["+c1.toFociString()+" "+c2.toFociString()+"]";
	}
    }

    public String toFociString(Map<String,String> map, Map<String,String> revertMap){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toFociString(map);
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "& ["+c1.toFociString(map)+" "+c2.toFociString(map)+"]";
	}
	else{
	    return "| ["+c1.toFociString(map)+" "+c2.toFociString(map)+"]";
	}
    }
    
    public String toFociString(Map<String,String> map){
	if(this.jointType==AdvCondition.Type_ATOM){
	    return this.atom.toFociString(map);
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    return "& ["+c1.toFociString(map)+" "+c2.toFociString(map)+"]";
	}
	else{
	    return "| ["+c1.toFociString(map)+" "+c2.toFociString(map)+"]";
	}
    }

    public void negateSelf(){
	if(this.jointType==AdvCondition.Type_ATOM){
	    this.atom.negateSelf();
	}
	else if (this.jointType==AdvCondition.Type_AND){
	    c1.negateSelf();
	    c2.negateSelf();
	    jointType=AdvCondition.Type_OR;
	}
	else{
	    c1.negateSelf();
	    c2.negateSelf();
	    jointType=AdvCondition.Type_AND;
	}
    }
    
    //get the wp(es,this). This instance won't be changed.
    public AdvCondition getWeakestPrecondition(EvaluationSentence es){
	return this.substitute(es.v,es.e);
    }

    public AdvCondition getWeakestPrecondition(AdvCondition c){
	   return new AdvCondition(this.clone(),c.clone(),Type_AND); 
    }

    public AdvCondition getNegativeCopy(){
	AdvCondition c=this.clone();
	c.negateSelf();
	return c;
    }
}
