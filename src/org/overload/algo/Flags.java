package org.overload.algo;

import org.overload.loc.Locatable;

/**
 * A collision flag provider.
 * @author Odell
 * @param <E>
 * 			Flag argument type which extends Locatable.
 */
public interface Flags<E extends Locatable> {
	
	/**
	 * Determines whether the given 
	 * @param loc
	 * 			A locatable which can be tested.
	 * @return whether the given locatable is blocked or not.
	 */
	public boolean blocked(final E loc);
	
}