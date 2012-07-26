package org.overload.algo.pathfinding;

import java.util.ArrayList;

import org.overload.algo.Flags;
import org.overload.loc.Locatable;
import org.overload.loc.Node;
import org.overload.loc.PLocatable;

/**
 * A node is defined as an x,y pair as it would show up on a computer screen.<br>
 * Up: y - 1<br>
 * Down: y + 1<br>
 * Left: x + 1<br>
 * Right: x - 1<br>
 * @author Odell
 */
abstract class PNode extends Node implements PLocatable, Comparable<PNode> {
	
	public final static double DIAGONAL = Math.sqrt(2.0D), STRAIGHT = 1.0D;
	
	PNode(final int x, final int y) {
		super(x, y);
	}
	
	PNode(final Locatable loc) {
		super(loc);
	}
	
	/**
	 * Gets all adjacent nodes to this node.<br>
	 * Starts @ north and goes clockwise.
	 * @param eight To search in 4 or 8 directions.
	 * @return an array of adjacent nodes.
	 */
	protected abstract PNode[] getAdjacents(final boolean eight);
	
	protected PNode[] getOpenAdjacents(final Flags<PLocatable> flags, final boolean eight) {
		final PNode[] all = getAdjacents(eight);
		final ArrayList<PNode> open = new ArrayList<PNode>(all.length);
		for (final PNode n : all) {
			final long s = System.nanoTime();
			if (!flags.blocked(n)) {
				long elapsed = System.nanoTime() - s;
				Pathfinder.NANO_BLOCKED += elapsed;
				open.add(n);
			}
		}
		return open.toArray(new PNode[open.size()]);
	}
	
	@Override
	public int compareTo(PNode pn) {
		if (getY() != pn.getY())
			return getY() - pn.getY();
		return getX() - pn.getX();
	}
	
	/**
	 * Determines whether the two locatable nodes are diagonal each other or not.
	 * @return <tt>true</tt> if diagonal, otherwise <tt>false</tt>.
	 */
	public static boolean isDiagonal(final Locatable l1, final Locatable l2) {
		return l1 != null && l2 != null && l1.getX() != l2.getX() && l1.getY() != l2.getY();
	}
	
}