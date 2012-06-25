package org.overload.media.screen;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * A high performance screen capture utility.
 * @author Odell
 */
public class ScreenCapture {
	
	/**
	 * The size of the default display.
	 */
	public final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * The size of the default display as a Rectangle.
	 */
	public final static Rectangle SCREEN_RECT = new Rectangle(SCREEN_SIZE);
	
	/**
	 * The screen resolution in dots-per-inch.
	 */
	public final static int SCREEN_RESOLUTION = Toolkit.getDefaultToolkit().getScreenResolution();
	
	private static long threadCount = 0;
	
	private final Robot r;
	private final CopyOnWriteArraySet<CaptureListener> listeners;
	private CaptureThread ct = null;
	private Object threadObj = new Object();
	
	/**
	 * Creates a new ScreenCapture object.
	 * @throws AWTException
	 */
	public ScreenCapture() throws AWTException {
		r = new Robot();
		listeners = new CopyOnWriteArraySet<CaptureListener>();
	}
	
	/**
	 * Creates a capture of the entire screen.
	 * @return a buffered image rendering of the screen.
	 */
	public BufferedImage createCapture() {
		return createCapture(SCREEN_RECT);
	}
	
	/**
	 * Creates a capture of the screen in the given rectangle.
	 * @param rect
	 * 		the rectangle marking the part of the screen to capture.
	 * @return a buffered image rendering of the screen.
	 */
	public BufferedImage createCapture(final Rectangle rect) {
		return r.createScreenCapture(rect);
	}
	
	/**
	 * Adds a capture listener to listen for new captured images.
	 * @param cl
	 * 		the capture listener to add.
	 */
	public void addCaptureListener(final CaptureListener cl) {
		if (listeners.size() == 0 && listeners.add(cl)) {
			if (ct != null) {
				ct.paused = false;
				threadObj.notify();
			} else {
				ct = new CaptureThread();
				ct.start();
			}
		}
	}
	
	/**
	 * Removes a capture listener from this screen capture object.
	 * @param cl
	 * 		the capture listener to remove.
	 */
	public void removeCaptureListener(final CaptureListener cl) {
		if (listeners.size() == 0)
			return;
		if (listeners.remove(cl) && listeners.size() == 0)
			ct.paused = true;
	}
	
	private class CaptureThread extends Thread {
		
		private final ThreadGroup listenerGroup;
		private boolean paused = false;
		
		public CaptureThread() {
			super("ScreenCapture#CaptureThread" + threadCount++);
			setDaemon(true);
			setPriority(Thread.MAX_PRIORITY);
			listenerGroup = new ThreadGroup(getName() + "_Group");
		}
		
		@Override
		public void run() {
			while (!isInterrupted()) {
				try {
					if (paused) {
						paused = false;
						threadObj.wait();
					}
					// find rectangle to capture
					final Iterator<CaptureListener> rTemp = listeners.iterator();
					int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
					while (rTemp.hasNext()) {
						final CaptureListener tempCL = rTemp.next();
						final Rectangle tempRect = tempCL.captureSize();
						minX = Math.min(minX, tempRect.x);
						minY = Math.min(minY, tempRect.y);
						maxX = Math.max(maxX, tempRect.width + minX);
						maxY = Math.max(maxY, tempRect.height + minY);
					}
					final Rectangle minRect = new Rectangle(minX, minY, maxX - minX, maxY - minY);
					
					final BufferedImage bi = createCapture(minRect);
					final Iterator<CaptureListener> temp = listeners.iterator();
					int i = 0;
					while (temp.hasNext()) {
						final CaptureListener tempCL = temp.next();
						new Thread(listenerGroup, new Runnable() {
							public void run() {
								final Rectangle targetSize = tempCL.captureSize();
								tempCL.captureReceived(bi.getSubimage(targetSize.x - minRect.x, targetSize.y - minRect.y, targetSize.width, targetSize.height));
							}
						}, listenerGroup.getName() + "_T" + i++).start();
					}
					
					Thread.yield();
				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}