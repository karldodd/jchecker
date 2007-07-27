package prover;


import tokens.*;
import abstraction.*;
//import java.io.*;

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
		try{
			Prover p=ProverFactory.getProverByName("focivampyre");	
			boolean im;

			//im=p.imply(c1,c2);
			im=p.isSatisfiable(cc);
			if(im)System.out.println("success!");
			else System.out.println("failure!");
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
	}
}
