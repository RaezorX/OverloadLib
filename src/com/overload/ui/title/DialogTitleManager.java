package com.overload.ui.title;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Dynamically manage the title of dialogs.<br>
 * Temporary titles with timeouts.
 * @author Odell
 */
public abstract class DialogTitleManager {
	
	private final Dialog dialog;
	private String defaultTitle;
	
	private Timer timer = null;
	private ActionListener event = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			appendTitle(null);
		}
	};
	
	/**
	 * Initializes a new DialogTitleManager.
	 * @param dialog
	 * 		the dialog to control.
	 * @param defaultTitle
	 * 		the default title of this dialog.
	 */
	public DialogTitleManager(final Dialog dialog, final String defaultTitle) {
		this.dialog = dialog;
		setDefaultTitle(defaultTitle);
		timer = new Timer(0, event);
		timer.setRepeats(false);
	}
	
	/**
	 * Changes the default title of this dialog.</br>
	 * @param defaultTitle
	 * 		the new default title of the controlled dialog.
	 */
	public final void setDefaultTitle(final String defaultTitle) {
		setDefaultTitle(defaultTitle, false);
	}
	
	/**
	 * Changes the default title of this dialog.</br>
	 * This will automatically update the dialog itself,</br>
	 * if the modify boolean is true or as long as a temporary title hasn't been submitted.
	 * @param defaultTitle
	 * 		the new default title of the controlled dialog.
	 */
	public final void setDefaultTitle(final String defaultTitle, final boolean modify) {
		this.defaultTitle = defaultTitle;
		if (modify && (timer == null || !timer.isRunning()))
			reset();
	}
	
	private void stopTimer() {
		if (timer.isRunning())
			timer.stop();
	}
	
	private void restartTimer(int delay) {
		stopTimer();
		timer.setInitialDelay(delay);
		timer.restart();
	}
	
	/**
	 * Sets the title of the controlled dialog to the default title.
	 */
	public final void reset() {
		setTitle(defaultTitle);
	}
	
	/**
	 * Sets the title of the controlled dialog.</br>
	 * @param title
	 * 		the suggested title of the dialog.
	 */
	public final void setTitle(final String title) {
		if (dialog != null)
			dialog.setTitle(title);
		onTitleChanged(title);
	}
	
	/**
	 * Modifies given text to the default title of the controlled dialog.</b>
	 * @param append
	 * 		the text to append the default title.
	 */
	public final void appendTitle(final String append) {
		final StringBuilder build = new StringBuilder().append(defaultTitle);
		setTitle((append != null ? build.append(' ').append(append) : build).toString());
	}
	
	/**
	 * Submits a temporary title to the dialog.
	 * @param title
	 * 		the temporary title.
	 * @param period
	 * 		the time period in milliseconds for the temporary title to last.
	 */
	public final void submit(final String title, final int period) {
		setTitle(title);
		stopTimer();
		if (period > 0)
			restartTimer(period);
	}
	
	/**
	 * Submits temporary text to the title of the dialog.
	 * @param append
	 * 		the temporary append text.
	 * @param period
	 * 		the time period in milliseconds for the temporary text to last.
	 */
	public final void submitAppend(final String append, final int period) {
		appendTitle(append);
		stopTimer();
		if (period > 0)
			restartTimer(period);
	}
	
	/**
	 * Occurs when the title of this dialog changes due to this DialogTitleManager.<br>
	 * This will not listen for changes to the title done manually.
	 * @param title
	 * 		the new title of the controlled dialog.
	 */
	public abstract void onTitleChanged(final String title);
	
}