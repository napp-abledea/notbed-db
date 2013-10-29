/**
 *
 */
package com.notbed.database.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.notbed.database.IConnectionProvider;
import com.notbed.database.IDAO;
import com.notbed.database.IDeleteQuery;
import com.notbed.database.IEntity;
import com.notbed.database.IFreeQuery;
import com.notbed.database.IInsertQuery;
import com.notbed.database.ISelectQuery;
import com.notbed.database.IUpdateQuery;
import com.notbed.util.NullTool;

/**
 * @author Alexandru Bledea
 * @since Sep 21, 2013
 */
public abstract class DAO<I extends IEntity> implements IDAO<I> {

	private final EntityMapper<I> entityMapper;
	private final String tableName;

	private final IConnectionProvider connectionProvider;

	/**
	 * @param clazz
	 * @param tableName
	 * @throws SQLException
	 */
	public DAO(Class<I> clazz, String tableName) throws SQLException {
		this(clazz, tableName, null);
	}

	/**
	 * @param entityClass
	 * @param tableName
	 * @param connectionProvider
	 * @throws SQLException
	 */
	public DAO(Class<I> entityClass, final String tableName, IConnectionProvider connectionProvider) throws SQLException {
		NullTool.forbidNull(entityClass, tableName);
		this.tableName = tableName;
		this.connectionProvider = connectionProvider;

		entityMapper = new EntityMapper(this, entityClass);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.DAO#select()
	 */
	@Override
	public final ISelectQuery<I> select() {
		return new SelectQuery(this);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IDAO#insert()
	 */
	@Override
	public final IInsertQuery insert() {
		return new InsertQuery(this);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IDAO#update()
	 */
	@Override
	public IUpdateQuery update() {
		return update((Integer) null);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IDAO#update(com.notbed.database.IEntity)
	 */
	@Override
	public IUpdateQuery update(I entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity is null");
		}
		return update(entity.getId());
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.IDAO#update(java.lang.Integer)
	 */
	@Override
	public IUpdateQuery update(Integer id) {
		UpdateQuery updateQuery = new UpdateQuery(this);
		if (id != null) {
			updateQuery.addEquals("id", id);
		}
		return updateQuery;
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.DAO#delete()
	 */
	@Override
	public final IDeleteQuery delete() {
		return new DeleteQuery(this);
	}

	/* (non-Javadoc)
	 * @see com.notbed.database.DAO#freeQuery()
	 */
	@Override
	public final IFreeQuery freeQuery() {
		return new FreeQuery(this);
	}

	/**
	 * @param property
	 */
	final void validateProperty(String property) {
		entityMapper.validate(property);
	}

	/**
	 * @return
	 */
	protected final Connection openConnection() {
		NullTool.forbidNull(connectionProvider);
		Connection connection = connectionProvider.getConnection();
		NullTool.forbidNull(connection);
		return connection;
	}

	/**
	 * @param con
	 */
	protected final void closeConnection(Connection con) {
		NullTool.forbidNull(connectionProvider);
		connectionProvider.close(con);
	}

	/**
	 * @return
	 */
	protected final String getTableName() {
		return tableName;
	}

	/**
	 * @return the entityMapper
	 */
	protected final EntityMapper<I> getEntityMapper() {
		return entityMapper;
	}

}
