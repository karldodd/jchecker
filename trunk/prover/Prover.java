package prover

import tokens.*;
import abstraction.*;

import java.lang.*;
import java.util.*;

public interface Prover{
	//if the edges are satisfiable, throw Exception
	Set<Predicate> getInterpolation(Set<EdgeLabel> edges) throws Exception;

	//to tell if c1 implies c2
	boolean imply(AdvCondition c1, AdvCondition c2);

	//to tell if the condition is satisfiable
	boolean isSatisfiable(AdvCondition c);
	
		
}
