package com.overload.util;

import java.lang.reflect.Array;
import java.util.Comparator;

public class Arrays {
	
	/*
	 * contains
	 */
	
	/**
	 * Checks to see if the list of elements, contains the given element.
	 * @param element - the element to look for.
	 * @param elements - the elements to search through.
	 * @return <code>true</code>, if the element was found, otherwise <code>false</code>.
	 */
	@SafeVarargs
	public static <E> boolean contains(E element, E... elements) {
		for (E e : elements)
			if (element == null ? e == null : element.equals(e))
				return true;
		return false;
	}
	
	@SafeVarargs
	public static <E> E[] newArray(int length, E... es) {
		@SuppressWarnings("unchecked")
		final E[] out = (E[]) Array.newInstance(es.getClass().getComponentType(), length);
		return out;
	}
	
	/*
	 * merging
	 */
	
	@SafeVarargs
	public static <E> E[] merge(E[]... s) {
		return merge(false, s);
	}
	
	@SafeVarargs
	public static <E> E[] merge(boolean sort, E[]... s) {
		return _merge(sort, s);
	}
	
	@SafeVarargs
	private static <E> E[] _merge(boolean sort, E[]... s) {
		if (s != null && s.length > 0) {
			int len = 0;
			for (int i = 0; i < s.length; i++) {
				len += s[i].length;
			}
			@SuppressWarnings("unchecked")
			final E[] alloc = (E[]) Array.newInstance(s[0].getClass().getComponentType(), len);
			int off = 0;
			for (int i = 0; i < s.length; i++) {
				System.arraycopy(s[i], 0, alloc, off, s[i].length);
				off += s[i].length;
			}
			if (sort)
				java.util.Arrays.sort(alloc);
			return alloc;
		}
		return null;
	}
	
	public static int[] merge(int[]... i) {
		return merge(false, i);
	}
	
	public static int[] merge(boolean sort, int[]... s) {
		return _merge(sort, s);
	}
	
	private static int[] _merge(boolean sort, int[]... s) {
		int len = 0;
		for (int i = 0; i < s.length; i++)
			len += s[i].length;
		final int[] alloc = new int[len];
		int off = 0;
		for (int i = 0; i < s.length; i++) {
			System.arraycopy(s[i], 0, alloc, off, s[i].length);
			off += s[i].length;
		}
		if (sort) 
			java.util.Arrays.sort(alloc);
		return alloc;
	}
	
	@SafeVarargs
	public static <E> E[] merge(E[] e, E... es) {
		return merge(false, e, es);
	}
	
	@SafeVarargs
	public static <E> E[] merge(boolean sort, E[] e, E... es) {
		return _merge(sort, e, es);
	}
	
	public static int[] merge(int[] f, int... combs) {
		return merge(false, f, combs);
	}
	
	public static int[] merge(boolean sort, int[] f, int... combs) {
		return _merge(sort, f, combs);
	}
	
	/*
	 * basics
	 */
	
	public static int[] toPrimitive(Integer... objects) {
		final int[] list = new int[objects.length];
		System.arraycopy(objects, 0, list, 0, list.length);
		return list;
	}
	
	@SafeVarargs
	public static <E> E[] reverse(E... orig) {
		E[] reversed = orig.clone();
		for (int i = 0; i < orig.length; i++)
			reversed[reversed.length - 1 - i] = orig[i];
		return reversed;
	}
	
	public static <E> boolean equals(E[] e1, E[] e2, Comparator<E> comp) {
		if (e1.length != e2.length)
			return false;
		for (int i = 0; i < e1.length; i++) {
			if (comp.compare(e1[i], e2[i]) != 0) {
				return false;
			}
		}
		return true;
	}
	
}