package org.overload.loc;

public interface Locatable3D extends Locatable {
	
	public int getZ();
	
	public static interface Double extends Locatable.Double {
		public double getZ();
	}
	
}