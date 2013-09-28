/**
 *
 */
package com.notbed.database.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import com.notbed.database.IFreeQuery;

/**
 * @author Alexandru Bledea
 * @since Sep 22, 2013
 */
class FreeQuery extends BaseQuery implements IFreeQuery {

	/**
	 * @param dao
	 */
	FreeQuery(DAO dao) {
		super(dao);
	}

	private String sql;

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.IFreeQuery#setSQL(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setSQL(String sql, Object... args) {
		this.sql = sql;
		setArgs(args);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.IFreeQuery#setArgs(java.lang.Object)
	 */
	@Override
	public void setArgs(Object... args) {
		this.args.clear();
		this.args.addAll(Arrays.asList(args));
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.BaseQuery#getSQL()
	 */
	@Override
	protected String getSQL() {
		return sql;
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.BaseQuery#setArguments(java.sql.PreparedStatement)
	 */
	@Override
	protected void setArguments(PreparedStatement ps) throws SQLException {
		addAll(ps, args);
	}

}
