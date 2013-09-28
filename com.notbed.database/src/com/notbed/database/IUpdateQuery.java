/**
 *
 */
package com.notbed.database;

/**
 * @author Alexandru Bledea
 * @since Sep 27, 2013
 */
public interface IUpdateQuery extends IQuery, ISetQuery, IWhereQuery {

	/**
	 * @param property
	 */
	void setCurrentTimestamp(String property);
}
