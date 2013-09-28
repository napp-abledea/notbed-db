/**
 *
 */
package com.notbed.database;

/**
 * @author Alexandru Bledea
 * @since Sep 25, 2013
 */
public interface IFreeQuery extends IQuery {

	/**
	 * @param sql
	 * @param args
	 */
	public abstract void setSQL(String sql, Object... args);

	/**
	 * @param args
	 */
	public abstract void setArgs(Object... args);

}