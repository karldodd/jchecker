package prover;

import tokens.*;
import abstraction.*;

import java.lang.*;
import java.util.*;

public interface Prover{
	//if the edges are satisfiable, throw Exception
	public List<Predicate> getInterpolation(List<EdgeLabel> edges) throws Exception;

	//to tell if c1 implies c2
	public boolean imply(AdvCondition c1, AdvCondition c2);

	//to tell if the condition is satisfiable
	public boolean isSatisfiable(List<AdvCondition> clist);
	
		
}
