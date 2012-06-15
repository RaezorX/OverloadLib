package org.overload.loc;

/**
 * A Locatable with a parent Locatable.<br>
 * See: {@link Locatable}
 * @author Odell
 */
public interface PLocatable extends Locatable {
	
	/**
	 * @return the parent of this PLocatable.
	 */
	public PLocatable getParent();
	
	public static interface Double extends Locatable.Double {
		
		/**
		 * @return the parent of this PLocatable.
		 */
		public PLocatable getParent();
		
	}
	
}