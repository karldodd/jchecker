/**
 * 
 */
package tokens;

/**
 * @author KarlHe
 *
 */
public class Number extends Expression {
	private int num;
	public Number(int num){
		this.num=num;
	}
	
	public int getNum(){
		return this.num;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.num;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof Number))return false;
		return this.num==((Number)obj).num;
	}
	
	@Override
	public Number substitute(Variable v, Expression e) {
		// TODO Auto-generated method stub
		return new Number(this.num);
	}
	
	@Override
	public Number clone() {
		// TODO Auto-generated method stub
		return new Number(this.num);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.num+"";
	}
}
