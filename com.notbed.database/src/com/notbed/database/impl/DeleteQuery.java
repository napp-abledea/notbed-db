/**
 *
 */
package com.notbed.database.impl;

import com.notbed.database.IDeleteQuery;

/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
class DeleteQuery extends WhereQuery implements IDeleteQuery {

	/**
	 * @param dao
	 */
	DeleteQuery(DAO dao) {
		super(dao, "DELETE FROM ");
	}

}
