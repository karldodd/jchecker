package abstraction;

import tokens.*;

import java.lang.*;
import java.util.*;

//public enum State {ptrue,pfalse,all}

//The following states mean respectively:  predicate is empty, pedicate is postive, predicate is negtive, predicate is universal
public enum State {STATE_FALSE, STATE_POS, STATE_NEG, STATE_TRUE}

/*
public class State
{
	public static int STATE_PTRUE=0;
	public static int STATE_PFALSE=1;
	public static int STATE_ALL=2;
	Predicate p;
	int state;
	public State(Predicate p, int state)
	{
		this.p=p;
		this.state=state;
	}
}*/
