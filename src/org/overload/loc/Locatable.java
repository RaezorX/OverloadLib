package org.overload.loc;

/**
 * An x,y all-purpose position.
 * @author Odell
 */
public interface Locatable {
	
	/**
	 * @return the X value of this Locatable.
	 */
	public int getX();
	
	/**
	 * @return the Y value of this Locatable.
	 */
	public int getY();
	
	public static interface Double {
		
		/**
		 * @return the X value of this Locatable.
		 */
		public double getX();
		
		/**
		 * @return the Y value of this Locatable.
		 */
		public double getY();
		
	}
	
}