/**
 *
 */
package com.notbed.database;

/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
public interface IDAO<I extends IEntity> {

	/**
	 * @return
	 */
	ISelectQuery<I> select();

	/**
	 * @return
	 */
	IInsertQuery insert();

	/**
	 * @return
	 */
	IUpdateQuery update();

	/**
	 * @return
	 */
	IDeleteQuery delete();

	/**
	 * @return
	 */
	IFreeQuery freeQuery();
}
