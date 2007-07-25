package tokens;
import java.lang.*;
import java.util.*;
public class Variable{
	private String name;
	private Object value;
	public Variable(String name){
		this.name=name;
		value=null;
	}
	public Variable(String name, Object value){
		this.name=name;
		this.value=value;
	}
	public String getName(){return name;}
	
	public void setValue(Object value){
		this.value=value;
	}	
	public Object getValue(){
		return this.value;
	}
}