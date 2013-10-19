/**
 *
 */
package com.notbed.database.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.notbed.database.IInsertQuery;
import com.notbed.util.UString;

/**
 * @author Alexandru Bledea
 * @since Sep 27, 2013
 */
class InsertQuery extends SetQuery implements IInsertQuery {

	/**
	 * @param dao
	 */
	InsertQuery(DAO dao) {
		super(dao, "INSERT INTO ");
	}

	/**
	 * @param sb
	 */
	private void addSetClause(StringBuilder sb) {
		if (set.isEmpty()) {
			throw new IllegalStateException("Empty Set Arguments!");
		}
		String table = UString.join(set.keySet(), ", ");
		sb.append(pad(table)).append(" VALUES ");
		String elem = UString.join(PLACEHOLDER, set.size(), ", ");
		sb.append(pad(elem));
	}

	/**
	 * @param s
	 * @return
	 */
	private String pad(String s) {
		return UString.pad(s, "(", ")");
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.Query#addToQuery(java.lang.StringBuilder)
	 */
	@Override
	protected void addToQuery(StringBuilder sb) {
		addSetClause(sb);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.WhereQuery#setArguments(java.sql.PreparedStatement)
	 */
	@Override
	protected void setArguments(PreparedStatement ps) throws SQLException {
		addAll(ps, set.values());
	}

}
