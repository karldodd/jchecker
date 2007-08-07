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

	StateSpace()
	{
		predVectorArray = new ArrayList<PredicateVector>();
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

		try {
		Prover p = ProverFactory.getProverByName("focivampyre");
		result = p.imply(leftAdvCondition, rightAdvCondition);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}
}
