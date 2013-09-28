/**
 *
 */
package com.notbed.database;

/**
 * @author Alexandru Bledea
 * @since Sep 22, 2013
 */
public interface IQuery {

	public static final String PLACEHOLDER = "?";

	/**
	 *
	 */
	void execute();

}
