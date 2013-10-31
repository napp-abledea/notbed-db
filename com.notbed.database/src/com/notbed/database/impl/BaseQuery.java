/**
 *
 */
package com.notbed.database.impl;

import static com.notbed.util.UClose.CLOSE_STATEMENT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.notbed.database.IEntity;
import com.notbed.database.IExecutableQuery;
import com.notbed.util.NullTool;
import com.notbed.util.mass.IEvaluatorWithException;

/**
 * @author Alexandru Bledea
 * @since Sep 22, 2013
 */
abstract class BaseQuery<I extends IEntity> implements IExecutableQuery {

	private static PreparedStatementEvaluator<Boolean> BOOLEAN_RESULT = new PreparedStatementEvaluator<Boolean>() {

		@Override
		public Boolean evaluate(PreparedStatement object) throws SQLException {
			return object.execute();
		};
	};

	protected final Collection args = new ArrayList();
	protected final DAO<I> dao;

	/**
	 * @param dao
	 */
	BaseQuery(DAO dao) {
		NullTool.forbidNull(dao);
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IQuery#execute()
	 */
	@Override
	public final boolean execute() {
		return execute(BOOLEAN_RESULT);
	}

	/**
	 * @param evaluator
	 * @return
	 */
	protected final <O> O execute(PreparedStatementEvaluator<O> evaluator) {
		String sql = getSQL();
		NullTool.forbidNull(sql);

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dao.openConnection();
			NullTool.forbidNull(con);
//			System.out.println(sql);
			ps = con.prepareStatement(sql);
			setArguments(ps);

			return evaluator.evaluate(ps);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			CLOSE_STATEMENT.evaluate(ps);
			dao.closeConnection(con);
		}
	}

	/**
	 * @return
	 */
	protected abstract String getSQL();

	/**
	 * @param ps
	 * @throws SQLException
	 */
	protected abstract void setArguments(PreparedStatement ps) throws SQLException;

	/**
	 * @param ps
	 * @param collections
	 * @throws SQLException
	 */
	protected final void addAll(PreparedStatement ps, Collection... collections) throws SQLException {
		Collection bigone = new ArrayList();
		for (Collection col : collections) {
			bigone.addAll(col);
		}
		int i = 1;
		for (Object arg : bigone) {
			ps.setObject(i++, arg);
		}
	}

	/**
	 * @author Alexandru Bledea
	 * @since Sep 22, 2013
	 * @param <O>
	 */
	interface PreparedStatementEvaluator<O> extends IEvaluatorWithException<PreparedStatement, O> {

		/* (non-Javadoc)
		 * @see com.notbed.util.IEvaluatorWithException#evaluate(java.lang.Object)
		 */
		@Override
		public O evaluate(PreparedStatement object) throws Exception;
	}
}
