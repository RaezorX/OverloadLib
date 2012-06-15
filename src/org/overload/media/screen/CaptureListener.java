package org.overload.media.screen;

import java.awt.image.BufferedImage;

public interface CaptureListener {
	public void captureReceived(final BufferedImage buffImg);
}