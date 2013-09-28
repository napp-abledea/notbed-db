/**
 *
 */
package com.notbed.database.impl;

/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
enum Type {
	EQUALS(" = "),
	GREATER_THAN(" > "),
	LESS_THAN(" < "),
	GREATER_EQUALS(" >= "),
	LESS_EQUALS(" <= "),
	NOT_EQUALS(" <>"),
	LIKE(" like "),
	NOT_LIKE(" not like "),
	IS_NULL(" is null"),
	IS_NOT_NULL(" is not null");

	private final String string;

	/**
	 * @param string
	 */
	private Type(String string) {
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
