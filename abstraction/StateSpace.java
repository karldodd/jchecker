package abstraction;

import tokens.*;

import java.lang.*;
import java.util.*;

public class StateSpace{
	LinkedHashMap<Predicate,State> map;
	//private ArrayList<State> states;
	private StateSpace(){
		map=new LinkedHashMap<Predicate,State>();
	}

	//if p already exists, return the former state
	//if p does not exist, return null
	public State setPredicateState(Predicate p, State s)
	{
		return map.put(p,s);
	}
	
	public boolean containsPredicate(Predicate p)
	{
		return map.containsKey(p);
	}

	public Set<Predicate> getPredicates()
	{
		return map.keySet();
	}
	
	public State getStateByPredicate(Predicate p) throws Exception{
		if(map.containsKey(p))return map.get(p);
		else 
		{
			throw new Exception("predicate does not exist!");
		}
		//return null;
	}
	
	public static StateSpace createInitialStateSpace(Set<Predicate> pset){
		StateSpace ss=new StateSpace();
		//State s=new State(p,State.STATE_ALL);
		for(Predicate p: pset)
			ss.map.put(p,State.all);
		//ss.states.add(s);
		return ss;
	}
	
	public StateSpace getNextStateSpace(EvaluationSentence s){
		
		return null;
	}
	
	public boolean imply(StateSpace ss){
		
	}

	/*
		false if while  []=> -gi ,this routine is disabled.
		true else statespace not changed
		
		predicate  []=> wp(sentence,predicate)
		!predicate []=> wp(sentence,predicate)
		true

		wp(0<4)

		if while back, if [new]=>[old], stop now.

		wp calculation

		wp(condition,condition)=condition^condition

		getPredicates imply isSatisfyable()
		
	*/
}