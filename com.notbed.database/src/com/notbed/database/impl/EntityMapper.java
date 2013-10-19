/**
 *
 */
package com.notbed.database.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.notbed.database.IEntity;
import com.notbed.util.UClose;
import com.notbed.util.NullTool;

/**
 * @author Alexandru Bledea
 * @since Sep 25, 2013
 */
class EntityMapper<I extends IEntity> {

	private static final String QUERY = "SELECT * FROM %s WHERE 0 = 1;";

	private final Class<I> entityClass;
	private final List<Method> setters;

	private final Collection<String> validFields;

	/**
	 * @param entityClass
	 * @throws SQLException
	 */
	EntityMapper(DAO<I> dao, Class<I> entityClass) throws SQLException {
		this.entityClass = entityClass;

		Map<String, Method> setters2 = getClassSetters(entityClass);
		validFields = setters2.keySet();
		ResultSetMetaData tableMetaData = getTableMetaData(dao.openConnection(), dao.getTableName());
		setters = getSetters(setters2, tableMetaData);
	}

	/**
	 * @param rs
	 * @param columnCount
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public I mapEntity(ResultSet rs, int columnCount) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SQLException {
		I entity = entityClass.newInstance();
		for (int i = 1; i <= columnCount; i++) {
			Method method = setters.get(i);
			if (method != null) {
				method.invoke(entity, rs.getObject(i));
			}
		}
		return entity;
	}

	/**
	 * @param property
	 * @return
	 */
	public void validate(String property) {
		NullTool.forbidNull(property);
		property = property.toUpperCase();
		boolean validField = validFields.contains(property);
		if (!validField) {
			throw new IllegalStateException("No such field!");
		}
	}

	/**
	 * @param clazz
	 * @return
	 */
	private Map<String, Method> getClassSetters(Class<I> clazz) {
		try {
			Map<String, Method> map = new HashMap();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String property = propertyDescriptor.getName().toUpperCase();
				if (map.containsKey(property)) {
					throw new IllegalStateException(String.format("%s mapped twice. DAOs are case insensitive.", property));
				}
				map.put(property, propertyDescriptor.getWriteMethod());
			}
			return map;
		} catch (IntrospectionException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @param con
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private ResultSetMetaData getTableMetaData(Connection con, String tableName) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(String.format(QUERY, tableName));
			return rs.getMetaData();
		} finally {
			UClose.RESULT_SET.evaluate(rs);
			UClose.STATEMENT.evaluate(stmt);
		}
	}

	/**
	 * @param setters
	 * @param tableMetaData
	 * @return
	 * @throws SQLException
	 */
	private List<Method> getSetters(Map<String, Method> setters, ResultSetMetaData tableMetaData) throws SQLException {
		List<Method> methods = new ArrayList();
		methods.add(null);

		int columnCount = tableMetaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			String columnName = tableMetaData.getColumnName(i);
			Method method = null;
			if (setters.containsKey(columnName)) {
				method = setters.get(columnName);
			}
			methods.add(method);
		}

		return methods;
	}
}
