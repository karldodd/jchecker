package prover;


import tokens.*;
import abstraction.*;

import java.lang.*;
import java.util.*;

/**
*没有用到的类，测试用
*/
public class ProverTest{
	public static void main(String[] args)
	{
		Variable a=new Variable("a");
		Variable b=new Variable("b");
		
		Condition cc1=new Condition(new Expression(a),new Expression(b),ConType.larger);
		
		Condition cc2=new Condition(new Expression(a),new Expression(new Expression(b),new Expression(1),ExpType.minus),ConType.equalsmaller);
		
		AdvCondition c1=new AdvCondition(cc1);
		AdvCondition c2=new AdvCondition(cc2);

		AdvCondition cc=new AdvCondition(c1,c2,AdvCondition.Type_AND);
		
		
		Condition cc3=new Condition(new Expression(a),new Expression(b),ConType.equal);
		AdvCondition c3=new AdvCondition(cc3);
		EvaluationSentence es=new EvaluationSentence(a,new Expression(new Expression(a),new Expression(1),ExpType.plus));
		Condition cc4=new Condition(new Expression(a),new Expression(b),ConType.equal);
		AdvCondition c4=new AdvCondition(cc4);

		ArrayList<EdgeLabel> al=new ArrayList<EdgeLabel>();
		al.add(c3);
		al.add(es);
		al.add(c4);
		
		//hs.add(new AdvCondition(new Condition(false)));
		
		try{
			Prover p=ProverFactory.getProverByName("focivampyre");	
			boolean im;

			//im=p.imply(c1,c2);
			ArrayList<AdvCondition> array=new ArrayList<AdvCondition>();
			array.add(cc.substitute(a,new Expression(new Expression(a),new Expression(b),ExpType.plus)));
			im=p.isSatisfiable(array);
			if(im)System.out.println("success!");
			else System.out.println("failure!");

			System.out.println("Now starting interpolation calculation...");
			p.getInterpolation(al);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
