/**
 * 
 */
package prover.impl.xxx.egraph.core;

import prover.impl.xxx.egraph.utils.DisjointSetImpl.DisjointSetEnvironment;

/**
 * @author KarlHe
 * 
 */
public abstract class FunctionalElement implements IEgraphElement {
	public abstract String getFunctionName();
	public abstract int getParamSize();
	protected abstract AtomicElement[] getParams();

	/**
	 * 检查两个FunctionalElement是否能够进行等价类合并
	 * @param fe
	 * @param dse
	 * @return
	 */
	public boolean connectedWith(FunctionalElement fe,
			DisjointSetEnvironment<IEgraphElement> dse) {
		if (!this.getFunctionName().equals(fe.getFunctionName()))
			return false;
		if (this.getParamSize() != fe.getParamSize())
			return false;

		AtomicElement[] array1 = this.getParams();
		AtomicElement[] array2 = this.getParams();

		try {
			for (int i = 0; i < this.getParamSize(); i++) {
				if (dse.findSet(array1[i]) != dse.findSet(array2[i]))
					return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
