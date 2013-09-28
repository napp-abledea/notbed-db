/**
 *
 */
package com.notbed.database;

import java.sql.Connection;

/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
public interface IConnectionProvider {

	/**
	 *
	 */
	Connection getConnection();

	/**
	 * @param con
	 */
	void close(Connection con);
}
