package com.warework.loader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.warework.core.loader.AbstractSaxLoader;
import com.warework.core.util.data.OrderBy;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.service.datastore.query.oo.AbstractExpression;
import com.warework.service.datastore.query.oo.And;
import com.warework.service.datastore.query.oo.Not;
import com.warework.service.datastore.query.oo.Operator;
import com.warework.service.datastore.query.oo.Or;
import com.warework.service.datastore.query.oo.Query;
import com.warework.service.datastore.query.oo.Where;

/**
 * Loads an XML file that represents an object-query and parses its content.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class ObjectQueryXmlLoader extends AbstractSaxLoader {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// XML ELEMENT NAMES

	protected static final String ELEMENT_AND = "and";

	protected static final String ELEMENT_ASC = "asc";

	protected static final String ELEMENT_ATTRIBUTE = "attribute";

	protected static final String ELEMENT_DESC = "desc";

	protected static final String ELEMENT_EXPRESSION = "expression";

	protected static final String ELEMENT_FORMAT = "format";

	protected static final String ELEMENT_LOCALE = "locale";

	protected static final String ELEMENT_NOT = "not";

	protected static final String ELEMENT_NUMBER = "number";

	protected static final String ELEMENT_OPERATOR = "operator";

	protected static final String ELEMENT_OR = "or";

	protected static final String ELEMENT_ORDER_BY = "order-by";

	protected static final String ELEMENT_PAGE = "page";

	protected static final String ELEMENT_PROVIDER_NAME = "provider-name";

	protected static final String ELEMENT_PROVIDER_OBJECT = "provider-object";

	protected static final String ELEMENT_QUERY = "query";

	protected static final String ELEMENT_SIZE = "size";

	protected static final String ELEMENT_TIME_ZONE = "time-zone";

	protected static final String ELEMENT_TYPE = "type";

	protected static final String ELEMENT_WHERE = "where";

	protected static final String ELEMENT_VALUE_OPERAND = "value-operand";

	protected static final String ELEMENT_PROVIDER_OPERAND = "provider-operand";

	protected static final String ELEMENT_VARIABLE_OPERAND = "variable-operand";

	// MISC.

	private static final String OPERAND_TYPE = "operand-type";

	private static final String EXCEPTION_XML_NOT_WELL_FORMED = "WAREWORK cannot parse the object-query XML file because it is not well formed.";

	private static final String EXCEPTION_CANNOT_PARSE_OPERAND_VALUE = "WAREWORK cannot parse the object-query XML file because specified operand value cannot be parsed into an object.";

	private static final String EXCEPTION_INVALID_OPERATOR = "WAREWORK cannot parse the object-query XML file because specified operator is not valid.";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Target configuration object.
	private Query<?> config;

	// Name of the query.
	private String queryName;

	// Last tag processed.
	private String lastTag;

	// Last element processed.
	private Object lastElement;

	// List of expressions (also WHERE) to process.
	private List<Object> expressions;

	// Last expression processed.
	private Map<String, String> lastExpression;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
			throws SAXException {
		if ((qName.equals(ELEMENT_QUERY)) || (localName.equals(ELEMENT_QUERY))) {

			// Set the name of the query.
			queryName = attributes.getValue(ELEMENT_NAME);

			// Reset the last element processed.
			lastElement = config;

			// Set the last tag processed.
			lastTag = ELEMENT_QUERY;

		} else if ((qName.equals(ELEMENT_OBJECT)) || (localName.equals(ELEMENT_OBJECT))) {
			lastTag = ELEMENT_OBJECT;
		} else if ((qName.equals(ELEMENT_WHERE)) || (localName.equals(ELEMENT_WHERE))) {

			// Create the WHERE clause.
			Where where = config.getWhere(true);

			// Reset the last element processed.
			lastElement = where;

			// Set the list of expressions to process.
			expressions = new ArrayList<Object>();

			// Add the WHERE clause to the list of expressions to process.
			expressions.add(where);

			// Set the last tag processed.
			lastTag = ELEMENT_WHERE;

		} else if ((qName.equals(ELEMENT_EXPRESSION)) || (localName.equals(ELEMENT_EXPRESSION))) {

			// Define a new expression.
			lastExpression = new HashMap<String, String>();

			// Set the last tag processed.
			lastTag = ELEMENT_EXPRESSION;

		} else if ((qName.equals(ELEMENT_ATTRIBUTE)) || (localName.equals(ELEMENT_ATTRIBUTE))) {
			lastTag = ELEMENT_ATTRIBUTE;
		} else if ((qName.equals(ELEMENT_OPERATOR)) || (localName.equals(ELEMENT_OPERATOR))) {
			lastTag = ELEMENT_OPERATOR;
		} else if ((qName.equals(ELEMENT_VALUE_OPERAND)) || (localName.equals(ELEMENT_VALUE_OPERAND))) {

			// Set the type of the operand.
			lastExpression.put(OPERAND_TYPE, ELEMENT_VALUE_OPERAND);

			// Set the last tag processed.
			lastTag = ELEMENT_VALUE_OPERAND;

		} else if ((qName.equals(ELEMENT_TYPE)) || (localName.equals(ELEMENT_TYPE))) {
			lastTag = ELEMENT_TYPE;
		} else if ((qName.equals(ELEMENT_VALUE)) || (localName.equals(ELEMENT_VALUE))) {
			lastTag = ELEMENT_VALUE;
		} else if ((qName.equals(ELEMENT_FORMAT)) || (localName.equals(ELEMENT_FORMAT))) {
			lastTag = ELEMENT_FORMAT;
		} else if ((qName.equals(ELEMENT_LOCALE)) || (localName.equals(ELEMENT_LOCALE))) {
			lastTag = ELEMENT_LOCALE;
		} else if ((qName.equals(ELEMENT_TIME_ZONE)) || (localName.equals(ELEMENT_TIME_ZONE))) {
			lastTag = ELEMENT_TIME_ZONE;
		} else if ((qName.equals(ELEMENT_PROVIDER_OPERAND)) || (localName.equals(ELEMENT_PROVIDER_OPERAND))) {

			// Set the type of the operand.
			lastExpression.put(OPERAND_TYPE, ELEMENT_PROVIDER_OPERAND);

			// Set the last tag processed.
			lastTag = ELEMENT_PROVIDER_OPERAND;

		} else if ((qName.equals(ELEMENT_PROVIDER_NAME)) || (localName.equals(ELEMENT_PROVIDER_NAME))) {
			lastTag = ELEMENT_PROVIDER_NAME;
		} else if ((qName.equals(ELEMENT_PROVIDER_OBJECT)) || (localName.equals(ELEMENT_PROVIDER_OBJECT))) {
			lastTag = ELEMENT_PROVIDER_OBJECT;
		} else if ((qName.equals(ELEMENT_VARIABLE_OPERAND)) || (localName.equals(ELEMENT_VARIABLE_OPERAND))) {

			// Set the type of the operand.
			lastExpression.put(OPERAND_TYPE, ELEMENT_VARIABLE_OPERAND);

			// Set the of the variable.
			lastExpression.put(ELEMENT_NAME, attributes.getValue(ELEMENT_NAME));

			// Set the last tag processed.
			lastTag = ELEMENT_VARIABLE_OPERAND;

		} else if ((qName.equals(ELEMENT_AND)) || (localName.equals(ELEMENT_AND))) {

			// Reset the last element processed.
			lastElement = config.getWhere().createAnd();

			// Set the current expression.
			expressions.add(lastElement);

			// Set the last tag processed.
			lastTag = ELEMENT_AND;

		} else if ((qName.equals(ELEMENT_OR)) || (localName.equals(ELEMENT_OR))) {

			// Reset the last element processed.
			lastElement = config.getWhere().createOr();

			// Set the current expression.
			expressions.add(lastElement);

			// Set the last tag processed.
			lastTag = ELEMENT_OR;

		} else if ((qName.equals(ELEMENT_NOT)) || (localName.equals(ELEMENT_NOT))) {

			// Reset the last element processed.
			lastElement = config.getWhere().createNot(null);

			// Set the current expression.
			expressions.add(lastElement);

			// Set the last tag processed.
			lastTag = ELEMENT_NOT;

		} else if ((qName.equals(ELEMENT_ORDER_BY)) || (localName.equals(ELEMENT_ORDER_BY))) {

			// Create the WHERE clause.
			OrderBy orderBy = new OrderBy();

			// Set the WHERE clause.
			config.setOrderBy(orderBy);

			// Reset the last element processed.
			lastElement = orderBy;

			// Set the last tag processed.
			lastTag = ELEMENT_ORDER_BY;

		} else if ((qName.equals(ELEMENT_ASC)) || (localName.equals(ELEMENT_ASC))) {

			// Create the WHERE clause.
			OrderBy orderBy = config.getOrderBy();

			// Set the order-by type.
			orderBy.addAscending(attributes.getValue(ELEMENT_ATTRIBUTE));

			// Set the last tag processed.
			lastTag = ELEMENT_ASC;

		} else if ((qName.equals(ELEMENT_DESC)) || (localName.equals(ELEMENT_DESC))) {

			// Create the WHERE clause.
			OrderBy orderBy = config.getOrderBy();

			// Set the order-by type.
			orderBy.addDescending(attributes.getValue(ELEMENT_ATTRIBUTE));

			// Set the last tag processed.
			lastTag = ELEMENT_DESC;

		} else if ((qName.equals(ELEMENT_PAGE)) || (localName.equals(ELEMENT_PAGE))) {

			// Set the page.
			config.setPage(Integer.parseInt(attributes.getValue(ELEMENT_NUMBER)));

			// Set the number of results per page.
			config.setPageSize(Integer.parseInt(attributes.getValue(ELEMENT_SIZE)));

			// Set the last tag processed.
			lastTag = ELEMENT_PAGE;

		} else if ((qName.equals(ELEMENT_PARAMETER)) || (localName.equals(ELEMENT_PARAMETER))) {

			// Set the parameter.
			config.setParameter(attributes.getValue(ELEMENT_NAME), attributes.getValue(ELEMENT_VALUE));

			// Set the last tag processed.
			lastTag = ELEMENT_PARAMETER;

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		if ((qName.equals(ELEMENT_EXPRESSION)) || (localName.equals(ELEMENT_EXPRESSION))) {
			if ((this.lastExpression != null) && (this.lastExpression.containsKey(ELEMENT_ATTRIBUTE))
					&& (this.lastExpression.containsKey(ELEMENT_OPERATOR)) && (config != null)
					&& (config.getWhere() != null)) {

				// Get the attribute.
				final String attribute = this.lastExpression.get(ELEMENT_ATTRIBUTE);

				// Get the operator.
				final Operator operator = Operator.toObject(this.lastExpression.get(ELEMENT_OPERATOR));
				if (operator == null) {
					throw new SAXException(
							"WAREWORK cannot parse the object-query XML file because given operator does not exists.");
				}

				// Create the expression.
				AbstractExpression expression = null;
				if (operator.equals(Operator.IS_NULL)) {
					expression = config.getWhere().createIsNull(attribute);
				} else if (operator.equals(Operator.IS_NOT_NULL)) {
					expression = config.getWhere().createIsNotNull(attribute);
				} else if (this.lastExpression.containsKey(OPERAND_TYPE)) {

					// Get the type of the operand.
					final String operandType = this.lastExpression.get(OPERAND_TYPE);

					// Process the operand.
					if (operandType.equals(ELEMENT_VALUE_OPERAND)) {
						if ((this.lastExpression.containsKey(ELEMENT_TYPE))
								&& (this.lastExpression.containsKey(ELEMENT_VALUE))) {

							// Get the type of the value.
							Class<?> type = null;
							try {
								type = ReflectionL2Helper.getType(this.lastExpression.get(ELEMENT_TYPE));
							} catch (final ClassNotFoundException e) {
								throw new SAXException(
										"WAREWORK cannot parse the object-query XML file because specified operand type does not exists.",
										e);
							}

							// Get the value.
							final String value = this.lastExpression.get(ELEMENT_VALUE);

							// Get how to format the value.
							final String formatPattern = this.lastExpression.get(ELEMENT_FORMAT);

							// Get the locale.
							Locale locale = null;
							if (this.lastExpression.containsKey(ELEMENT_LOCALE)) {
								try {
									locale = (Locale) StringL2Helper.parse(Locale.class,
											this.lastExpression.get(ELEMENT_LOCALE), null, null, null);
								} catch (final ParseException e) {
									throw new SAXException(
											"WAREWORK cannot parse the object-query XML file because specified operand locale does not exists.",
											e);
								}
							}

							// Get the time zone.
							TimeZone timeZone = null;
							if (this.lastExpression.containsKey(ELEMENT_TIME_ZONE)) {
								try {
									timeZone = (TimeZone) StringL2Helper.parse(TimeZone.class,
											this.lastExpression.get(ELEMENT_TIME_ZONE), null, null, null);
								} catch (final ParseException e) {
									throw new SAXException(
											"WAREWORK cannot parse the object-query XML file because specified operand time-zone does not exists.",
											e);
								}
							}

							// Create the expression.
							if (operator.equals(Operator.EQUAL_TO)) {
								try {
									expression = config.getWhere().createEqualToValue(attribute, type, value,
											formatPattern, locale, timeZone);
								} catch (final ParseException e) {
									throw new SAXException(EXCEPTION_CANNOT_PARSE_OPERAND_VALUE, e);
								}
							} else if (operator.equals(Operator.NOT_EQUAL_TO)) {
								try {
									expression = config.getWhere().createNotEqualToValue(attribute, type, value,
											formatPattern, locale, timeZone);
								} catch (final ParseException e) {
									throw new SAXException(EXCEPTION_CANNOT_PARSE_OPERAND_VALUE, e);
								}
							} else if (operator.equals(Operator.GREATER_THAN)) {
								try {
									expression = config.getWhere().createGreaterThanValue(attribute, type, value,
											formatPattern, locale, timeZone);
								} catch (final ParseException e) {
									throw new SAXException(EXCEPTION_CANNOT_PARSE_OPERAND_VALUE, e);
								}
							} else if (operator.equals(Operator.GREATER_THAN_OR_EQUAL_TO)) {
								try {
									expression = config.getWhere().createGreaterThanOrEqualToValue(attribute, type,
											value, formatPattern, locale, timeZone);
								} catch (final ParseException e) {
									throw new SAXException(EXCEPTION_CANNOT_PARSE_OPERAND_VALUE, e);
								}
							} else if (operator.equals(Operator.LESS_THAN)) {
								try {
									expression = config.getWhere().createLessThanValue(attribute, type, value,
											formatPattern, locale, timeZone);
								} catch (final ParseException e) {
									throw new SAXException(EXCEPTION_CANNOT_PARSE_OPERAND_VALUE, e);
								}
							} else if (operator.equals(Operator.LESS_THAN_OR_EQUAL_TO)) {
								try {
									expression = config.getWhere().createLessThanOrEqualToValue(attribute, type, value,
											formatPattern, locale, timeZone);
								} catch (final ParseException e) {
									throw new SAXException(EXCEPTION_CANNOT_PARSE_OPERAND_VALUE, e);
								}
							} else if (operator.equals(Operator.LIKE)) {
								try {
									expression = config.getWhere().createLikeValue(attribute, type, value,
											formatPattern, locale, timeZone);
								} catch (final ParseException e) {
									throw new SAXException(EXCEPTION_CANNOT_PARSE_OPERAND_VALUE, e);
								}
							} else if (operator.equals(Operator.NOT_LIKE)) {
								try {
									expression = config.getWhere().createNotLikeValue(attribute, type, value,
											formatPattern, locale, timeZone);
								} catch (final ParseException e) {
									throw new SAXException(EXCEPTION_CANNOT_PARSE_OPERAND_VALUE, e);
								}
							} else {
								throw new SAXException(EXCEPTION_INVALID_OPERATOR);
							}

						} else {
							throw new SAXException(EXCEPTION_XML_NOT_WELL_FORMED);
						}
					} else if (operandType.equals(ELEMENT_PROVIDER_OPERAND)) {
						if ((this.lastExpression.containsKey(ELEMENT_PROVIDER_NAME))
								&& (this.lastExpression.containsKey(ELEMENT_PROVIDER_OBJECT))) {

							// Get the name of the provider.
							final String providerName = this.lastExpression.get(ELEMENT_PROVIDER_NAME);

							// Get the object to extract from the provider.
							final String providerObject = this.lastExpression.get(ELEMENT_PROVIDER_OBJECT);

							// Create the expression.
							if (operator.equals(Operator.EQUAL_TO)) {
								expression = config.getWhere().createEqualToProviderValue(attribute, providerName,
										providerObject);
							} else if (operator.equals(Operator.NOT_EQUAL_TO)) {
								expression = config.getWhere().createNotEqualToProviderValue(attribute, providerName,
										providerObject);
							} else if (operator.equals(Operator.GREATER_THAN)) {
								expression = config.getWhere().createGreaterThanProviderValue(attribute, providerName,
										providerObject);
							} else if (operator.equals(Operator.GREATER_THAN_OR_EQUAL_TO)) {
								expression = config.getWhere().createGreaterThanOrEqualToProviderValue(attribute,
										providerName, providerObject);
							} else if (operator.equals(Operator.LESS_THAN)) {
								expression = config.getWhere().createLessThanProviderValue(attribute, providerName,
										providerObject);
							} else if (operator.equals(Operator.LESS_THAN_OR_EQUAL_TO)) {
								expression = config.getWhere().createLessThanOrEqualToProviderValue(attribute,
										providerName, providerObject);
							} else if (operator.equals(Operator.LIKE)) {
								expression = config.getWhere().createLikeProviderValue(attribute, providerName,
										providerObject);
							} else if (operator.equals(Operator.NOT_LIKE)) {
								expression = config.getWhere().createNotLikeProviderValue(attribute, providerName,
										providerObject);
							} else {
								throw new SAXException(EXCEPTION_INVALID_OPERATOR);
							}

						} else {
							throw new SAXException(EXCEPTION_XML_NOT_WELL_FORMED);
						}
					} else if (operandType.equals(ELEMENT_VARIABLE_OPERAND)) {
						if (this.lastExpression.containsKey(ELEMENT_NAME)) {

							// Get the variable.
							final String variableName = this.lastExpression.get(ELEMENT_NAME);

							// Create the expression.
							if (operator.equals(Operator.EQUAL_TO)) {
								expression = config.getWhere().createEqualToVariable(attribute, variableName);
							} else if (operator.equals(Operator.NOT_EQUAL_TO)) {
								expression = config.getWhere().createNotEqualToVariable(attribute, variableName);
							} else if (operator.equals(Operator.GREATER_THAN)) {
								expression = config.getWhere().createGreaterThanVariable(attribute, variableName);
							} else if (operator.equals(Operator.GREATER_THAN_OR_EQUAL_TO)) {
								expression = config.getWhere().createGreaterThanOrEqualToVariable(attribute,
										variableName);
							} else if (operator.equals(Operator.LESS_THAN)) {
								expression = config.getWhere().createLessThanVariable(attribute, variableName);
							} else if (operator.equals(Operator.LESS_THAN_OR_EQUAL_TO)) {
								expression = config.getWhere().createLessThanOrEqualToVariable(attribute, variableName);
							} else if (operator.equals(Operator.LIKE)) {
								expression = config.getWhere().createLikeVariable(attribute, variableName);
							} else if (operator.equals(Operator.NOT_LIKE)) {
								expression = config.getWhere().createNotLikeVariable(attribute, variableName);
							} else {
								throw new SAXException(EXCEPTION_INVALID_OPERATOR);
							}

						} else {
							throw new SAXException(EXCEPTION_XML_NOT_WELL_FORMED);
						}
					} else {
						throw new SAXException(EXCEPTION_XML_NOT_WELL_FORMED);
					}

				} else {
					throw new SAXException(EXCEPTION_XML_NOT_WELL_FORMED);
				}

				// Set the expression.
				if (lastElement instanceof Where) {

					// Get the WHERE clause.
					final Where where = (Where) lastElement;

					// Set the expression.
					where.setExpression(expression);

				} else if (lastElement instanceof And) {

					// Get the WHERE clause.
					final And and = (And) lastElement;

					// Set the expression.
					and.add(expression);

				} else if (lastElement instanceof Or) {

					// Get the WHERE clause.
					final Or or = (Or) lastElement;

					// Set the expression.
					or.add(expression);

				} else if (lastElement instanceof Not) {

					// Get the WHERE clause.
					final Not not = (Not) lastElement;

					// Set the expression.
					not.setExpression(expression);

				}

				// Reset the last expression processed.
				expression = null;

			} else {
				throw new SAXException(EXCEPTION_XML_NOT_WELL_FORMED);
			}
		} else if ((qName.equals(ELEMENT_AND)) || (localName.equals(ELEMENT_AND)) || (qName.equals(ELEMENT_OR))
				|| (localName.equals(ELEMENT_OR))) {

			// Get the current expression.
			final AbstractExpression currentExpression = (AbstractExpression) lastElement;

			// Get the parent expression of the current expression.
			final Object parentExpression = expressions.get(expressions.size() - 2);

			// Set the expression.
			if (parentExpression instanceof Where) {

				// Get the WHERE clause.
				final Where where = (Where) parentExpression;

				// Set the expression.
				where.setExpression(currentExpression);

			} else if (parentExpression instanceof And) {

				// Get the WHERE clause.
				final And and = (And) parentExpression;

				// Set the expression.
				and.add(currentExpression);

			} else if (parentExpression instanceof Or) {

				// Get the WHERE clause.
				final Or or = (Or) parentExpression;

				// Set the expression.
				or.add(currentExpression);

			} else if (parentExpression instanceof Not) {

				// Get the WHERE clause.
				final Not not = (Not) parentExpression;

				// Set the expression.
				not.setExpression(currentExpression);

			}

			// Remove the current expression.
			expressions.remove(expressions.size() - 1);

			// Update the last element with the "now" current expression.
			lastElement = expressions.get(expressions.size() - 1);

		} else if ((qName.equals(ELEMENT_NOT)) || (localName.equals(ELEMENT_NOT))) {

			// Get the current expression.
			final AbstractExpression currentExpression = (AbstractExpression) lastElement;

			// Get the parent expression of the current expression.
			final Object parentExpression = expressions.get(expressions.size() - 2);

			// Set the expression.
			if (parentExpression instanceof Where) {

				// Get the WHERE clause.
				final Where where = (Where) parentExpression;

				// Set the expression.
				where.setExpression(currentExpression);

			} else if (parentExpression instanceof And) {

				// Get the WHERE clause.
				final And and = (And) parentExpression;

				// Set the expression.
				and.add(currentExpression);

			} else if (parentExpression instanceof Or) {

				// Get the WHERE clause.
				final Or or = (Or) parentExpression;

				// Set the expression.
				or.add(currentExpression);

			}

			// Remove the current expression.
			expressions.remove(expressions.size() - 1);

			// Update the last element with the "now" current expression.
			lastElement = expressions.get(expressions.size() - 1);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.warework.loader.AbstractSAXLoader#characters(char[], int, int)
	 */
	public void characters(final char ch[], final int start, final int length) throws SAXException {
		if (lastTag != null) {

			// Process the value of the tag.
			if (lastTag.equals(ELEMENT_OBJECT)) {

				// Get the query type.
				Class<?> queryType = null;
				try {
					queryType = Class.forName(new String(ch, start, length));
				} catch (final ClassNotFoundException e) {
					throw new SAXException(
							"WAREWORK cannot parse the object-query XML file because specified object/class for the query does not exists.",
							e);
				}

				// Create the Query object.
				config = createQuery(queryType);

				// Set the name of the query.
				config.setName(queryName);

			} else if (lastTag.equals(ELEMENT_ATTRIBUTE)) {
				lastExpression.put(ELEMENT_ATTRIBUTE, new String(ch, start, length));
			} else if (lastTag.equals(ELEMENT_OPERATOR)) {
				lastExpression.put(ELEMENT_OPERATOR, new String(ch, start, length));
			} else if (lastTag.equals(ELEMENT_TYPE)) {
				lastExpression.put(ELEMENT_TYPE, new String(ch, start, length));
			} else if (lastTag.equals(ELEMENT_VALUE)) {
				lastExpression.put(ELEMENT_VALUE, new String(ch, start, length));
			} else if (lastTag.equals(ELEMENT_FORMAT)) {
				lastExpression.put(ELEMENT_FORMAT, new String(ch, start, length));
			} else if (lastTag.equals(ELEMENT_LOCALE)) {
				lastExpression.put(ELEMENT_LOCALE, new String(ch, start, length));
			} else if (lastTag.equals(ELEMENT_TIME_ZONE)) {
				lastExpression.put(ELEMENT_TIME_ZONE, new String(ch, start, length));
			} else if (lastTag.equals(ELEMENT_PROVIDER_NAME)) {
				lastExpression.put(ELEMENT_PROVIDER_NAME, new String(ch, start, length));
			} else if (lastTag.equals(ELEMENT_PROVIDER_OBJECT)) {
				lastExpression.put(ELEMENT_PROVIDER_OBJECT, new String(ch, start, length));
			}

			// Reset the last tag processed.
			lastTag = null;

		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the object that represents the XML file.
	 * 
	 * @return Object that contains the information of the XML file.<br>
	 *         <br>
	 */
	protected Object getConfiguration() {
		return config;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new Query object.
	 * 
	 * @param queryType Type for the query.<br>
	 *                  <br>
	 * @return A new Query.<br>
	 *         <br>
	 */
	private <E> Query<E> createQuery(final Class<E> queryType) {
		return new Query<E>(getScopeFacade(), queryType);
	}

}
