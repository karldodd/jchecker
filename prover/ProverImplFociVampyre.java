package prover;

import tokens.*;
import abstraction.*;

import java.lang.*;
import java.util.*;
import java.io.*;

public class ProverImplFociVampyre implements Prover{
	private static ProverImplFociVampyre prover=null;
	private ProverImplFociVampyre(){}

	public static Prover getInstance(){
		if(prover==null)prover=new ProverImplFociVampyre();
		return (Prover)prover;
	}

	//if the edges are satisfiable, throw Exception
	public Set<Predicate> getInterpolation(Set<EdgeLabel> edges) throws Exception{
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
		File f=writeStringToTempFile(sb.toString());
		return null;
	}

	//to tell if c1 implies c2
	public boolean imply(AdvCondition c1, AdvCondition c2){
		return implyByVampyre(c1.toLFString(),c2.toLFString());
	}

	//to tell if the condition is satisfiable
	public boolean isSatisfiable(AdvCondition c)
	{
		return false;
		//throw new Exception("to be implemented...");
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
			System.err.println("Error occurred when writing temp file. The answer is regarded as false.");
			return false;
		}
		
		
		Process vampyreProcess=null;
		try {               
	       		vampyreProcess = Runtime.getRuntime().exec(new String[]{"./prover.byte",f.getAbsolutePath()});
		}     
     		catch(IOException e) {
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
			System.err.println("Warning: vampyre engine failed. The answer is regarded as false.");
			return false;
		}
		return result;
	}

	private File writeStringToTempFile(String s) throws Exception{
		Calendar rightNow = Calendar.getInstance(); 
		String fileName=Long.toString(rightNow.getTimeInMillis());

      		//File f=new File(".");
		//File tempFile=new File(f.getCanonicalPath()+"/"+fileName);
		//System.out.println(tempFile.getAbsolutePath());
		System.out.println("Writing:\n"+s);
		File tempFile=File.createTempFile("for","vam");
		tempFile.deleteOnExit();

		FileWriter fw=new FileWriter(tempFile);
		fw.write(s);
		fw.close();
		return tempFile;
	}
}
