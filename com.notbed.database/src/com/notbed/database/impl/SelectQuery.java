/**
 *
 */
package com.notbed.database.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.notbed.database.IEntity;
import com.notbed.database.ISelectQuery;
import com.notbed.util.CloseUtil;
import com.notbed.util.StringUtil;

/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
class SelectQuery<I extends IEntity> extends WhereQuery<I> implements ISelectQuery<I> {

	private final PreparedStatementEvaluator<Collection<I>> RESULTSET_RESULT = new PreparedStatementEvaluator<Collection<I>>() {

		@Override
		public Collection<I> evaluate(PreparedStatement object) throws Exception {
			ResultSet rs = null;
			try {
				rs = object.executeQuery();
				return createList(rs);
			} finally {
				CloseUtil.RESULT_SET.evaluate(rs);
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
		if (!StringUtil.empty(sortColumn)) {
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
	public Collection<I> executeQuery() {
		return execute(RESULTSET_RESULT);
	}

	/**
	 * @param executeQuery
	 * @return
	 * @throws Exception
	 */
	private Collection<I> createList(ResultSet executeQuery) throws Exception {
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

	/**
	 * @param property
	 * @param asc
	 */
	public void setOrderBy(String property, boolean asc) {
		sortColumn = property;
		this.asc = asc;
	}

}
