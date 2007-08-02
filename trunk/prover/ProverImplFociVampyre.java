package prover;

import tokens.*;
import abstraction.*;
import parsers.*;
import java.lang.*;
import java.util.*;
import java.io.*;

public class ProverImplFociVampyre implements Prover{
	private static boolean debugging=true;

	private static ProverImplFociVampyre prover=null;
	private ProverImplFociVampyre(){}

	public static Prover getInstance(){
		if(prover==null)prover=new ProverImplFociVampyre();
		return (Prover)prover;
	}

	/*public Set<String> doSubstitution(Set<EdgeLabel> edges){
		//for evaluation sentence v=e;
		//e is substituted by former regulation;
		//and generate new rule for v;	
		
	}*/

	//if the edges are satisfiable, throw Exception
	public Set<Predicate> getInterpolation(List<EdgeLabel> edges) throws Exception{
		StringBuilder sb=new StringBuilder("");
		boolean firstElement=true;

		TreeMap<String,String> map=new TreeMap<String,String>();
		TreeMap<String,String> revertMap=new TreeMap<String,String>();

		for(EdgeLabel e : edges){
			if(firstElement)
				firstElement=false;
			else
				sb.append(" ; ");
			sb.append(e.toFociString(map,revertMap));
		}
		//then write the string to a temp file
		System.out.println("String sent to foci:\n"+sb.toString());
		File fin=writeStringToTempFile(sb.toString());
		File fout=File.createTempFile("for","foci");
		fout.deleteOnExit();

		Process fociProcess = Runtime.getRuntime().exec(new String[]{"./foci.byte",fin.getAbsolutePath(),fout.getAbsolutePath()});
		
		BufferedReader inputReader  = new BufferedReader(new InputStreamReader(fociProcess.getInputStream()));
		BufferedReader errorReader  = new BufferedReader(new InputStreamReader(fociProcess.getErrorStream()));
		
		StreamTokenizer inputST=new StreamTokenizer(inputReader);
		StreamTokenizer errorST=new StreamTokenizer(errorReader);

		inputST.eolIsSignificant(false);
		errorST.eolIsSignificant(false);
		
		fociProcess.waitFor();

		FociParser fp=new FociParser(false);
		if(debugging)
		{
			System.out.println("=============================");
			System.out.println("Showing revertMap:");
			Set<String> s=revertMap.keySet();
			for(String key : s){
				System.out.println("Key: "+key+"  value: "+revertMap.get(key));
			}
			System.out.println("=============================");
		}
		fp.parseFile(fout,revertMap);
		ArrayList<AdvCondition> interpolations=fp.getConditionPool();

		HashSet<Predicate> predicates=new HashSet<Predicate>();
		for(AdvCondition ac : interpolations)
		{
			if(debugging)System.out.println("Getting predicate: "+ac.toString());
			Predicate p=new Predicate(ac);
			predicates.add(p);
		}
		
		return predicates;
	}

	//to tell if c1 implies c2
	public boolean imply(AdvCondition c1, AdvCondition c2){
		return implyByVampyre(c1.toLFString(),c2.toLFString());
	}

	//to tell if the condition is satisfiable
	public boolean isSatisfiable(AdvCondition c)
	{
		
		boolean result=false;
		boolean gotresult=false;
		try{
		System.out.println("Formula sent to foci:\n"+c.toString());
		File fin=writeStringToTempFile(c.toFociString());
		File fout=File.createTempFile("for","foci");
		fout.deleteOnExit();

		Process fociProcess = Runtime.getRuntime().exec(new String[]{"./foci.byte",fin.getAbsolutePath(),fout.getAbsolutePath()});
		
		BufferedReader inputReader  = new BufferedReader(new InputStreamReader(fociProcess.getInputStream()));
		BufferedReader errorReader  = new BufferedReader(new InputStreamReader(fociProcess.getErrorStream()));
		
		StreamTokenizer inputST=new StreamTokenizer(inputReader);
		StreamTokenizer errorST=new StreamTokenizer(errorReader);

		inputST.eolIsSignificant(false);
		errorST.eolIsSignificant(false);

		boolean streamEnd=false;
		StreamTokenizer st=inputST;
		while(!streamEnd){
			int tok=st.nextToken();
			if(tok==st.TT_EOF)streamEnd=true;
			else{
				if(tok==st.TT_WORD)
				{
					//System.out.println(st.sval);
					if(st.sval.equals("SAT")){
						result=true;
						streamEnd=true;
						gotresult=true;
					}
					else if (st.sval.equals("UNSAT")){
						result=false;
						streamEnd=true;
						gotresult=true;
					}
				}
			}
		}
		if(!gotresult){
			streamEnd=false;
			st=errorST;
			while(!streamEnd){
				int tok=st.nextToken();
				if(tok==st.TT_EOF)streamEnd=true;
				else{
					if(tok==st.TT_WORD)
					{
						//System.out.println(st.sval);
						if(st.sval.equals("Fatal")){
							System.out.println("Warning: error occurred. There might be syntax error! The answer is regarded as false.");
							result=false;
							streamEnd=true;
							gotresult=true;
						}
						else if (st.sval.equals("error")){
							System.out.println("Warning: error occurred. There might be syntax error! The answer is regarded as false.");
							result=false;
							streamEnd=true;
							gotresult=true;
						}
					}
				}
			}
		}
		if(!gotresult){
			System.out.println("Warning: No useful signals received. The answer is regarded as false.");
			return false;
		}
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.out.println("Warning: Exception caught. The answer is regarded as false.");
			return false;
		}
		return result;
	}

