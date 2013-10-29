/**
 *
 */
package com.notbed.database;

import java.util.List;

/**
 * @author Alexandru Bledea
 * @since Sep 25, 2013
 */
public interface ISelectQuery<I extends IEntity> extends IQuery, IWhereQuery {

	/**
	 * @param max
	 */
	void setLimit(int max);

	/**
	 * @param start
	 * @param max
	 */
	void setLimit(int start, int max);

	/**
	 * @param property
	 * @param asc
	 */
	void setOrderBy(String property, boolean asc);

	/**
	 * @return
	 */
	List<I> executeQuery();
}
