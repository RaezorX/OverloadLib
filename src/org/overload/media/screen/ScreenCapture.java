package org.overload.media.screen;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public class ScreenCapture {
	
	public final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public final static Rectangle SCREEN_RECT = new Rectangle(SCREEN_SIZE);
	public final static int SCREEN_RESOLUTION = Toolkit.getDefaultToolkit().getScreenResolution();
	private static long threadCount = 0;
	
	private final Robot r;
	private final CopyOnWriteArraySet<CaptureListener> listeners;
	private CaptureThread ct = null;
	private Object threadObj = new Object();
	
	public ScreenCapture() throws AWTException {
		r = new Robot();
		listeners = new CopyOnWriteArraySet<CaptureListener>();
	}
	
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
	
	public void removeCaptureListener(final CaptureListener cl) {
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
					
					final BufferedImage bi = r.createScreenCapture(SCREEN_RECT);
					final Iterator<CaptureListener> temp = listeners.iterator();
					int i = 0;
					while (temp.hasNext()) {
						final CaptureListener tempCL = temp.next();
						new Thread(listenerGroup, new Runnable() {
							public void run() {
								tempCL.captureReceived(bi);
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