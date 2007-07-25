package abstraction;

import tokens.*;

import java.lang.*;
import java.util.*;

public class Predicate {
	private AdvCondition c;
	public Predicate(AdvCondition c){
		this.c=c;
	}
	public AdvCondition getAdvCondition(){return c;}
	public String toString(){
		return c.toString();
	}

	public String toLFString(){
		return c.toLFString();
	}
}