/**
 *
 */
package com.notbed.database.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.notbed.database.IEntity;
import com.notbed.database.ISelectQuery;
import com.notbed.util.UClose;
import com.notbed.util.UString;

/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
class SelectQuery<I extends IEntity> extends WhereQuery<I> implements ISelectQuery<I> {

	private final PreparedStatementEvaluator<List<I>> RESULTSET_RESULT = new PreparedStatementEvaluator<List<I>>() {

		@Override
		public List<I> evaluate(PreparedStatement object) throws Exception {
			ResultSet rs = null;
			try {
				rs = object.executeQuery();
				return createList(rs);
			} finally {
				UClose.RESULT_SET.evaluate(rs);
			}
		};

	};

	private int limitStart;
	private int limitMax = 15;
	private boolean asc;
	private String sortColumn;

	/**
	 * @param dao
	 * @param entityMapper
	 */
	SelectQuery(DAO<I> dao) {
		super(dao, "SELECT * FROM ");
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.WhereQuery#addToQuery(java.lang.StringBuilder)
	 */
	@Override
	protected void addToQuery(StringBuilder sb) {
		super.addToQuery(sb);
		addOrder(sb);
		addLimit(sb);
	}

	/**
	 * @param sb
	 */
	private void addOrder(StringBuilder sb){
		if (!UString.empty(sortColumn)) {
			sb.append(" ORDER BY ").append(sortColumn).append(" ").append(asc ? "ASC" : "DESC");
		}
	}

	/**
	 * @param sb
	 */
	private void addLimit(StringBuilder sb) {
		sb.append(" LIMIT ").append(limitStart).append(", ").append(limitMax);
	}

	/* (non-Javadoc)
	* @see com.notbed.database.IQuery#executeQuery()
	*/
	@Override
	public List<I> executeQuery() {
		return execute(RESULTSET_RESULT);
	}

	/**
	 * @param executeQuery
	 * @return
	 * @throws Exception
	 */
	private List<I> createList(ResultSet executeQuery) throws Exception {
		List<I> list = new ArrayList();
		int columnCount = executeQuery.getMetaData().getColumnCount();
		EntityMapper<I> entityMapper = dao.getEntityMapper();
		while (executeQuery.next()) {
			list.add(entityMapper.mapEntity(executeQuery, columnCount));
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.ISelectQuery#setLimit(int)
	 */
	@Override
	public void setLimit(int max) {
		setLimit(0, max);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.ISelectQuery#setLimit(int, int)
	 */
	@Override
	public void setLimit(int start, int max) {
		this.limitStart = start;
		this.limitMax = max;
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.ISelectQuery#setOrderBy(java.lang.String, boolean)
	 */
	@Override
	public void setOrderBy(String property, boolean asc) {
		sortColumn = property;
		this.asc = asc;
	}

}
