/**
 *
 */
package com.notbed.database.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.notbed.database.IUpdateQuery;

/**
 * @author Alexandru Bledea
 * @since Sep 28, 2013
 */
class UpdateQuery extends WhereQuery implements IUpdateQuery {

	private final Map<String, Object> commands = new HashMap();
	private final Map<String, Object> set = new HashMap();
	protected boolean allowOverride = true;

	/**
	 * @param dao
	 */
	UpdateQuery(DAO dao) {
		super(dao, "UPDATE ");
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.ISetQuery#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public final void set(String property, Object object) {
		dao.validateProperty(property);
		if (!allowOverride && set.containsKey(property)) {
			throw new IllegalStateException(String.format("%s was already set to %s", property, set.get(property)));
		}
		set.put(property, object);
	}

	/**
	 * @param sb
	 */
	private void addSetClause(StringBuilder sb) {
		if (set.isEmpty() && commands.isEmpty()) {
			throw new IllegalStateException("Empty Set Arguments!");
		}
		sb.append(" SET ");

		append(sb, set.entrySet(), !commands.isEmpty(), false);
		append(sb, commands.entrySet(), false, true);
	}

	/**
	 * @param sb
	 * @param entrySet
	 * @param moreToCome
	 * @param commands
	 */
	private void append(StringBuilder sb, Set<Entry<String, Object>> entrySet, boolean moreToCome, boolean commands) {
		if (!entrySet.isEmpty()) {
			boolean hasNext = true;
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();
			while (hasNext) {
				Entry<String, Object> entry = iterator.next();
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(commands ? (String) entry.getValue() : PLACEHOLDER);
				hasNext = iterator.hasNext();
				if (hasNext || moreToCome) {
					sb.append(",  ");
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.WhereQuery#setArguments(java.sql.PreparedStatement)
	 */
	@Override
	protected void setArguments(PreparedStatement ps) throws SQLException {
		addAll(ps, set.values(), args);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.WhereQuery#addToQuery(java.lang.StringBuilder)
	 */
	@Override
	protected void addToQuery(StringBuilder sb) {
		addSetClause(sb);
		super.addToQuery(sb);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IUpdateQuery#setCurrentTimestamp(java.lang.String)
	 */
	@Override
	public void setCurrentTimestamp(String property) {
		commands.put(property, "NOW()");
	}

	/**
	 * @return the allowOverride
	 */
	public final boolean isAllowOverride() {
		return allowOverride;
	}

	/**
	 * @param allowOverride the allowOverride to set
	 */
	public final void setAllowOverride(boolean allowOverride) {
		this.allowOverride = allowOverride;
	}

}
