package mikera.cljutils;

import clojure.lang.IPersistentCollection;
import clojure.lang.ISeq;
import clojure.lang.PersistentList;
import clojure.lang.RT;

/*
 * A fast sequence class designed for quick construction of lists
 */

public final class FastSeq implements clojure.lang.ISeq {
	public Object first;
	public FastSeq next;
	
	public FastSeq() {
		this(null,null);
	}
	
	public FastSeq(Object o) {
		this (o,null);
	}
	
	public FastSeq(Object o, FastSeq next) {
		first=o;
		this.next=next;
	}

	@Override
	public int count() {
		int result=0;
		FastSeq head=next;
		while (head!=null) {
			head=head.next;
			result++;
		}
		return result;
	}

	@Override
	public IPersistentCollection empty() {
		return null;
	}

	@Override
	public boolean equiv(Object o) {
		ISeq oseq=RT.seq(o);
		FastSeq head=this;
		while (head!=null) {
			if (oseq==null) return false;
			if (!clojure.lang.Numbers.equiv(head.first, oseq.first())) return false;
			head=head.next;
			oseq=oseq.next();
		}
		return (o==null);
	}

	@Override
	public ISeq seq() {
		return this;
	}

	@Override
	public Object first() {
		return first;
	}

	@Override
	public ISeq next() {
		return next;
	}

	@Override
	public ISeq more() {
		return (next==null)?PersistentList.EMPTY:next;
	}

	@Override
	public ISeq cons(Object o) {
		return new FastSeq(o, this);
	}

}
