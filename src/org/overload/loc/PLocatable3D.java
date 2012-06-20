package org.overload.loc;

/**
 * A Locatable3D with a parent Locatable3D.<br>
 * See: {@link PLocatable}<br>
 * See: {@link Locatable3D}
 * @author Odell
 */
public interface PLocatable3D extends PLocatable, Locatable3D {
	public static interface Double extends PLocatable.Double, Locatable3D.Double {}
}