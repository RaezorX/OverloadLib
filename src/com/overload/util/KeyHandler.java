package com.overload.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Records the state of keys when they are pressed, including system time and key code.
 * @author Odell
 */
public class KeyHandler implements KeyListener {

	private HashMap<Integer, Key> keyMap;

	/**
	 * Creates a new KeyHandler object.
	 */
	public KeyHandler() {
		keyMap = new HashMap<Integer, Key>();
	}

	/**
	 * Determines the state of the given key.
	 * @param keyCode
	 * 		the key code which maps to the state of the key.
	 * @return if the key is pressed, a valid key object will be returned with the time the key was pressed, if the key isn't pressed, null will be returned.
	 */
	public final Key getKey(int keyCode) {
		return keyMap.get(keyCode);
	}

	public final void keyPressed(KeyEvent key) {
		Key last = keyMap.get(key.getKeyCode());
		if (last == null)
			keyMap.put(key.getKeyCode(), new Key(key.getKeyCode(), System.currentTimeMillis()));
		pressed(key);
	}

	public final void keyReleased(KeyEvent key) {
		keyMap.put(key.getKeyCode(), null);
		released(key);
	}

	public final void keyTyped(KeyEvent e) {
		typed(e);
	}

	public void pressed(KeyEvent key) {}
	public void released(KeyEvent key) {}
	public void typed(KeyEvent key) {}

	public class Key {

		private int code;
		private long time;

		public Key(int code, long time) {
			this.code = code;
			this.time = time;
		}

		public int getCode() {
			return code;
		}

		public long getTime() {
			return time;
		}

	}

}