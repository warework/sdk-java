package com.warework.core.util.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.warework.core.util.helper.ReflectionL2Helper;

/**
 * 
 * @author jschiaffino
 *
 * @param <T>
 */
public final class ComparableBean<T> implements Comparable<ComparableBean<T>> {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Bean to wrap, used to compare with another bean.
	private T bean1;

	// Order clause of the query.
	private OrderBy orderBy;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a wrapper for a bean so it can be compared with another bean.
	 * 
	 * @param bean    Object/bean to wrap.<br>
	 *                <br>
	 * @param orderBy Order clause of the query.<br>
	 *                <br>
	 */
	public ComparableBean(final T bean, final OrderBy orderBy) {

		// Set the bean.
		this.bean1 = bean;

		// Set the order clause of the query.
		this.orderBy = orderBy;

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the value of the wrapped bean.
	 * 
	 * @return Wrapped bean.<br>
	 *         <br>
	 */
	public T getBean() {
		return bean1;
	}

	/**
	 * Compares the values of the attrbiutes of the wrapped bean with the values of
	 * the attrbiutes of another bean.
	 * 
	 * @param bean2 Bean to compare with the wrapped bean.<br>
	 *              <br>
	 */
	public int compareTo(final ComparableBean<T> bean2) {
		try {

			// Compare each attribute of both beans.
			for (int index = 0; index < orderBy.size(); index++) {

				// Get the attribute to compare.
				final String attribute = orderBy.getAttribute(index);

				// Get the value of the attribute in the first bean.
				final Object value1 = ReflectionL2Helper.getAttributeValue(bean1, attribute);

				// Get the value of the attribute in the second bean.
				final Object value2 = ReflectionL2Helper.getAttributeValue(bean2.getBean(), attribute);

				// Validate null values.
				if ((value1 == null) && (value2 == null)) {
					continue;
				} else if (value1 == null) {
					return -1;
				} else if (value2 == null) {
					return 1;
				}

				// Compare values.
				int result = -1;
				if (value1 instanceof Boolean) {
					result = ((Boolean) value1).compareTo((Boolean) value2);
				} else if (value1 instanceof Byte) {
					result = ((Byte) value1).compareTo((Byte) value2);
				} else if (value1 instanceof Short) {
					result = ((Short) value1).compareTo((Short) value2);
				} else if (value1 instanceof Integer) {
					result = ((Integer) value1).compareTo((Integer) value2);
				} else if (value1 instanceof Long) {
					result = ((Long) value1).compareTo((Long) value2);
				} else if (value1 instanceof Float) {
					result = ((Float) value1).compareTo((Float) value2);
				} else if (value1 instanceof Double) {
					result = ((Double) value1).compareTo((Double) value2);
				} else if (value1 instanceof BigDecimal) {
					result = ((BigDecimal) value1).compareTo((BigDecimal) value2);
				} else if (value1 instanceof BigInteger) {
					result = ((BigInteger) value1).compareTo((BigInteger) value2);
				} else if (value1 instanceof Character) {
					result = ((Character) value1).compareTo((Character) value2);
				} else if (value1 instanceof String) {
					result = ((String) value1).compareTo((String) value2);
				} else if (value1 instanceof Date) {
					result = ((Date) value1).compareTo((Date) value2);
				} else {
					throw new ClassCastException(
							"WAREWORK cannot sort the result of the query because the type of the attribute '"
									+ attribute + "' is '" + value1.getClass().getName()
									+ "' and it is not supported.");
				}

				// Return result only if both values are not equal.
				if (result != 0) {

					// Get the order type.
					final String orderType = orderBy.getOrderType(index);

					// Return result.
					if (orderType.equals(OrderBy.KEYWORD_ASCENDING)) {
						return result;
					} else {
						return -result;
					}

				}

			}

			// At this point, both objects are equal.
			return 0;

		} catch (Exception e) {
			throw new ClassCastException(
					"WAREWORK cannot sort the result of the query because the value of an attribute throws the following exception when it is accessed: "
							+ e.getMessage());
		}
	}

}
