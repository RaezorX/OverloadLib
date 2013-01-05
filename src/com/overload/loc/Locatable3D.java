package com.overload.loc;

/**
 * An x,y,z all-purpose position.
 * @author Odell
 */
public interface Locatable3D extends Locatable {
	
	/**
	 * @return the Z position of this Locatable.
	 */
	public int getZ();
	
	/**
	 * Double precision Locatable.
	 * @author Odell
	 */
	public static interface Double extends Locatable.Double {
		
		/**
		 * @return the Z position of this Locatable.
		 */
		public double getZ();
		
	}
	
}