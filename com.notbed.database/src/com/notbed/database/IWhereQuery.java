/**
 *
 */
package com.notbed.database;

/**
 * @author Alexandru Bledea
 * @since Sep 22, 2013
 */
public interface IWhereQuery {

	/**
	 * @param property
	 * @param value
	 */
	void addEquals(String property, Object value);

	/**
	 * @param property
	 * @param value
	 */
	void addOrEquals(String property, Object value);

	/**
	 * @param property
	 * @param value
	 */
	void addNotEquals(String property, Object value);

	/**
	 * @param property
	 * @param value
	 */
	void addOrNotEquals(String property, Object value);

	/**
	 * @param property
	 */
	void addNull(String property);

	/**
	 * @param property
	 */
	void addOrNull(String property);

	/**
	 * @param property
	 */
	void addNotNull(String property);

	/**
	 * @param property
	 */
	void addOrNotNull(String property);

	/**
	 * @param property
	 * @param value
	 */
	void addLike(String property, Object value);

	/**
	 * @param property
	 * @param value
	 */
	void addOrLike(String property, Object value);

	/**
	 * @param property
	 * @param value
	 */
	void addNotLike(String property, Object value);

	/**
	 * @param property
	 * @param value
	 */
	void addOrNotLike(String property, Object value);

}
