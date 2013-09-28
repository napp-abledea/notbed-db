/**
 *
 */
package com.notbed.database.impl;

/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
enum LinkType {
	AND(" AND "),
	OR(" OR ");

	private final String string;

	/**
	 * @param string
	 */
	private LinkType(String string) {
		this.string = string;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return string;
	};
}
