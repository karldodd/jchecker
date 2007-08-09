package abstraction;

import tokens.*;
import prover.*;

import java.lang.*;
import java.util.*;

public class StateSpace
{
	ArrayList<PredicateVector> predVectorArray;
	State stateSign;

	public static StateSpace createInitialStateSpace(ArrayList<Predicate> predArray)
	{
		StateSpace ss = new StateSpace();

		for (Predicate p : predArray)
		{
			ss.predVectorArray.add(new PredicateVector(p, State.STATE_TRUE));
		}
		ss.stateSign = State.STATE_TRUE;

		return ss;
	}

	public static StateSpace createInitialStateSpace(StateSpace preSs)
	{
		StateSpace ss = new StateSpace();

		for (PredicateVector pv : preSs.predVectorArray)
		{
			ss.predVectorArray.add(new PredicateVector(pv.getPredicate(), State.STATE_TRUE));
		}
		ss.stateSign = State.STATE_TRUE;

		return ss;
	}

	public StateSpace()
	{
		predVectorArray = new ArrayList<PredicateVector>();
		stateSign = State.STATE_TRUE;
	}

	public StateSpace(PredicateVector pv)
	{
		predVectorArray = new ArrayList<PredicateVector>();
		predVectorArray.add(pv);
		stateSign = State.STATE_TRUE;
	}

	boolean isFalse()
	{
		return (stateSign==State.STATE_FALSE);
	}
	
	void add(PredicateVector pv)
	{
		predVectorArray.add(pv);
	}

	public static StateSpace merge(StateSpace ss1, StateSpace ss2)
	{
		StateSpace ss3 = new StateSpace();
		for (PredicateVector pv : ss1.predVectorArray)
		{
			ss3.predVectorArray.add(pv);
		}
		for (PredicateVector pv : ss2.predVectorArray)
		{
			ss3.predVectorArray.add(pv);
		}
		return ss3;
	}
	
	public static StateSpace merge(StateSpace ss1, ArrayList<StateSpace> ssList)
	{
		StateSpace ss3 = new StateSpace();
		ss3 = merge(ss3, ss1);
		for (StateSpace newss : ssList)
		{
			ss3 = merge(ss3, newss);
		}		
		return ss3;
	}
	
	public static StateSpace calStateSpace(StateSpace preSs, Edge edge)
	{
 		StateSpace nextSs = new StateSpace();
		EdgeLabel label = edge.label;
		AdvCondition pre = null;
		ArrayList<AdvCondition> preList = new ArrayList<AdvCondition>();

		for ( PredicateVector pv : preSs.predVectorArray)
		{
			preList.add(pv.getAdvConditionByState());
		}
		pre = AdvCondition.intersectAll(preList);

		for ( PredicateVector predVect : preSs.predVectorArray)
		{
			//calculate each predicate in state space
			Predicate pred = predVect.getPredicate();		//(primitive predicate, predicate's state) and predicate
			State predState = predVect.getState();			//i.e. (x>4, STATE_NEG) and x<=4			
			State nextState;

			if (label instanceof EvaluationSentence)
			{
				nextState = calAssignment(pre, pred.getAdvCondition(), label);				
			}
			else if (label instanceof AdvCondition)
			{
				nextState = calCondition(pred, predState, label);
			}
			else
			{
				System.err.println("Unexpected EdgeLabel (neither Evaluation nor AdvCondition");
				nextState = null;
			}	
			if (nextState == State.STATE_FALSE)		//if next state space is false, return
			{
				nextSs.stateSign = State.STATE_FALSE;
				return nextSs;
			}
			nextSs.add(new PredicateVector(pred, nextState));
		}

		return nextSs;
	}

	private static State calAssignment(AdvCondition pre, AdvCondition cPred, EdgeLabel label)
	{		
		Prover p = CommonMethod.getProverInstance();
		
		//method to calculate evaluation sentence
		if (p.imply( pre, cPred.getWeakestPrecondition((EvaluationSentence)label) ))
		{
			return State.STATE_POS;
		}
		if (p.imply( pre, cPred.getNegativeCopy().getWeakestPrecondition((EvaluationSentence)label) ))
		{
			return State.STATE_NEG;
		}
		return State.STATE_TRUE;
	}

	private static State calCondition(Predicate pred, State predState, EdgeLabel label)
	{
		AdvCondition cPred = pred.getAdvCondition();
		Prover p = CommonMethod.getProverInstance();

		//method to calculate condition sentence
		if ( p.imply((AdvCondition)label, cPred) )
		{
			if (predState == State.STATE_NEG) return State.STATE_FALSE;
			else return predState;
		}
		if ( p.imply((AdvCondition)label, cPred.getNegativeCopy()) )
		{
			if (predState == State.STATE_POS) return State.STATE_FALSE;
			else return predState;
		}
		return predState;	//inherit
	}

	boolean imply(StateSpace rightSs)
	{
		ArrayList<AdvCondition> leftAdvList = new ArrayList<AdvCondition>();
		ArrayList<AdvCondition> rightAdvList = new ArrayList<AdvCondition>();
		boolean result = false;

		for (PredicateVector pv : predVectorArray)
		{
			leftAdvList.add(pv.getAdvConditionByState());
		}
		AdvCondition leftAdvCondition = AdvCondition.intersectAll(leftAdvList);
		for (PredicateVector pv : rightSs.predVectorArray)
		{
			rightAdvList.add(pv.getAdvConditionByState());
		}
		AdvCondition rightAdvCondition = AdvCondition.intersectAll(rightAdvList);

		try 
		{
			Prover p = ProverFactory.getProverByName("focivampyre");
			result = p.imply(leftAdvCondition, rightAdvCondition);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	void display()
	{
		System.out.println("state sign is: " + stateSign);
		for (PredicateVector pv : predVectorArray)
		{
			pv.display();
		}
	}
}
