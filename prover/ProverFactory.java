package prover;

import tokens.*;

import java.lang.*;
import java.util.*;

public class ProverFactory{

	public static Prover getProverByName(String name) throws Exception{
		switch(name)
		{
			case "focivampyre": 
				return ProverImplFociVampyre.getInstance();
			default: 
				throw new Exception("No specified prover found.");
		}
	}
}

