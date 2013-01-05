package com.overload.media.screen;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Callback for the ScreenCapture class to output captured images.
 * @author Odell
 */
public interface CaptureListener {
	
	/**
	 * Occurs when a new image has been captured of the screen.
	 * @param buffImg
	 * 		the new captured screenshot.
	 */
	public void captureReceived(final BufferedImage buffImg);
	
	/**
	 * Informs the ScreenCapture what section of the screen it should provide this CaptureListener.
	 * @return a rectangle that specifies the section of the screen to capture.
	 */
	public Rectangle captureSize();
	
}