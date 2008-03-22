package prover.impl.xxx.egraph.utils.DisjointSetImpl;

public class HeadNode<K> extends Node<K> {
	private DisjointSet<K> ds;

	public HeadNode(DisjointSet<K> ds, K stuff) {
		super(stuff);
		this.ds = ds;
	}

	public DisjointSet<K> getMyDisjointSet() {
		return this.ds;
	}
}
