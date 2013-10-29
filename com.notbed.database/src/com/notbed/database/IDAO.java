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
	 * @param id
	 * @return
	 */
	IUpdateQuery update(Integer id);

	/**
	 * @param entity
	 * @return
	 */
	IUpdateQuery update(I entity);

	/**
	 * @return
	 */
	IDeleteQuery delete();

	/**
	 * @return
	 */
	IFreeQuery freeQuery();
}
