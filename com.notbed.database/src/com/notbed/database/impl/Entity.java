/**
 *
 */
package com.notbed.database.impl;

import com.notbed.database.IEntity;

/**
 * @author Alexandru Bledea
 * @since Sep 22, 2013
 */
public class Entity implements IEntity {

	private int id;

	/* (non-Javadoc)
	 * @see com.notbed.database.IEntity#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IEntity#setId(int)
	 */
	@Override
	public void setId(int id) {
		this.id = id;
	}

}