	private	boolean implyByVampyre(String c1lf,String c2lf){
		boolean result=false;
		boolean gotresult=false;
		String source="V1:\n pf (=> "+c1lf+" "+c2lf+" ).";
		//then write the string to a temp file
		File f=null;
		try{
			f=writeStringToTempFile(source);
		}
		catch(Exception e)
		{
			System.err.println("Warning: Error occurred when writing temp file. The answer is regarded as false.");
			return false;
		}
		
		
		Process vampyreProcess=null;
		try {               
	       		vampyreProcess = Runtime.getRuntime().exec(new String[]{"./prover.byte",f.getAbsolutePath()});
		}     
     	catch(Exception e) {
			System.err.println("Error when invoking vampyre:\n" + e); 
 			System.exit(1);
		}

		try{
			//InputStream[] inStreams = new InputStream[] {process.getInputStream(),process.getErrorStream()};
			BufferedReader br  = new BufferedReader(new InputStreamReader(vampyreProcess.getInputStream()));
			StreamTokenizer st=new StreamTokenizer(br);
			st.commentChar('%');
			st.eolIsSignificant(false);

			vampyreProcess.waitFor();
		
			boolean streamEnd=false;
			while(!streamEnd){
				int tok=st.nextToken();
				if(tok==st.TT_EOF)streamEnd=true;
				else{
					if(tok==st.TT_WORD)
					{
						//System.out.println(st.sval);
						if(st.sval.equals("successfully.")){
							result=true;
							streamEnd=true;
							gotresult=true;
						}
						/*else if (st.sval.equals("Error")){
							result=false;
							streamEnd=true;
							gotresult=true;
						}*/
					}
				}
			}
			br.close();

			br= new BufferedReader(new InputStreamReader(vampyreProcess.getErrorStream()));
			st=new StreamTokenizer(br);
			st.commentChar('%');
			st.eolIsSignificant(false);
			streamEnd=false;
			while(!streamEnd){
				int tok=st.nextToken();
				if(tok==st.TT_EOF)streamEnd=true;
				else{
					if(tok==st.TT_WORD)
					{
						//System.out.println(st.sval);
						/*if(st.sval.equals("successfully")){
							result=true;
							streamEnd=true;
							gotresult=true;
						}*/
						if (st.sval.equals("Error")){
							result=false;
							System.err.println("Vampyre reported error: there might be syntax error(s). The answer is regarded as false.");
							streamEnd=true;
							gotresult=true;
						}
					}
				}
			}
			br.close();
		}
		catch(Exception e)
		{
			System.err.println("Error occurred when excuting vampyre. The answer is regarded as false.");
			return false;
		}
		if(!gotresult)
		{
			//throw new Exception("result is null when invoking implyByVampyre");
			//System.err.println("Warning: vampyre engine failed. The answer is regarded as false.");
			return false;
		}
		return result;
	}

	private File writeStringToTempFile(String s) throws Exception{
		//Calendar rightNow = Calendar.getInstance(); 
		//String fileName=Long.toString(rightNow.getTimeInMillis());

      		//File f=new File(".");
		//File tempFile=new File(f.getCanonicalPath()+"/"+fileName);
		//System.out.println(tempFile.getAbsolutePath());
		System.out.println("Writing:\n"+s);
		File tempFile=File.createTempFile("for","vamfoci");
		tempFile.deleteOnExit();

		FileWriter fw=new FileWriter(tempFile);
		fw.write(s);
		fw.close();
		return tempFile;
	}
}
