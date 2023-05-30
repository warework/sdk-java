package com.warework.service.datastore.client;

import java.sql.Types;

import com.warework.core.NullType;

/**
 * Identifies generic SQL types for <code>null</code> values.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class JdbcNullType extends NullType {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines a null value for the generic SQL type BIGINT.
	 */
	public static final NullType BIGINT = new JdbcNullType(Types.BIGINT);

	/**
	 * Constant that defines a null value for the generic SQL type BINARY.
	 */
	public static final NullType BINARY = new JdbcNullType(Types.BINARY);

	/**
	 * Constant that defines a null value for the generic SQL type BIT.
	 */
	public static final NullType BIT = new JdbcNullType(Types.BIT);

	/**
	 * Constant that defines a null value for the generic SQL type CHAR.
	 */
	public static final NullType CHAR = new JdbcNullType(Types.CHAR);

	/**
	 * Constant that defines a null value for the generic SQL type DATE.
	 */
	public static final NullType DATE = new JdbcNullType(Types.DATE);

	/**
	 * Constant that defines a null value for the generic SQL type DECIMAL.
	 */
	public static final NullType DECIMAL = new JdbcNullType(Types.DECIMAL);

	/**
	 * Constant that defines a null value for the generic SQL type DOUBLE.
	 */
	public static final NullType DOUBLE = new JdbcNullType(Types.DOUBLE);

	/**
	 * Constant that defines a null value for the generic SQL type FLOAT.
	 */
	public static final NullType FLOAT = new JdbcNullType(Types.FLOAT);

	/**
	 * Constant that defines a null value for the generic SQL type INTEGER.
	 */
	public static final NullType INTEGER = new JdbcNullType(Types.INTEGER);

	/**
	 * Constant that defines a null value for the generic SQL type
	 * LONGVARBINARY.
	 */
	public static final NullType LONGVARBINARY = new JdbcNullType(
			Types.LONGVARBINARY);

	/**
	 * Constant that defines a null value for the generic SQL type LONGVARCHAR.
	 */
	public static final NullType LONGVARCHAR = new JdbcNullType(
			Types.LONGVARCHAR);

	/**
	 * Constant that defines a null value for the generic SQL type NUMERIC.
	 */
	public static final NullType NUMERIC = new JdbcNullType(Types.NUMERIC);

	/**
	 * Constant that defines a null value for the generic SQL type REAL.
	 */
	public static final NullType REAL = new JdbcNullType(Types.REAL);

	/**
	 * Constant that defines a null value for the generic SQL type SMALLINT.
	 */
	public static final NullType SMALLINT = new JdbcNullType(Types.SMALLINT);

	/**
	 * Constant that defines a null value for the generic SQL type TIME.
	 */
	public static final NullType TIME = new JdbcNullType(Types.TIME);

	/**
	 * Constant that defines a null value for the generic SQL type TIMESTAMP.
	 */
	public static final NullType TIMESTAMP = new JdbcNullType(Types.TIMESTAMP);

	/**
	 * Constant that defines a null value for the generic SQL type TINYINT.
	 */
	public static final NullType TINYINT = new JdbcNullType(Types.TINYINT);

	/**
	 * Constant that defines a null value for the generic SQL type VARBINARY.
	 */
	public static final NullType VARBINARY = new JdbcNullType(Types.VARBINARY);

	/**
	 * Constant that defines a null value for the generic SQL type VARCHAR.
	 */
	public static final NullType VARCHAR = new JdbcNullType(Types.VARCHAR);

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Defines a null value for a specific SQL data type.
	 * 
	 * @param type
	 *            Specific SQL type defined at <code>java.sql.Types</code>.<br>
	 * <br>
	 */
	public JdbcNullType(final int type) {
		super(type);
	}

}
