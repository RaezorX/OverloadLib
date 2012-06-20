package org.overload.media.screen;

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
	
}