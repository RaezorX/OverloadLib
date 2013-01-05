package com.overload.markup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * A custom made XML-like markup tag.<br>
 * Format: {@code <name key="value" key="value">post }
 * @author Odell
 */
public class Tag {
	
	protected final String raw;
	protected final HashMap<String, String> attributes;
	protected String name = null, post = null;
	protected int length;
	protected boolean parsed = false;
	
	/**
	 * Creates an unparsed tag with raw content.
	 * @param raw
	 * 		the raw content of this Tag.
	 */
	public Tag(final String raw) {
		this.raw = raw;
		this.attributes = new HashMap<String, String>();
	}
	
	/**
	 * @return the name of this tag.
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * @return the count of attributes of this tag.
	 */
	public final int getCount() {
		return attributes.size();
	}
	
	/**
	 * Finds the attribute of this tag from the key.
	 * @param key
	 * 		the key mapped to the value.
	 * @return the attribute retrieved from the key.
	 */
	public final String getAttribute(final String key) {
		return attributes.get(key);
	}
	
	/**
	 * @return the length of this tag not including the post data.
	 */
	public final int getLength() {
		return length;
	}
	
	/**
	 * @return the raw tag string.
	 */
	public final String getRaw() {
		return raw;
	}
	
	/**
	 * Some Tags have data which is after the attributes, such as Tags that contain message info.</br>
	 * This method provides access to any post data which exists in this Tag.
	 * @return any post data that exists in this Tag, may return null.
	 */
	public final String getPost() {
		return post;
	}
	
	/**
	 * @return whether this tag has been parsed.
	 */
	public boolean isParsed() {
		return parsed;
	}
	
	/**
	 * Parses this tag using the raw data.
	 * @return this tag after parsing.
	 */
	public Tag parse() {
		if (raw.charAt(0) != '<')
			return null;
		final StringBuilder build = new StringBuilder();
		boolean noQuote = true;
		for (int m = 1;; m++) {
			if (m >= raw.length())
				return null;
			final char curr = raw.charAt(m);
			if (curr == '"') {
				noQuote = !noQuote;
			} else if (noQuote) {
				if (curr == ' ') {
					resolveBuilder(build);
					build.delete(0, build.length());
					continue;
				} else if (curr == '>') {
					resolveBuilder(build);
					length = m + 1;
					break;
				}
			}
			build.append(curr);
		}
		if (length < raw.length())
			post = raw.substring(length);
		parsed = true;
		return this;
	}
	
	private void resolveBuilder(final StringBuilder build) {
		final String[] data = build.toString().split(Pattern.quote("="), 2);
		if (data.length < 2) {
			name = data[0];
		} else {
			attributes.put(data[0], data[1].substring(1, data[1].length() - 1));
		}
	}
	
	/**
	 * Parses a new Tag from the given data.
	 * @param data
	 * 		the data to parse.
	 * @return a new parsed Tag object.
	 */
	public static Tag parse(final String data) {
		return new Tag(data).parse();
	}
	
	/**
	 * Creates a String representation of a Tag given the arguments.
	 * @param type - the type of the Tag.
	 * @param tags - tag data in the form of { "key", "value", "key", "value" }.
	 * @return a String representation of the Tag described.
	 */
	public static String constructString(Object type, Object... tags) {
		final StringBuilder build = new StringBuilder();
		build.append('<');
		build.append(type.toString());
		boolean value = false;
		for (int i = 0; i < tags.length; i++) {
			build.append(value ? '=' : ' ');
			if (value)
				build.append('"');
			build.append(tags[i]);
			if (value)
				build.append('"');
			value = !value;
		}
		build.append('>');
		return build.toString();
	}
	
	/**
	 * Creates a Tag object with the raw text from {@link #constructString(Object, Object...)}.</br>
	 * If the length of the tags aren't even, then the last element is appended as post data.
	 * @param type - the type of the Tag.
	 * @param tags - tag data in the form of { "key", "value", "key", "value" }.
	 * @return a Tag object from raw text formed from the arguments.
	 */
	public static Tag construct(Object type, Object... tags) {
		if ((tags.length % 2) == 0) {
			return new Tag(constructString(type, tags));
		} else {
			Object[] stags = Arrays.copyOf(tags, tags.length - 1);
			return new Tag(constructString(type, stags) + tags[tags.length - 1]);
		}
	}
	
	@Override
	public String toString() {
		return raw;
	}
	
}