package com.overload.geom;

import java.awt.geom.Point2D;

/**
 * A generic vector in 2D space.
 * @author Odell
 */
public class Vector2D {
	
	private double xDiv, yDiv;
	private double mag = -1;
	
	public Vector2D() {
		this(0.0D, 0.0D);
	}
	
	public Vector2D(final int xDiv, final int yDiv) {
		this((double) xDiv, (double) yDiv);
	}
	
	public Vector2D(final float xDiv, final float yDiv) {
		this((double) xDiv, (double) yDiv);
	}
	
	public Vector2D(final double xDiv, final double yDiv) {
		this(xDiv, yDiv, -1.0D);
	}
	
	private Vector2D(final double xDiv, final double yDiv, final double mag) {
		this.xDiv = xDiv;
		this.yDiv = yDiv;
		this.mag = mag;
	}
	
	public final double getXDiv() {
		return xDiv;
	}
	
	public final double getYDiv() {
		return yDiv;
	}
	
	/*
	 * Arithmetic
	 */
	
	public final Vector2D multiply(final double m) {
		xDiv *= m;
		yDiv *= m;
		invalidate();
		return this;
	}
	
	public final double getMagnitude() {
		if (mag < 0)
			mag = Math.sqrt(xDiv * xDiv + yDiv * yDiv);
		return mag;
	}
	
	public final double getDotProduct(final Vector2D v) {
		return getDotProduct(v.toPoint());
	}
	
	public final double getDotProduct(final Point2D p) {
		return xDiv * p.getX() + yDiv * p.getY();
	}
	
	public final Vector2D normalize() {
		double magn = getMagnitude();
		if (magn != 1) {
			xDiv /= getMagnitude();
			yDiv /= getMagnitude();
			invalidate();
		}
		return this;
	}
	
	public final Vector2D getNormalized() {
		return clone().normalize();
	}
	
	public final Vector2D toPerpendicular() {
		final double temp = -yDiv;
		this.yDiv = xDiv;
		this.xDiv = temp;
		return this;
	}
	
	public final Vector2D getPerpendicular() {
		return clone().toPerpendicular();
	}
	
	public final Point2D project(final Point2D initial) {
		initial.setLocation(initial.getX() + xDiv, initial.getY() + yDiv);
		return initial;
	}
	
	public final Point2D getProjection(final Point2D initial) {
		return project((Point2D) initial.clone());
	}
	
	public final Point2D toPoint() {
		return new Point2D.Double(xDiv, yDiv);
	}
	
	public void invalidate() {
		mag = -1;
	}
	
	@Override
	public Vector2D clone() {
		return new Vector2D(xDiv, yDiv, mag);
	}
	
	@Override
	public int hashCode() {
		return (int)(xDiv * 31.0D + yDiv);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Vector2D && ((Vector2D) obj).getXDiv() == getXDiv() && ((Vector2D) obj).getYDiv() == getYDiv();
	}
	
	public static Vector2D toVector(final Point2D initial, final Point2D terminal) {
		return new Vector2D(terminal.getX() - initial.getX(), terminal.getY() - initial.getY());
	}
	
}