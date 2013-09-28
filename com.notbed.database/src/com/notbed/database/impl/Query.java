/**
 *
 */
package com.notbed.database.impl;

import com.notbed.database.IEntity;


/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
abstract class Query<I extends IEntity> extends BaseQuery<I> {

	private final String prefix;

	/**
	 * @param dao
	 * @param prefix
	 */
	Query(DAO<I> dao, String prefix) {
		super(dao);
		this.prefix = prefix;
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.Query#generate()
	 */
	@Override
	protected final String getSQL() {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(dao.getTableName());
		addToQuery(sb);
		return sb.toString();
	}

	/**
	 * @param sb
	 */
	protected abstract void addToQuery(StringBuilder sb);

}
