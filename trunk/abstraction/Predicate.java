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
	
	public AdvCondition getAdvConditionByState(State s){
		//public enum State {STATE_FALSE, STATE_POS, STATE_NEG, STATE_TRUE}
		if(s==State.STATE_FALSE)return new AdvCondition(new Condition(false));
		else if(s==State.STATE_POS)return this.c.clone();
		else if(s==State.STATE_NEG){
			AdvCondition temp=this.c.clone();
			temp.negateSelf();
			return temp;
		}
		else if(s==State.STATE_TRUE)return new AdvCondition(new Condition(true));
		else{
			System.err.println("Unexpected state of a predicate, returning null");
			return null;
		}
	}
}
