package com.overload.algorithms.floodfill;

import java.util.LinkedList;

import com.overload.algorithms.floodfill.Floodfill.FillResult;
import com.overload.algorithms.floodfill.Floodfill.Flags;
import com.overload.loc.Locatable;
import com.overload.loc.Node;

/**
 * A queue (expanding) flood fill implementation.
 * @author Odell
 */
class QueueImpl implements AlgorithmDefinition {
	
	private Flags flags;
	private boolean eight;

	@Override
	public void setEight(boolean eight) {
		this.eight = eight;
	}

	@Override
	public void setFlags(Flags flags) {
		this.flags = flags;
	}
	
	@Override
	public void fill(final Locatable start, final FillResult fr) {
		if (flags == null)
			throw new IllegalStateException("collision flags are null");
		final LinkedList<Node> q = new LinkedList<Node>();
		Node curr = new Node(start);
		do {
			if (flags.blocked(curr))
				continue;
			fr.locationFound(curr);
			final Node[] expand = eight ? new Node[] {
				curr.derive(0, -1), curr.derive(1, 0), curr.derive(0, 1), curr.derive(-1, 0),
				curr.derive(1, -1), curr.derive(1, 1), curr.derive(-1, 1), curr.derive(-1, -1),
			} : new Node[] {
				curr.derive(0, -1), curr.derive(1, 0), curr.derive(0, 1), curr.derive(-1, 0)
			};
			for (final Node temp : expand) {
				if (!flags.blocked(temp))
					q.offer(temp);
			}
		} while ((curr = q.poll()) != null);
	}
	
}
