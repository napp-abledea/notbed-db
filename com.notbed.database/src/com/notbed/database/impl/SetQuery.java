/**
 *
 */
package com.notbed.database.impl;

import java.util.HashMap;
import java.util.Map;

import com.notbed.database.ISetQuery;

/**
 * @author Alexandru Bledea
 * @since Sep 27, 2013
 */
abstract class SetQuery extends Query implements ISetQuery {

	protected final Map<String, Object> set = new HashMap();
	protected boolean allowOverride = true;

	/**
	 * @param dao
	 * @param prefix
	 */
	SetQuery(DAO dao, String prefix) {
		super(dao, prefix);
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
