package prover;

import tokens.*;

import java.lang.*;
import java.util.*;

/**
*没有用到的类，测试用
*/
public class ProverFactory{

	public static Prover getProverByName(String name) throws Exception{
		if(name.equals("focivampyre")){
			return ProverImplFociVampyre.getInstance();
		}
		else throw new Exception("No specified prover found.");
	}
}

