package tokens;
import java.lang.*;
import java.util.*;
public class ReturnSentence extends Sentence{
	Expression e;
	public ReturnSentence(Expression e){
		this.e=e;
	}
	public String toString(){
		return "return "+e.toString()+";";
	}
}