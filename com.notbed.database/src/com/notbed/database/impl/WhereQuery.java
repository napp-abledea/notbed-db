/**
 *
 */
package com.notbed.database.impl;

import static com.notbed.database.impl.LinkType.AND;
import static com.notbed.database.impl.LinkType.OR;
import static com.notbed.database.impl.Type.EQUALS;
import static com.notbed.database.impl.Type.GREATER_EQUALS;
import static com.notbed.database.impl.Type.GREATER_THAN;
import static com.notbed.database.impl.Type.IS_NOT_NULL;
import static com.notbed.database.impl.Type.IS_NULL;
import static com.notbed.database.impl.Type.LESS_EQUALS;
import static com.notbed.database.impl.Type.LESS_THAN;
import static com.notbed.database.impl.Type.LIKE;
import static com.notbed.database.impl.Type.NOT_EQUALS;
import static com.notbed.database.impl.Type.NOT_LIKE;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.notbed.database.IEntity;
import com.notbed.database.IWhereQuery;
import com.notbed.util.NullTool;

/**
 * @author Alexandru Bledea
 * @since Sep 22, 2013
 */
class WhereQuery<I extends IEntity> extends Query<I> implements IWhereQuery {

	private final Collection<Element> whereCondition = new ArrayList<Element>();

	/**
	 * @param dao
	 * @param prefix
	 */
	WhereQuery(DAO<I> dao, String prefix) {
		super(dao, prefix);
	}

	/**
	 * @param property
	 * @param object
	 */
	@Override
	public void addEquals(String property, Object object) {
		and(property, EQUALS, object);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrEquals(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addOrEquals(String property, Object value) {
		or(property, EQUALS, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addNotEquals(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addNotEquals(String property, Object object) {
		and(property, NOT_EQUALS, object);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrNotEquals(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addOrNotEquals(String property, Object value) {
		or(property, NOT_EQUALS, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addNull(java.lang.String)
	 */
	@Override
	public void addNull(String property) {
		and(property, IS_NULL, null);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrNull(java.lang.String)
	 */
	@Override
	public void addOrNull(String property) {
		or(property, IS_NULL, null);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addNotNull(java.lang.String)
	 */
	@Override
	public void addNotNull(String property) {
		and(property, IS_NOT_NULL, null);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrNotNull(java.lang.String)
	 */
	@Override
	public void addOrNotNull(String property) {
		or(property, IS_NOT_NULL, null);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.Query#addToQuery(java.lang.StringBuilder)
	 */
	@Override
	protected void addToQuery(StringBuilder sb) {
		addWhereClause(sb);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addLike(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addLike(String property, Object value) {
		and(property, LIKE, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrLike(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addOrLike(String property, Object value) {
		or(property, LIKE, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addNotLike(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addNotLike(String property, Object value) {
		and(property, NOT_LIKE, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrNotLike(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addOrNotLike(String property, Object value) {
		or(property, NOT_LIKE, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addGreaterThan(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addGreaterThan(String property, Object value) {
		and(property, GREATER_THAN, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrGreaterThan(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addOrGreaterThan(String property, Object value) {
		or(property, GREATER_THAN, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addLessThan(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addLessThan(String property, Object value) {
		and(property, LESS_THAN, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrLessThan(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addOrLessThan(String property, Object value) {
		or(property, LESS_THAN, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addGreaterEquals(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addGreaterEquals(String property, Object value) {
		and(property, GREATER_EQUALS, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrGreaterEquals(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addOrGreaterEquals(String property, Object value) {
		or(property, GREATER_EQUALS, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addLessEquals(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addLessEquals(String property, Object value) {
		and(property, LESS_EQUALS, value);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IWhereQuery#addOrLessEquals(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addOrLessEquals(String property, Object value) {
		or(property, LESS_EQUALS, value);
	}

	/**
	 * @param property
	 * @param lt
	 * @param t
	 * @param object
	 */
	private void aux(String property, LinkType lt, Type t, Object object) {
		whereCondition.add(new Element(property, lt, t, object));
	}

	/**
	 * @param property
	 * @param t
	 * @param object
	 */
	private void and(String property, Type t, Object object) {
		aux(property, AND, t, object);
	}

	/**
	 * @param property
	 * @param t
	 * @param object
	 */
	private void or(String property, Type t, Object object) {
		aux(property, OR, t, object);
	}

	/**
	 * @param sb
	 * @return
	 */
	protected final StringBuilder addWhereClause(StringBuilder sb) {
		this.args.clear();
		if (!whereCondition.isEmpty()) {
			sb.append(" WHERE ");
			boolean atLeastOne = false;
			for (Element element : whereCondition) {
				element.populate(sb, atLeastOne);
				atLeastOne = true;
			}
		}
		return sb;
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.impl.BaseQuery#setArguments(java.sql.PreparedStatement)
	 */
	@Override
	protected void setArguments(PreparedStatement ps) throws SQLException {
		addAll(ps, args);
	}

	/**
	 * @author Alexandru Bledea
	 * @since Sep 21, 2013
	 */
	private class Element {

		private final Object object;
		private final Type op;
		private final LinkType link;
		private final String property;

		/**
		 * @param property
		 * @param link
		 * @param op
		 * @param object
		 */
		Element(String property, LinkType link, Type op, Object object) {
			NullTool.forbidNull(property, op, link);

			dao.validateProperty(property);

			if (object == null) {
				switch (op) {
					case EQUALS:
						op = IS_NULL;
						break;
					case NOT_EQUALS:
						op = IS_NOT_NULL;
						break;
				}
			}

			this.property = property;
			this.link = link;
			this.op = op;
			this.object = object;
		}

		/**
		 * @param sb
		 * @return
		 */
		public void populate(StringBuilder sb, boolean addLink) {
			if (addLink) {
				sb.append(link);
			}
			sb.append(property).append(op);
			if (object != null) {
				args.add(object);
				addPlaceHolder(sb);
			}
		}

		/**
		 * @param sb
		 */
		private void addPlaceHolder(StringBuilder sb) {
			sb.append(PLACEHOLDER);
		}
	}

}
