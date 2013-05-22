package mikera.cljutils;

import java.util.*;

import clojure.lang.IPersistentCollection;
import clojure.lang.ISeq;
import clojure.lang.PersistentList;
import clojure.lang.RT;

/*
 * A fast, lightweight sequence class designed for quick construction of lists
 * 
 * In particular, the list is mutable so you can cut / reshape sequences. Exploit
 * this "feature" with caution.
 */

public final class FastSeq implements clojure.lang.ISeq, clojure.lang.Sequential, Collection<Object> {
	public Object _first;
	public FastSeq _next;
	public Object x;
	
	public FastSeq() {
		this(null,null);
	}
	
	public FastSeq(Object o) {
		this (o,null);
	}
	
	public FastSeq(Object o, FastSeq next) {
		_first=o;
		this._next=next;
	}

	@Override
	public int count() {
		int result=0;
		FastSeq head=_next;
		while (head!=null) {
			head=head._next;
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
			if (!clojure.lang.Numbers.equiv(head._first, oseq.first())) return false;
			head=head._next;
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
		return _first;
	}

	@Override
	public ISeq next() {
		return _next;
	}

	@Override
	public ISeq more() {
		return (_next==null)?PersistentList.EMPTY:_next;
	}

	@Override
	public ISeq cons(Object o) {
		return new FastSeq(o, this);
	}

	@Override
	public int size() {
		return count();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		FastSeq head=this;
		while (head!=null) {
			if (o.equals(head._first)) return true;
			head=head._next;
		}
		return false;
	}
	
	public class FastSeqIterator implements Iterator<Object> {
		FastSeq head=FastSeq.this;
		
		@Override
		public boolean hasNext() {
			return head!=null;
		}

		@Override
		public Object next() {
			Object result=head._first;
			head=head._next;
			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Iterator<Object> iterator() {
		return new FastSeqIterator();
	}

	@Override
	public Object[] toArray() {
		Object[] arr=new Object[count()];
		int i=0;
		for (Object o:this) {
			arr[i++]=o;
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] toArray(Object[] a) {
		int i=0;
		for (Object o:this) {
			a[i++]=o;
		}
		return a;
	}

	@Override
	public boolean add(Object e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<? extends Object> c) {
		for (Object o:c) {
			if (!contains (o)) return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Object> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<? extends Object> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<? extends Object> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

}
