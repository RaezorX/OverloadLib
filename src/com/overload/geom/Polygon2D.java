package com.overload.geom;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * Double precision polygon class.
 * @author Odell
 */
public class Polygon2D {
	
	protected volatile Point2D[] points = null;
	protected Object pntSync = new Object();
	protected Rectangle2D bounds = null;
	
	public Polygon2D(final Point2D[] points) {
		this.points = points;
	}
	
	public Point2D[] getPoints() {
		return points != null ? points : new Point2D[0];
	}
	
	public void setPoints(final Point2D[] points) {
		this.points = points;
		this.bounds = null;
	}
	
	public void addPoints(final Point2D... pnts) {
		if (pnts == null || pnts.length <= 0)
			return;
		if (points == null) {
			synchronized (pntSync) {
				points = pnts;
			}
		} else {
			Point2D[] newPnts = Arrays.copyOf(points, points.length + pnts.length);
			System.arraycopy(pnts, 0, newPnts, points.length, pnts.length);
			synchronized (pntSync) {
				points = newPnts;
			}
		}
		invalidate();
	}
	
	public Rectangle2D getBounds() {
		if (bounds == null) {
			if (points != null && points.length > 0) {
				Double minX = null, minY = null, maxX = null, maxY = null;
				synchronized (pntSync) {
					for (final Point2D p : points) {
						if (p == null)
							continue;
						minX = (minX == null) ? p.getX() : Math.min(minX, p.getX());
						minY = (minY == null) ? p.getY() : Math.min(minY, p.getY());
						maxX = (maxX == null) ? p.getX() : Math.max(maxX, p.getX());
						maxY = (maxY == null) ? p.getY() : Math.max(maxY, p.getY());
					}
				}
				bounds = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
			}
		}
		return bounds;
	}
	
	public void translate(final double x, final double y) {
		synchronized (pntSync) {
			for (final Point2D p : points) {
				if (p == null)
					continue;
				p.setLocation(p.getX() + x, p.getY() + y);
			}
		}
	}
	
	public void invalidate() {
		bounds = null;
	}
	
	public void reset() {
		synchronized (pntSync) {
			points = null;
		}
	}
	
	Vector2D[] createEdges() {
		if (points == null)
			return null;
		Vector2D[] vectors = new Vector2D[points.length];
		for (int i = 0; i < points.length; i++) {
			final Point2D p = points[i], p2 = (i + 1 >= points.length) ? points[0] : points[i + 1];
			vectors[i] = new Vector2D(p2.getX() - p.getX(), p2.getY() - p.getY());
		}
		return vectors;
	}
	
	/**
	 * 
	 * @author Odell
	 */
	public static class CollisionResult {
		
		private Vector2D rebound = null;
		private boolean intersects = true, willintersect = true;
		
		/**
		 * 
		 * @return
		 */
		public boolean intersects() {
			return intersects;
		}
		
		/**
		 * 
		 * @return
		 */
		public boolean willIntersect() {
			return willintersect;
		}
		
		/**
		 * 
		 * @return
		 */
		public Vector2D getRebound() {
			return rebound;
		}
		
	}
	
	public static CollisionResult polygonCollision(final Polygon2D poly1, final Vector2D poly1V, final Polygon2D poly2, final Vector2D poly2V) {
		if (poly1 == null || poly1V == null || poly2 == null || poly2V == null)
			return null;
		final CollisionResult cr = new CollisionResult();
		
		final Vector2D[] poly1E = poly1.createEdges(), poly2E = poly2.createEdges();
		double minIntervalDistance = Double.POSITIVE_INFINITY;
		Vector2D translationAxis = new Vector2D();
		@SuppressWarnings("unused")
		Vector2D edge;
		
		for (int edgeIndex = 0; edgeIndex < poly1E.length + poly2E.length; edgeIndex++) {
			if (edgeIndex < poly1E.length) {
				edge = poly1E[edgeIndex];
			} else {
				edge = poly2E[edgeIndex - poly1E.length ];
			}
			
			
			
		}
		
		if (cr.willintersect) 
			cr.rebound = translationAxis.multiply(minIntervalDistance);
		
		return cr;
	}
	
	@SuppressWarnings("unused")
	private static double[] projectPolygon(final Vector2D axis, final Polygon2D poly) {
		final Point2D[] points = poly.getPoints();
		if (points.length <= 0)
			return null;
		axis.normalize();
		double temp = axis.getDotProduct(points[0]), min = temp, max = temp;
		for (int i = 1; i < points.length; i++) {
			final Point2D p = points[i];
			temp = axis.getDotProduct(p);
			if (temp < min) {
				min = temp;
			} else if (temp > max) {
				max = temp;
			}
		}
		return new double[] { min, max };
	}
	
	@SuppressWarnings("unused")
	private static double getIntervalDistance(double min1, double max1, double min2, double max2) {
		return min1 < min2 ? min2 - max1 : min1 - max2;
	}
	
}