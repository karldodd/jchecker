package tokens;

import java.lang.*;
import java.util.*;

//a more powerful class for condition expressions
public class AdvCondition implements EdgeLabel{
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
}