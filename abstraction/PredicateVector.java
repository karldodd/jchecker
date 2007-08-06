package abstraction;

import tokens.*;

public class PredicateVector
{
	Predicate p;
	State s;

	PredicateVector(Predicate pred, State stat)
	{
		p = pred;
		s = stat;
	}

	Predicate getPredicate()
	{
		return p;
	}

	State getState()
	{
		return s;
	}

	public AdvCondition getAdvConditionByState()
	{
		//public enum State {STATE_FALSE, STATE_POS, STATE_NEG, STATE_TRUE}
		if (s == State.STATE_FALSE)  return new AdvCondition(new Condition(false));
		else if (s == State.STATE_POS) return p.getAdvCondition().clone();
		else if (s == State.STATE_NEG)
		{
			AdvCondition temp=p.getAdvCondition().clone();
			temp.negateSelf();
			return temp;
		}
		else if (s == State.STATE_TRUE) return new AdvCondition(new Condition(true));
		else
		{
			System.err.println("Unexpected state of a predicate, returning null");
			return null;
		}
}
