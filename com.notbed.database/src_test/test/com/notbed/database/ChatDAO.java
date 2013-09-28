/**
 *
 */
package test.com.notbed.database;

import java.sql.SQLException;

import com.notbed.database.IConnectionProvider;
import com.notbed.database.impl.DAO;

/**
 * @author Alexandru Bledea
 * @since Sep 22, 2013
 */
public class ChatDAO extends DAO<ChatEntry> {

	/**
	 * @param connectionProvider
	 * @throws SQLException
	 */
	ChatDAO(IConnectionProvider connectionProvider) throws SQLException {
		super(ChatEntry.class, "chat", connectionProvider);
	}

}
