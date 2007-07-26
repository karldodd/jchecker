package prover;

import tokens.*;

import java.lang.*;
import java.util.*;
import java.io.*;

public class ProverImplFociVampyre extends Prover{
	private static ProverImplFociVampyre prover;
	private ProverImplFociVampyre(){}

	public static getInstance(){
		if(prover==null)prover=new ProverImplFociVampyre();
		return prover;
	}

	//if the edges are satisfiable, throw Exception
	Set<Predicate> getInterpolation(Set<EdgeLabel> edges) throws Exception{
		StringBuilder sb=new StringBuilder("");
		boolean firstElement=true;
		for(EdgeLabel e : edges){
			if(firstElement)
				firstElement=false;
			else
				sb.append(" ; ");
			sb.append(e.toLFString());			
		}
		//then write the string to a temp file
	}

	//to tell if c1 implies c2
	boolean imply(AdvCondition c1, AdvCondition c2){
		return implyByVamPyre(c1.toLFString(),c2.toLFString());
	}

	//to tell if the condition is satisfiable
	boolean isSatisfiable(AdvCondition c);

	private	boolean implyByVampyre(String c1lf,String c2lf){
		String source="pf: (=> "+c1lf+" "+c2lf+" )";
		//then write the string to a temp file
		File f=writeStringToTempFile(source);
		
		Process vampyreProcess;
		try {               
	       		vampyreProcess = Runtime.getRuntime().exec(new String[]{"./prover.byte",f.getAbsolutPath()});
		}     
     		catch(IOException e) {
			System.err.println("Error when invoking vampyre:\n" + e); 
 			System.exit(1);
		}
		//InputStream[] inStreams = new InputStream[] {process.getInputStream(),process.getErrorStream()};
		BufferedReader br  = new BufferedReader(new InputStreamReader(vampyreProcess.getInputStream()));
		StreamTokenizer st=new StreamTokenizer(br);
		st.commentChar("%");
		st.eolIsSignificant(false);

		vampyreProcess.waitFor();
		
		boolean streamEnd=false;
		while(!streamEnd){
			int tok=st.nextToken();
			if(tok==st.TT_EOF)streamEnd=true;
			else{
				
			}
		}
				
	}

	private File writeStringToTempFile(String s){
		
	}
}
