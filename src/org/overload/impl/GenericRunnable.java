package org.overload.impl;

public abstract class GenericRunnable<G> implements Runnable {
	
	protected G obj;
	
	public GenericRunnable(final G obj) {
		this.obj = obj;
	}
	
}