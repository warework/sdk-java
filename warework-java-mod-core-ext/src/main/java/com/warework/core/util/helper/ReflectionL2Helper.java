package com.warework.core.util.helper;

import java.beans.IntrospectionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.warework.core.util.CommonValueL2Constants;

/**
 * Performs common reflection operations.
 * 
 * @author Jose Schiaffino
 * @version 2.2.0
 */
public abstract class ReflectionL2Helper extends ReflectionL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// FIELD NAMES

	private static final String FIELD_NAME_THIS = "this$";

	private static final String FIELD_NAME_CLASS = "class$";

	private static final String FIELD_NAME_SERIAL_VERSION_UID = "serialVersionUID";

	// METHOD NAMES

	private static final String METHOD_NAME_VALUE_OF = "valueOf";

	// METHOD ARGUMENTS

	private static final Class<?>[] METHOD_EMPTY_ARGUMENT_TYPES = new Class[] {};

	private static final Object[] METHOD_EMPTY_ARGUMENT_VALUES = new Object[] {};

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected ReflectionL2Helper() {
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the class of each object from an array of objects.
	 * 
	 * @param objects
	 *            Objects where to extract each class.<br>
	 * <br>
	 * @return Class of each object.<br>
	 * <br>
	 */
	public static Class<?>[] getTypes(Object[] objects) {

		// Create an array where to store the classes of the objects.
		Class<?>[] classes = new Class[objects.length];

		// Get each class.
		for (int i = 0; i < objects.length; i++) {

			// Get an object.
			Object object = objects[i];

			// Only store the class when object exists in the array.
			if (object != null) {
				classes[i] = object.getClass();
			}

		}

		// Return the classes.
		return classes;

	}

	/**
	 * Get the class of each object from a collection.
	 * 
	 * @param collection
	 *            Collection of objects where to extract each class.<br>
	 * <br>
	 * @return Class of each object.<br>
	 * <br>
	 */
	public static Class<?>[] getTypes(Collection<?> collection) {
		return getTypes(collection.toArray());
	}

	/**
	 * Validates if the class of an object exists in a set of classes.
	 * 
	 * @param object
	 *            Object to validate.<br>
	 * <br>
	 * @param classes
	 *            Array with classes that restricts which type the object must
	 *            have.<br>
	 * <br>
	 * @return <code>true</code> if the object's class exists in the array of
	 *         classes given.<br>
	 * <br>
	 */
	public static boolean isType(Object object, Class<?>[] classes) {

		// Search the class of the object in the array.
		for (int i = 0; i < classes.length; i++) {

			// Get a class.
			Class<?> clazz = classes[i];

			// Validate if the class of the object is the class of the array.
			if ((clazz != null) && (clazz.isInstance(object))) {
				return true;
			}

		}

		// Return false when it's not found.
		return false;

	}

	/**
	 * Validates if an object matches any of the primitive types.
	 * 
	 * @param object
	 *            Object.<br>
	 * <br>
	 * @return <code>true</code> if given object matches any of the primitive
	 *         types or false if not.<br>
	 * <br>
	 */
	public static boolean isPrimitiveType(Object object) {

		// Validate if it is a primitive number.
		boolean primitiveInteger = ((object instanceof Byte)
				|| (object instanceof Short) || (object instanceof Integer) || (object instanceof Long));

		// Validate if it is a primitive decimal.
		boolean primitiveDecimal = ((object instanceof Float) || (object instanceof Double));

		// Validate if it is a primitive number.
		boolean primitiveNumber = (primitiveInteger || primitiveDecimal);

		// Validate all.
		return ((object instanceof Boolean) || primitiveNumber || (object instanceof Character));

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates if an object is an array.
	 * 
	 * @param array
	 *            Object to validate.<br>
	 * <br>
	 * @return <code>true</code> if the object is an array or false if not.<br>
	 * <br>
	 */
	public static boolean isArray(Object array) {
		return array.getClass().isArray();
	}

	/**
	 * Validates if an object is a primitive type array.
	 * 
	 * @param array
	 *            Array object.<br>
	 * <br>
	 * @return <code>true</code> if the array holds a primitive type (boolean,
	 *         byte, short, int, long, float, double or char) or
	 *         <code>false</code> if not.<br>
	 * <br>
	 */
	public static boolean isPrimitiveTypeArray(Object array) {

		// Validate primitive integer array.
		boolean primitiveIntegerArray = ((array instanceof byte[])
				|| (array instanceof short[]) || (array instanceof int[]) || (array instanceof long[]));

		// Validate primitive decimal array.
		boolean primitiveDecimalArray = ((array instanceof float[]) || (array instanceof double[]));

		// Validate primitive number array.
		boolean primitiveNumberArray = (primitiveIntegerArray || primitiveDecimalArray);

		// Validate all.
		return (primitiveNumberArray || (array instanceof boolean[]) || (array instanceof char[]));

	}

	/**
	 * Validates that a given annotation exists in a class that represents the
	 * type of an array.
	 * 
	 * @param object
	 *            Array object.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the type of the array contains the
	 *         annotation and <code>false</code> if not.<br>
	 * <br>
	 */
	public static boolean isAnnotationArray(Object object,
			Class<? extends Annotation> annotationType) {

		// First validate that given object is an array.
		if (ReflectionL2Helper.isArray(object)) {

			// Get the type of the array.
			Class<?> arrayType = ReflectionL2Helper.getArrayType(object);

			// Validate that given annotation exists in the class that
			// represents the type of the array.
			return existsClassAnnotation(arrayType, annotationType);

		}

		// If object is not an array, return false.
		return false;

	}

	/**
	 * Gets the type of an array.
	 * 
	 * @param array
	 *            Array object.<br>
	 * <br>
	 * @return A class that represents the type of the array.<br>
	 * <br>
	 */
	public static Class<?> getArrayType(Object array) {

		// Get the class of the object.
		Class<?> type = array.getClass();

		// Return the type of the array.
		if (type.isArray()) {
			return type.getComponentType();
		} else {
			return null;
		}

	}

	/**
	 * Validates if a string contains a boolean value.
	 * 
	 * @param string
	 *            String that represents a boolean value or not.<br>
	 * <br>
	 * @return <code>true</code> if the string contains 'true' or 'false' values
	 *         or false if not. This methods ignores the case of the string so
	 *         it will return true when the string contains 'TrUe' or 'fAlSe'
	 *         values.<br>
	 * <br>
	 */
	public static boolean isBoolean(String string) {
		return ((string.equalsIgnoreCase(CommonValueL2Constants.STRING_TRUE)) || (string
				.equalsIgnoreCase(CommonValueL2Constants.STRING_FALSE)));
	}

	/**
	 * Validates if the given string is a character value.
	 * 
	 * @param string
	 *            String that represents a character value or not.<br>
	 * <br>
	 * @return <code>true</code> if the given string contains just one character
	 *         and false if not.<br>
	 * <br>
	 */
	public static boolean isCharacter(String string) {
		return (string.length() == 1);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets an array with the classes that represents common numbers.
	 * 
	 * @return Numbers' classes.<br>
	 * <br>
	 */
	public static Class<?>[] getNumbers() {
		return new Class<?>[] { Byte.class, Short.class, Integer.class,
				BigInteger.class, Long.class, Float.class, Double.class,
				BigDecimal.class };
	}

	/**
	 * Gets an array with the classes that represents primitive numbers.
	 * 
	 * @return Primitive numbers' classes.<br>
	 * <br>
	 */
	public static Class<?>[] getPrimitiveNumbers() {
		return new Class[] { byte.class, short.class, int.class, long.class,
				float.class, double.class };
	}

	/**
	 * Gets a number that matches the value of the given string.
	 * 
	 * @param number
	 *            A number as a string, positive or negative and with or without
	 *            decimals. The format of the string must be flat (do not use
	 *            any kind of separators to represent thousand, million, ...
	 *            numbers, like dots or commas).<br>
	 * <br>
	 * @return The smallest number type that matches the value of the given
	 *         string. If your number is '0' you'll get a
	 *         <code>java.lang.Byte</code> object, if your number is '300'
	 *         you'll get a <code>java.lang.Short</code> and so on. Only objects
	 *         that matches primitive number types are returned, so it excludes
	 *         <code>java.math.BigInteger</code> and
	 *         <code>java.math.BigDecimal</code>. This method returns inifity
	 *         (positive and negative) values as <code>java.lang.Double</code>.<br>
	 * <br>
	 */
	public static Number getNumber(String number) {

		// Get the numbers' classes.
		Class<?>[] numbers = getNumbers();

		// Try to get the value with each number data type.
		for (int i = 0; i < numbers.length; i++) {

			// Get the class of the number
			Class<?> clazz = numbers[i];

			// Transform the number as a string into a number of this class.
			try {

				// Get the method 'valueOf' of the number.
				Method valueOfMethod = clazz.getMethod(METHOD_NAME_VALUE_OF,
						new Class[] { String.class });

				// Get the value of the given number
				Number value = (Number) valueOfMethod.invoke(null,
						new Object[] { number });

				// Validate Float min and max values to fit into a double when
				// required.
				if (value instanceof Float) {

					// Cast the value.
					Float floatValue = (Float) value;

					// Return a double when float is an infinity value.
					if ((floatValue.equals(Float.NEGATIVE_INFINITY))
							|| (floatValue.equals(Float.POSITIVE_INFINITY))) {
						continue;
					} else if ((floatValue == 0.0)
							&& (Double.parseDouble(number) > 0.0)) {
						continue;
					}

				}

				// Return the value.
				return value;

			} catch (Exception e) {
				// Do nothing
			}

		}

		// Throw an exception when no numbers are found
		throw new NumberFormatException();

	}

	/**
	 * Validates if an object is a number.
	 * 
	 * @param object
	 *            An object.<br>
	 * <br>
	 * @return <code>true</code> if the given object is a number type object.<br>
	 * <br>
	 */
	public static boolean isNumber(Object object) {
		return (object instanceof Number);
	}

	/**
	 * Validates if a string contains a number value. As this method uses the
	 * 'getNumber' method of this class, check it out to understand how the
	 * format of the string should be.
	 * 
	 * @param string
	 *            String that represents a number value or not.<br>
	 * <br>
	 * @return <code>true</code> if the given string is a number and false if
	 *         not.<br>
	 * <br>
	 */
	public static boolean isNumber(String string) {

		// Convert the string into a number
		try {
			getNumber(string);
		} catch (NumberFormatException e) {
			return false;
		}

		// At this point the string is a number
		return true;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Copies an object.
	 * 
	 * @param object
	 *            Object to copy.<br>
	 * <br>
	 * @return A raw copy of the given object.<br>
	 * <br>
	 * @throws IOException
	 *             If there is an error when trying to copy the object.<br>
	 * <br>
	 * @throws ClassNotFoundException
	 *             If there is an error when trying to load the class.<br>
	 * <br>
	 */
	public static Object copyObject(Object object) throws IOException,
			ClassNotFoundException {

		// Copy the object into an output stream.
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Get the output stream.
		ObjectOutputStream oos = new ObjectOutputStream(baos);

		// Write the object.
		oos.writeObject(object);

		// Copy the object into an input stream.
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
				baos.toByteArray()));

		// Read the object and return it.
		return ois.readObject();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the last package name.
	 * 
	 * @param p
	 *            Package where to extract the name.<br>
	 * <br>
	 * @return Name of the last package. Example: for 'com.mycompany' this
	 *         method returns 'mycompany'.<br>
	 * <br>
	 */
	public static String getLastPackageName(Package p) {

		// Get then name of the package.
		String packageName = p.getName();

		// Validate that a package exists.
		if ((packageName != null)
				&& (!packageName.equals(CommonValueL2Constants.STRING_EMPTY))) {
			if (packageName.lastIndexOf(CommonValueL2Constants.CHAR_PERIOD) != -1) {
				return packageName.substring(packageName
						.lastIndexOf(CommonValueL2Constants.CHAR_PERIOD) + 1,
						packageName.length());
			} else {
				return packageName;
			}
		} else {
			return CommonValueL2Constants.STRING_EMPTY;
		}

	}

	/**
	 * Gets the path of a package. This method does not use the system-dependent
	 * default name-separator character, instead the path that it returns
	 * separates each directory with '/' character (UNIX style).
	 * 
	 * @param p
	 *            Package or a <code>null</code> value for default packages.<br>
	 * <br>
	 * @return Path representation of the package. Returns an empty string if
	 *         given package is <code>null</code>, to match default packages.<br>
	 * <br>
	 */
	public static String getPackageAsPath(Package p) {
		if (p != null) {
			return p.getName().replace(CommonValueL2Constants.CHAR_PERIOD,
					CommonValueL2Constants.CHAR_FORWARD_SLASH);
		} else {
			return CommonValueL2Constants.STRING_EMPTY;
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new instance of a class.
	 * 
	 * @param clazz
	 *            Class where to get a new instance.<br>
	 * <br>
	 * @param types
	 *            Types of the arguments for the constructor to use.<br>
	 * <br>
	 * @param values
	 *            Values for the constructor.<br>
	 * <br>
	 * @param <T>
	 *            The type of object to create.<br>
	 * <br>
	 * @return A new instance of the class.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 * @throws InstantiationException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 */
	public static <T> T createInstance(Class<T> clazz, Class<?>[] types,
			Object[] values) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {

		// Specify the constructor of the class.
		Constructor<T> constructor = clazz.getConstructor(types);

		// Create a new instance of the service's factory.
		return constructor.newInstance(values);

	}

	/**
	 * Creates a new instance of a class.
	 * 
	 * @param clazz
	 *            Name of the class.<br>
	 * <br>
	 * @param types
	 *            Types of the arguments for the constructor to use.<br>
	 * <br>
	 * @param values
	 *            Values for the constructor.<br>
	 * <br>
	 * @return A new instance of the class.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 * @throws InstantiationException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 * @throws ClassNotFoundException
	 *             If there is an error when trying to create the object.<br>
	 * <br>
	 */
	public static Object createInstance(String clazz, Class<?>[] types,
			Object[] values) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, ClassNotFoundException {
		return createInstance(Class.forName(clazz), types, values);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the fields of a given class and its parents.
	 * 
	 * @param clazz
	 *            Class where to get the fields.<br>
	 * <br>
	 * @return Fields of the class and its parents.<br>
	 * <br>
	 */
	public static Field[] getFields(Class<?> clazz) {

		// Get the parent class.
		Class<?> superClass = clazz.getSuperclass();

		// Perform recursivity until the object class is found.
		if (superClass.equals(Object.class)) {

			// Get the fields of the class.
			Field[] fields = clazz.getDeclaredFields();

			// Remove the 'this$' instance field from the class.
			return removeDefaultInstances(fields);

		} else {

			// Get the fields of the parent class.
			Field[] superFields = getFields(superClass);
			if (superFields == null) {
				superFields = new Field[] {};
			}

			// Get the fields of the class.
			Field[] thisFields = clazz.getDeclaredFields();
			if (thisFields == null) {
				thisFields = new Field[] {};
			}

			// Remove the 'this$' instance field from the class.
			thisFields = removeDefaultInstances(thisFields);

			// Merge both arrays and return.
			return (Field[]) DataStructureL2Helper.mergeArrays(superFields,
					thisFields);

		}

	}

	/**
	 * Gets a field from a class.
	 * 
	 * @param clazz
	 *            Class where to get the field.<br>
	 * <br>
	 * @param fieldName
	 *            Name of the field.<br>
	 * <br>
	 * @return A field of the class or <code>null</code> if no field with this
	 *         name exists.<br>
	 * <br>
	 */
	public static Field getField(Class<?> clazz, String fieldName) {

		// Get every field of the class.
		Field[] fields = getFields(clazz);

		// Search for the field.
		for (int i = 0; i < fields.length; i++) {

			// Get one field from the class.
			Field field = fields[i];

			// Decide if it is the field that we are looking for.
			if (field.getName().equals(fieldName)) {
				return field;
			}

		}

		// Nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the method of a class.
	 * 
	 * @param clazz
	 *            Class where to get the method.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method to get.<br>
	 * <br>
	 * @param paramTypes
	 *            Type of each argument of the method.<br>
	 * <br>
	 * @return A method.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static Method getMethod(Class<?> clazz, String methodName,
			Class<?>[] paramTypes) throws NoSuchMethodException {
		return clazz.getMethod(methodName, paramTypes);
	}

	/**
	 * Gets every method that matches the given name.
	 * 
	 * @param clazz
	 *            Class where to get the methods.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method.<br>
	 * <br>
	 * @return Array of methods.<br>
	 * <br>
	 */
	public static Method[] getMethods(Class<?> clazz, String methodName) {

		// Get every method that exist in the class.
		Method[] methods = clazz.getMethods();

		// Collection for every method found that matches the given name.
		Set<Method> methodsFound = new HashSet<Method>();

		// Search for the methods.
		for (int i = 0; i < methods.length; i++) {

			// Get one method.
			Method method = methods[i];

			// Validate that both have the same name.
			if (method.getName().equals(methodName)) {
				methodsFound.add(method);
			}

		}

		// Return an array with the methods.
		return (Method[]) DataStructureL2Helper.toArray(methodsFound, false);

	}

	/**
	 * Gets every method that matches the given name and given number of
	 * parameters.
	 * 
	 * @param clazz
	 *            Class where to get the methods.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method.<br>
	 * <br>
	 * @param numParams
	 *            Number of parameters that must have the method.<br>
	 * <br>
	 * @return Array of methods.<br>
	 * <br>
	 */
	public static Method[] getMethods(Class<?> clazz, String methodName,
			int numParams) {

		// Get every method that exist in the class.
		Method[] methods = getMethods(clazz, methodName);

		// Collection for every method found that matches the given name.
		Set<Method> methodsFound = new HashSet<Method>();

		// Search for the methods.
		for (int i = 0; i < methods.length; i++) {

			// Get one method.
			Method method = methods[i];

			// Get the types of the parameters.
			Class<?>[] paramTypes = method.getParameterTypes();

			// Add only given name and given number of parameters are the same.
			if ((method.getName().equals(methodName))
					&& (paramTypes.length == numParams)) {
				methodsFound.add(method);
			}

		}

		// Return an array with the methods.
		return (Method[]) DataStructureL2Helper.toArray(methodsFound, false);

	}

	/**
	 * Decides if a given annotation exists in a method.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method where to search for the annotation.<br>
	 * <br>
	 * @param paramTypes
	 *            Type of each argument of the method.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists and false if not.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static boolean existsMethodAnnotation(Class<?> clazz,
			String methodName, Class<?>[] paramTypes,
			Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {

		// Get the method.
		Method method = getMethod(clazz, methodName, paramTypes);

		// Validate if annotation exists in the method.
		return method.isAnnotationPresent(annotationType);

	}

	/**
	 * Decides if a given annotation exists in a method without arguments.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists and false if not.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static boolean existsMethodAnnotation(Class<?> clazz,
			String methodName, Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {
		return existsMethodAnnotation(clazz, methodName,
				METHOD_EMPTY_ARGUMENT_TYPES, annotationType);
	}

	/**
	 * Decides if a given annotation exists in a GET method.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param attributeName
	 *            Name of the attribute with the <code>getXYZ</code> method.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists and false if not.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static boolean existsGetMethodAnnotation(Class<?> clazz,
			String attributeName, Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {
		return existsMethodAnnotation(clazz,
				createGetMethodName(attributeName),
				METHOD_EMPTY_ARGUMENT_TYPES, annotationType);
	}

	/**
	 * Decides if a given annotation exists in a SET method.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param attributeName
	 *            Name of the attribute with the <code>setXYZ</code> method.<br>
	 * <br>
	 * @param paramType
	 *            Type of the attribute; the same type as the
	 *            <code>setXYZ</code> argument.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists and false if not.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static boolean existsSetMethodAnnotation(Class<?> clazz,
			String attributeName, Class<?> paramType,
			Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {
		return existsMethodAnnotation(clazz,
				createSetMethodName(attributeName),
				new Class<?>[] { paramType }, annotationType);
	}

	/**
	 * Decides if a given annotation exists in any of the methods of a given
	 * class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists in any of the methods
	 *         of the class and false if not.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static boolean existsMethodAnnotation(Class<?> clazz,
			Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {

		// Get every method.
		Method[] methods = clazz.getMethods();

		// Search for the annotation.
		if (methods != null) {
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].isAnnotationPresent(annotationType)) {
					return true;
				}
			}
		}

		// At this point, no annotation exists.
		return false;

	}

	/**
	 * Decides if a given annotation exists just one time in any of the methods
	 * of a given class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists in any of the methods
	 *         of the class just one time and false if not.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static boolean existsMethodAnnotationOnlyOnce(Class<?> clazz,
			Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {

		// Get every method.
		Method[] methods = clazz.getMethods();

		// Occurrences counter.
		int counter = 0;

		// Count occurrences.
		if (methods != null) {
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].isAnnotationPresent(annotationType)) {

					// Increase counter.
					counter = counter + 1;

					// More than one occurrence? If so, return false.
					if (counter > 1) {
						return false;
					}

				}
			}
		}

		// Return if 0 or 1 occurrences.
		return (counter == 1);

	}

	/**
	 * Counts the number of times that a given annotation exist in the methods
	 * of a class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return Number of occurrences of the annotation in the methods of the
	 *         class.
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static int countMethodAnnotation(Class<?> clazz,
			Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {

		// Get every method.
		Method[] methods = clazz.getMethods();

		// Occurrences counter.
		int counter = 0;

		// Count occurrences.
		if (methods != null) {
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].isAnnotationPresent(annotationType)) {
					counter = counter + 1;
				}
			}
		}

		// Return counter.
		return counter;

	}

	/**
	 * Gets the annotation of a method.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method where to search for the annotation.<br>
	 * <br>
	 * @param paramTypes
	 *            Type of each argument of the method.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return Annotation of the method. If annotation is not present then this
	 *         method returns <code>null</code>.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static Annotation getMethodAnnotation(Class<?> clazz,
			String methodName, Class<?>[] paramTypes,
			Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {

		// Get the method.
		Method method = getMethod(clazz, methodName, paramTypes);

		// Return the annotation.
		if (method.isAnnotationPresent(annotationType)) {
			return method.getAnnotation(annotationType);
		} else {
			return null;
		}

	}

	/**
	 * Gets the annotation of a method without arguments.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return Annotation of the method. If annotation is not present then this
	 *         method returns <code>null</code>.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static Annotation getMethodAnnotation(Class<?> clazz,
			String methodName, Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {
		return getMethodAnnotation(clazz, methodName,
				METHOD_EMPTY_ARGUMENT_TYPES, annotationType);
	}

	/**
	 * Gets the value of a parameter in an annotation that exists in method of a
	 * class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method where to search for the annotation.<br>
	 * <br>
	 * @param paramTypes
	 *            Type of each argument of the method.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @param annotationParameter
	 *            Parameter of the annotation where to retrieve the value.<br>
	 * <br>
	 * @return Value of the annotation's parameter in a method of a class.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getMethodAnnotationValue(Class<?> clazz,
			String methodName, Class<?>[] paramTypes,
			Class<? extends Annotation> annotationType,
			String annotationParameter) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		// Get the annotation.
		Annotation annotation = getMethodAnnotation(clazz, methodName,
				paramTypes, annotationType);

		// Return the value of the parameter in the annotation.
		return invokeMethod(annotationParameter, annotation);

	}

	/**
	 * Gets the value of a parameter in an annotation that exists in method
	 * without arguments of a class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param methodName
	 *            Name of the method where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @param annotationParameter
	 *            Parameter of the annotation where to retrieve the value.<br>
	 * <br>
	 * @return Value of the annotation's parameter in a method of a class.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getMethodAnnotationValue(Class<?> clazz,
			String methodName, Class<? extends Annotation> annotationType,
			String annotationParameter) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return getMethodAnnotationValue(clazz, methodName,
				METHOD_EMPTY_ARGUMENT_TYPES, annotationType,
				annotationParameter);
	}

	/**
	 * Gets the value of a parameter in an annotation that exists in a GET
	 * method of a class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param attributeName
	 *            Name of the attribute with the <code>getXYZ</code> method.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @param annotationParameter
	 *            Parameter of the annotation where to retrieve the value.<br>
	 * <br>
	 * @return Value of the annotation's parameter in a method of a class.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getMethodGetAnnotationValue(Class<?> clazz,
			String attributeName, Class<? extends Annotation> annotationType,
			String annotationParameter) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return getMethodAnnotationValue(clazz,
				createGetMethodName(attributeName),
				METHOD_EMPTY_ARGUMENT_TYPES, annotationType,
				annotationParameter);
	}

	/**
	 * Gets the value of a parameter in an annotation that exists in a SET
	 * method of a class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param attributeName
	 *            Name of the attribute with the <code>setXYZ</code> method.<br>
	 * <br>
	 * @param paramType
	 *            Type of the attribute; the same type as the
	 *            <code>setXYZ</code> argument.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @param annotationParameter
	 *            Parameter of the annotation where to retrieve the value.<br>
	 * <br>
	 * @return Value of the annotation's parameter in a method of a class.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getMethodSetAnnotationValue(Class<?> clazz,
			String attributeName, Class<?> paramType,
			Class<? extends Annotation> annotationType,
			String annotationParameter) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return getMethodAnnotationValue(clazz,
				createSetMethodName(attributeName),
				new Class<?>[] { paramType }, annotationType,
				annotationParameter);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Invokes a method without arguments in an object.
	 * 
	 * @param methodName
	 *            Method to execute.<br>
	 * <br>
	 * @param object
	 *            Object where to execute the method.<br>
	 * <br>
	 * @return The result of the execution of the method.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object invokeMethod(String methodName, Object object)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return invokeMethod(methodName, object, METHOD_EMPTY_ARGUMENT_TYPES,
				METHOD_EMPTY_ARGUMENT_VALUES);
	}

	/**
	 * Invokes a method.
	 * 
	 * @param methodName
	 *            Name of the method to invoke.<br>
	 * <br>
	 * @param object
	 *            Object where to invoke a method.<br>
	 * <br>
	 * @param paramTypes
	 *            Types of the parameters of the method.<br>
	 * <br>
	 * @param paramValues
	 *            Values for the method.
	 * @return The result of the execution of the method.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object invokeMethod(String methodName, Object object,
			Class<?>[] paramTypes, Object[] paramValues)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Get the class of the method.
		Class<?> methodClass = object.getClass();

		// Get the method.
		Method method = methodClass.getMethod(methodName, paramTypes);

		// Invoke the method.
		return method.invoke(object, paramValues);

	}

	/**
	 * Invokes a setter method for an attribute. The name of the attribute is
	 * used to call the 'setAttribute' method.
	 * 
	 * @param attribute
	 *            Name of the attribute.<br>
	 * <br>
	 * @param object
	 *            Object where to execute the setter method.<br>
	 * <br>
	 * @param paramTypes
	 *            Types of the parameters of the method.<br>
	 * <br>
	 * @param paramValues
	 *            Values for the method.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 */
	public static void invokeSetMethod(String attribute, Object object,
			Class<?>[] paramTypes, Object[] paramValues)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Create a setter method name for a property.
		String methodName = createSetMethodName(attribute);

		// Invoke the method.
		invokeMethod(methodName, object, paramTypes, paramValues);

	}

	/**
	 * Invokes a setter method with just one parameter for an attribute that
	 * exists in an object. The name of the attribute is used to call the
	 * 'setAttribute' method.
	 * 
	 * @param attribute
	 *            Name of the attribute.<br>
	 * <br>
	 * @param object
	 *            Object where to execute the setter method.<br>
	 * <br>
	 * @param value
	 *            Object to pass as the argument of the method.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws IntrospectionException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 */
	public static void invokeSetMethod(String attribute, Object object,
			Object value) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, IntrospectionException,
			NoSuchFieldException {

		// Create a setter method name for a property.
		String methodName = createSetMethodName(attribute);

		// Types of the parameters of the method.
		Class<?>[] classes = new Class[] { getAttributeClass(object, attribute) };

		// Values for the method.
		Object[] values = new Object[] { value };

		// Invoke the method.
		invokeMethod(methodName, object, classes, values);

	}

	/**
	 * Invokes a getter method for an attribute. The name of the attribute is
	 * used to call the 'getAttribute' method.
	 * 
	 * @param attribute
	 *            Name of the attribute.<br>
	 * <br>
	 * @param object
	 *            Object where to execute the getter method.<br>
	 * <br>
	 * @return The result of the execution of the method.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object invokeGetMethod(String attribute, Object object)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Create a getter method name for a property.
		String methodName = createGetMethodName(attribute);

		// Invoke the method.
		return invokeMethod(methodName, object);

	}

	/**
	 * Invokes a 'is' method for an attribute. The name of the attribute is used
	 * to call the 'isAttribute' method.
	 * 
	 * @param attribute
	 *            Name of the attribute.<br>
	 * <br>
	 * @param object
	 *            Object where to execute the 'is' method.<br>
	 * <br>
	 * @return The result of the execution of the method.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static boolean invokeIsMethod(String attribute, Object object)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Create a getter method name for a property.
		String methodName = createIsMethodName(attribute);

		// Invoke the method.
		return (Boolean) invokeMethod(methodName, object);

	}

	/**
	 * Invokes a static method.
	 * 
	 * @param methodName
	 *            Name of the method to invoke.<br>
	 * <br>
	 * @param clazz
	 *            Class where to invoke a method.<br>
	 * <br>
	 * @param paramTypes
	 *            Types of the arguments of the method.<br>
	 * <br>
	 * @param paramValues
	 *            Values for the method.<br>
	 * <br>
	 * @return The result of the method.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object invokeStaticMethod(String methodName, Class<?> clazz,
			Class<?>[] paramTypes, Object[] paramValues)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Get the method.
		Method method = getMethod(clazz, methodName, paramTypes);

		// Invoke the method
		return method.invoke(null, paramValues);

	}

	/**
	 * Invokes a static method.
	 * 
	 * @param methodName
	 *            Name of the method to invoke.<br>
	 * <br>
	 * @param clazz
	 *            Name of the class.<br>
	 * <br>
	 * @param paramTypes
	 *            Types of the arguments of the method.<br>
	 * <br>
	 * @param paramValues
	 *            Values for the method.<br>
	 * <br>
	 * @return The result of the method.<br>
	 * <br>
	 * @throws ClassNotFoundException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object invokeStaticMethod(String methodName, String clazz,
			Class<?>[] paramTypes, Object[] paramValues)
			throws ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		// Get the class of the method.
		Class<?> methodClass = Class.forName(clazz);

		// Invoke the method.
		return invokeStaticMethod(methodName, methodClass, paramTypes,
				paramValues);

	}

	/**
	 * Decides if a setter method exists for a given attribute name.
	 * 
	 * @param attribute
	 *            Name of the attribute.<br>
	 * <br>
	 * @param clazz
	 *            Class where to search for the 'set' method.<br>
	 * <br>
	 * @return <code>true</code> if the 'set' method exists and false if not.<br>
	 * <br>
	 */
	public static boolean existsSetMethod(String attribute, Class<?> clazz) {
		try {

			// Get the field that represents the attribute.
			Field field = getField(clazz, attribute);

			// Search for the method.
			return (clazz.getMethod(createSetMethodName(attribute),
					field.getType()) != null);

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Decides if a getter method exists for a given attribute name.
	 * 
	 * @param attribute
	 *            Name of the attribute.<br>
	 * <br>
	 * @param clazz
	 *            Class where to search for the 'get' method.<br>
	 * <br>
	 * @return <code>true</code> if the 'get' method exists and false if not.<br>
	 * <br>
	 */
	public static boolean existsGetMethod(String attribute, Class<?> clazz) {
		try {
			return (clazz.getMethod(createGetMethodName(attribute)) != null);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Decides if a 'is' method exists for a given attribute name.
	 * 
	 * @param attribute
	 *            Name of the attribute.<br>
	 * <br>
	 * @param clazz
	 *            Class where to search for the 'get' method.<br>
	 * <br>
	 * @return <code>true</code> if the 'is' method exists and false if not.<br>
	 * <br>
	 */
	public static boolean existsIsMethod(String attribute, Class<?> clazz) {
		try {
			return (clazz.getMethod(CommonValueL2Constants.STRING_IS
					+ StringL2Helper.firstLetterToUpperCase(attribute)) != null);
		} catch (Exception e) {
			return false;
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Transforms a Java Bean into a <code>java.util.Hashtable</code>. This
	 * method copies from a given Java Bean the value of every attribute into a
	 * Map. The key of the Map will be the name of the attribute and the value
	 * of the Map will be the value of the execution of the 'get' method for
	 * that attribute.
	 * 
	 * @param bean
	 *            Java Bean where to extract the values of the attributes.<br>
	 * <br>
	 * @return Map where each key is the name of the attribute and each value of
	 *         the map corresponds with the value of each attribute.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the values.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the values.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the values.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the values.<br>
	 * <br>
	 */
	public static Map<String, Object> toHashMap(Object bean)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, NoSuchFieldException {

		// Get the class of the given object.
		Class<?> clazz = bean.getClass();

		// Get the attributes of the class.
		String[] attributes = getAttributes(clazz);

		// Set up the result.
		Map<String, Object> result = new HashMap<String, Object>();

		// Copy the name of each field and its value into the map.
		for (int i = 0; i < attributes.length; i++) {

			// Get the name of the attribute.
			String attribute = attributes[i];

			// Store the name of the field and its value.
			result.put(attribute, getAttributeValue(bean, attribute));

		}

		// Return the map with the fields
		return result;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of an attribute that exists in a given class.
	 * 
	 * @param clazz
	 *            Class where to get the type of the attribute.<br>
	 * <br>
	 * @param attributeName
	 *            Attribute of the class where to get the type.<br>
	 * <br>
	 * @return Type of the attribute.<br>
	 * <br>
	 */
	public static Class<?> getAttributeType(Class<?> clazz, String attributeName) {

		// Gets the property.
		Field field = getField(clazz, attributeName);

		// Return the type of the property.
		if (field != null) {
			return field.getType();
		} else {
			return null;
		}

	}

	/**
	 * Gets the names of the attributes that exists in a given class and its
	 * parents. Each attribute must have a 'set' and a 'get' (or 'is') method to
	 * perform as a Java Bean.
	 * 
	 * @param clazz
	 *            Class where to get the names of the attributes.<br>
	 * <br>
	 * @return Names of the attributes that have a get/is and a set method.<br>
	 * <br>
	 */
	public static String[] getAttributes(Class<?> clazz) {

		// Get each field of the class.
		Field[] fields = getFields(clazz);

		// Search for attributes in the class.
		Collection<String> attributes = new HashSet<String>();
		for (int i = 0; i < fields.length; i++) {

			// Get the name of a field.
			String filedName = fields[i].getName();

			// Validate it conform the Java Bean rules.
			if (fields[i].getType().equals(Boolean.class)) {
				if (((existsGetMethod(filedName, clazz)) || (ReflectionL2Helper
						.existsIsMethod(filedName, clazz)))
						&& (ReflectionL2Helper
								.existsSetMethod(filedName, clazz))) {
					attributes.add(filedName);
				}
			} else {
				if ((existsGetMethod(filedName, clazz))
						&& (ReflectionL2Helper
								.existsSetMethod(filedName, clazz))) {
					attributes.add(filedName);
				}
			}

		}

		// Return the array.
		return (String[]) DataStructureL2Helper.toArray(attributes, false);

	}

	/**
	 * Sets the value for an attribute in a bean by calling a 'set' method.
	 * 
	 * @param bean
	 *            Object where to invoke the 'set' method.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'setTitle', you
	 *            can set the value for 'title' with the name for the attribute
	 *            (this argument) 'title'. If X contains object Y with an
	 *            attribute named 'y' and Y contains the attribute 'age', you
	 *            can set the value for 'age' with 'y.age'. The dot separates
	 *            each bean and you can make the navigation as deep as you want.
	 *            'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson' and finally 'setName'. You can also navigate
	 *            through arrays, collections and maps. Examples:
	 *            'agenda.friends[0].name' invokes 'getAgenda' first, after that
	 *            'getFriends' and the element that exists in the '0' position
	 *            of the collection or array that it returns, and finally the
	 *            'setName' method in the object that exists in that position of
	 *            the array or collection. For maps, the value inside of '[' and
	 *            ']' must exists between single quotes. Example:
	 *            "agenda.friends['john'].name" retrieves this time from the
	 *            'friends' attribute a <code>java.util.Map</code> and uses
	 *            'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @param value
	 *            Value to set for the attribute of the bean.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws IntrospectionException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 */
	public static void setAttributeValue(Object bean, String attribute,
			Object value) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, IntrospectionException,
			NoSuchFieldException {

		// Get the object that is referenced by the name of the attribute.
		Object holder = extractAttributeHolder(bean, attribute);

		// Extract the last attribute name.
		String lastAttribute = lastStringAttribute(attribute);

		// Validate if the name of the attribute references a collection.
		if (isStringCollection(lastAttribute)) {
			setCollectionValue(holder, lastAttribute, value);
		} else {
			invokeSetMethod(lastAttribute, holder, value);
		}

	}

	/**
	 * Gets the value for an attribute in a bean by calling a 'get' or 'is'
	 * method.
	 * 
	 * @param bean
	 *            Object where to invoke the 'get/is' method.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the value of 'title' with the name for the attribute
	 *            (this argument) 'title'. If X contains object Y with an
	 *            attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the value of 'age' with 'y.age'. The dot separates
	 *            each bean and you can make the navigation as deep as you want.
	 *            'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson' and finally 'getName'. You can also navigate
	 *            through arrays, collections and maps. Examples:
	 *            'agenda.friends[0].name' invokes 'getAgenda' first, after that
	 *            'getFriends' and the element that exists in the '0' position
	 *            of the collection or array that it returns, and finally the
	 *            'getName' method in the object that exists in that position of
	 *            the array or collection. For maps, the value inside of '[' and
	 *            ']' must exists between single quotes. Example:
	 *            "agenda.friends['john'].name" retrieves this time from the
	 *            'friends' attribute a <code>java.util.Map</code> and uses
	 *            'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @return Value of the attribute.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getAttributeValue(Object bean, String attribute)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, NoSuchFieldException {

		// Get the object that is referenced by the name of the attribute.
		Object holder = extractAttributeHolder(bean, attribute);

		// Extract the last attribute name.
		String lastAttribute = lastStringAttribute(attribute);

		// Validate if the name of the attribute references a collection.
		if (isStringCollection(lastAttribute)) {
			return getCollectionValue(holder, lastAttribute);
		} else if (existsGetMethod(lastAttribute, holder.getClass())) {
			return invokeGetMethod(lastAttribute, holder);
		} else {
			return invokeIsMethod(lastAttribute, holder);
		}

	}

	/**
	 * Decides if the value of an attribute in a bean is <code>null</code>.
	 * 
	 * @param bean
	 *            Object where to retrieve the value.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the value of 'title' with the name for the attribute
	 *            (this argument) 'title'. If X contains object Y with an
	 *            attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the value of 'age' with 'y.age'. The dot separates
	 *            each bean and you can make the navigation as deep as you want.
	 *            'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson' and finally 'getName'. You can also navigate
	 *            through arrays, collections and maps. Examples:
	 *            'agenda.friends[0].name' invokes 'getAgenda' first, after that
	 *            'getFriends' and the element that exists in the '0' position
	 *            of the collection or array that it returns, and finally the
	 *            'getName' method in the object that exists in that position of
	 *            the array or collection. For maps, the value inside of '[' and
	 *            ']' must exists between single quotes. Example:
	 *            "agenda.friends['john'].name" retrieves this time from the
	 *            'friends' attribute a <code>java.util.Map</code> and uses
	 *            'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @return <code>true</code> if the value of the attribute is
	 *         <code>null</code> or false if not.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static boolean isAttributeNull(Object bean, String attribute)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, NoSuchFieldException {
		return (getAttributeValue(bean, attribute) == null);
	}

	/**
	 * Gets the type of an attribute.
	 * 
	 * @param bean
	 *            Object where to retrieve the class of an attribute.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the type of 'title' with the name for the attribute
	 *            (this argument) 'title'. If X contains object Y with an
	 *            attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the type of 'age' with 'y.age'. The dot separates each
	 *            bean and you can make the navigation as deep as you want.
	 *            'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson', then 'getName' and finally the 'getClass' method
	 *            to retrieve the type. You can also navigate through arrays,
	 *            collections and maps. Examples: 'agenda.friends[0].name'
	 *            invokes 'getAgenda' first, after that 'getFriends' and the
	 *            element that exists in the '0' position of the collection or
	 *            array that it returns, and finally the 'getName' + 'getClass'
	 *            methods in the object that exists in that position of the
	 *            array or collection. For maps, the value inside of '[' and ']'
	 *            must exists between single quotes. Example:
	 *            "agenda.friends['john'].name" retrieves this time from the
	 *            'friends' attribute a <code>java.util.Map</code> and uses
	 *            'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @return Type of an attribute.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Class<?> getAttributeClass(Object bean, String attribute)
			throws NoSuchFieldException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return getAttributeField(bean, attribute).getType();
	}

	/**
	 * Gets the <code>java.lang.reflect.Field</code> of an attribute.
	 * 
	 * @param bean
	 *            Object where to retrieve the field of an attribute.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the field of 'title' with the name for the attribute
	 *            (this argument) 'title'. If X contains object Y with an
	 *            attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the field of 'age' with 'y.age'. The dot separates
	 *            each bean and you can make the navigation as deep as you want.
	 *            'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson', and finally 'getName' method to retrieve the
	 *            field. You can also navigate through arrays, collections and
	 *            maps. Examples: 'agenda.friends[0].name' invokes 'getAgenda'
	 *            first, after that 'getFriends' and the element that exists in
	 *            the '0' position of the collection or array that it returns,
	 *            and finally the 'getName' methods in the object that exists in
	 *            that position of the array or collection. For maps, the value
	 *            inside of '[' and ']' must exists between single quotes.
	 *            Example: "agenda.friends['john'].name" retrieves this time
	 *            from the 'friends' attribute a <code>java.util.Map</code> and
	 *            uses 'john' as the key of the map to retrieve the value.
	 * @return The field that represents an attribute in an object.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Field getAttributeField(Object bean, String attribute)
			throws NoSuchFieldException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		// Get the object that is referenced by the name of the attribute.
		Object holder = extractAttributeHolder(bean, attribute);

		// Extract the last attribute name.
		String lastAttribute = lastStringAttribute(attribute);

		// Validate if the name of the attribute references a collection.
		if (isStringCollection(lastAttribute)) {

			// Gets the name of the attribute for the collection.
			String newAttribute = lastAttribute.substring(0,
					lastAttribute.indexOf('['));

			// Get the field for the attribute.
			return getField(bean.getClass(), newAttribute);

		} else {
			return ReflectionL2Helper
					.getField(holder.getClass(), lastAttribute);
		}

	}

	/**
	 * Gets the annotation of an attribute.
	 * 
	 * @param bean
	 *            Object where to retrieve the annotation of an attribute.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the annotation of 'title' with the name for the
	 *            attribute (this argument) 'title'. If X contains object Y with
	 *            an attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the annotation of 'age' with 'y.age'. The dot
	 *            separates each bean and you can make the navigation as deep as
	 *            you want. 'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson', and finally 'getName' method to retrieve the
	 *            annotation. You can also navigate through arrays, collections
	 *            and maps. Examples: 'agenda.friends[0].name' invokes
	 *            'getAgenda' first, after that 'getFriends' and the element
	 *            that exists in the '0' position of the collection or array
	 *            that it returns, and finally the 'getName' methods in the
	 *            object that exists in that position of the array or
	 *            collection. For maps, the value inside of '[' and ']' must
	 *            exists between single quotes. Example:
	 *            "agenda.friends['john'].name" retrieves this time from the
	 *            'friends' attribute a <code>java.util.Map</code> and uses
	 *            'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to retrieve.<br>
	 * <br>
	 * @return Annotation of the attibute that exists in a bean. If annotation
	 *         is not present then this method returns <code>null</code>.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 */
	public static Annotation getAttributeAnnotation(Object bean,
			String attribute, Class<? extends Annotation> annotationType)
			throws NoSuchFieldException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		// Get the attribute.
		Field field = getAttributeField(bean, attribute);

		// Return the annotation.
		if (field.isAnnotationPresent(annotationType)) {
			return field.getAnnotation(annotationType);
		} else {
			return null;
		}

	}

	/**
	 * Gets the value of a parameter in an annotation that exists in an
	 * attribute of a Java Bean.
	 * 
	 * @param bean
	 *            Object where to retrieve the annotation of an attribute.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the annotation of 'title' with the name for the
	 *            attribute (this argument) 'title'. If X contains object Y with
	 *            an attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the annotation of 'age' with 'y.age'. The dot
	 *            separates each bean and you can make the navigation as deep as
	 *            you want. 'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson', and finally 'getName' method to retrieve the
	 *            annotation. You can also navigate through arrays, collections
	 *            and maps. Examples: 'agenda.friends[0].name' invokes
	 *            'getAgenda' first, after that 'getFriends' and the element
	 *            that exists in the '0' position of the collection or array
	 *            that it returns, and finally the 'getName' methods in the
	 *            object that exists in that position of the array or
	 *            collection. For maps, the value inside of '[' and ']' must
	 *            exists between single quotes. Example:
	 *            "agenda.friends['john'].name" retrieves this time from the
	 *            'friends' attribute a <code>java.util.Map</code> and uses
	 *            'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation where to search for the value.<br>
	 * <br>
	 * @param annotationParameter
	 *            Parameter of the annotation where to retrieve the value.<br>
	 * <br>
	 * @return Value of the annotation's parameter in the attribute of the bean.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getAttributeAnnotationValue(Object bean,
			String attribute, Class<? extends Annotation> annotationType,
			String annotationParameter) throws NoSuchFieldException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Get the annotation of the attribute of the bean.
		Annotation annotation = getAttributeAnnotation(bean, attribute,
				annotationType);

		// Return the value of the parameter in the annotation.
		return invokeMethod(annotationParameter, annotation);

	}

	/**
	 * Gets the value of a parameter in an annotation that exists in an
	 * attribute of a Java Bean.
	 * 
	 * @param type
	 *            Object where to retrieve the annotation of an attribute.<br>
	 * <br>
	 * @param attribute
	 *            Attribute of the object where the annotation exists.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation where to search for the value.<br>
	 * <br>
	 * @param annotationParameter
	 *            Parameter of the annotation where to retrieve the value.<br>
	 * <br>
	 * @return Annotation parameter value.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws SecurityException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getAttributeAnnotationValue(Class<?> type,
			String attribute, Class<? extends Annotation> annotationType,
			String annotationParameter) throws NoSuchFieldException,
			SecurityException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Get the attribute.
		Field field = type.getDeclaredField(attribute);

		// Get the annotation of the attribute of the bean.
		Annotation annotation = field.getAnnotation(annotationType);

		// Return the value of the parameter in the annotation.
		return invokeMethod(annotationParameter, annotation);

	}

	/**
	 * Gets the value of an attribute in a Java Bean when the attribute holds a
	 * given annotation.
	 * 
	 * @param bean
	 *            Object where to retrieve the value of the attribute.<br>
	 * <br>
	 * @param annotationType
	 *            Annotation to look for in the attributes.<br>
	 * <br>
	 * @return The value of the first attribute found with the given annotation.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getAttributeValueWithAnnotation(Object bean,
			Class<? extends Annotation> annotationType)
			throws NoSuchFieldException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		// Get the attributes of the object.
		String[] attributeNames = getAttributes(bean.getClass());

		// Search for the attribute with given entity.
		if (attributeNames != null) {
			for (int i = 0; i < attributeNames.length; i++) {

				// Get one attribute name.
				String attributeName = attributeNames[i];

				// Validate that attribute has given entity and return the value
				// of
				// the attribute if so.
				if (existsAttributeAnnotation(bean, attributeName,
						annotationType)) {
					return getAttributeValue(bean, attributeName);
				}

			}
		}

		// At this point, attibute with given entity is not found.
		return null;

	}

	/**
	 * Gets the name of an attribute in a class when the attribute holds a given
	 * annotation.
	 * 
	 * @param type
	 *            Class where to retrieve the name of the attribute.<br>
	 * <br>
	 * @param annotationType
	 *            Annotation to look for in the attributes.<br>
	 * <br>
	 * @param <T>
	 *            The type of the annotation.<br>
	 * <br>
	 * @return The name of the first attribute found with the given annotation.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the name.<br>
	 * <br>
	 * @throws SecurityException
	 *             If there is an error when trying to get the name.<br>
	 * <br>
	 */
	public static <T extends Annotation> String getAttributeNameWithAnnotation(
			Class<?> type, Class<T> annotationType)
			throws NoSuchFieldException, SecurityException {

		// Get the attributes of the object.
		String[] attributeNames = getAttributes(type);

		// Search for the attribute with given entity.
		for (int i = 0; i < attributeNames.length; i++) {

			// Get one attribute name.
			String attributeName = attributeNames[i];

			// Get the field.
			Field field = type.getDeclaredField(attributeName);

			// Validate field contains given annotation.
			if ((field != null)
					&& (field.getAnnotation(annotationType) != null)) {
				return attributeName;
			}

		}

		// At this point, attibute with given entity is not found.
		return null;

	}

	/**
	 * Decides if an annotation exists in an attribute.
	 * 
	 * @param bean
	 *            Object where to search for the annotation of an attribute.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the annotation of 'title' with the name for the
	 *            attribute (this argument) 'title'. If X contains object Y with
	 *            an attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the annotation of 'age' with 'y.age'. The dot
	 *            separates each bean and you can make the navigation as deep as
	 *            you want. 'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson', and finally 'getName' method to retrieve the
	 *            annotation. You can also navigate through arrays, collections
	 *            and maps. Examples: 'agenda.friends[0].name' invokes
	 *            'getAgenda' first, after that 'getFriends' and the element
	 *            that exists in the '0' position of the collection or array
	 *            that it returns, and finally the 'getName' methods in the
	 *            object that exists in that position of the array or
	 *            collection. For maps, the value inside of '[' and ']' must
	 *            exists between single quotes. Example:
	 *            "agenda.friends['john'].name" retrieves this time from the
	 *            'friends' attribute a <code>java.util.Map</code> and uses
	 *            'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @param annotation
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists and false if not.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 */
	public static boolean existsAttributeAnnotation(Object bean,
			String attribute, Class<? extends Annotation> annotation)
			throws NoSuchFieldException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		// Get the attribute.
		Field field = getAttributeField(bean, attribute);

		// Decide if the annotation exists.
		return (field.isAnnotationPresent(annotation));

	}

	/**
	 * Decides if a given annotation exists just one time in any of the
	 * attributes of a given class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists in any of the
	 *         attributes of the class just one time and false if not.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the annotation.<br>
	 * <br>
	 */
	public static boolean existsAttributeAnnotationOnlyOnce(Class<?> clazz,
			Class<? extends Annotation> annotationType)
			throws NoSuchMethodException {

		// Get every field.
		Field[] fields = clazz.getFields();

		// Occurrences counter.
		int counter = 0;

		// Count occurrences.
		if (fields != null) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(annotationType)) {

					// Increase counter.
					counter = counter + 1;

					// More than one occurrence? If so, return false.
					if (counter > 1) {
						return false;
					}

				}
			}
		}

		// Return if 0 or 1 occurrences.
		return (counter == 1);

	}

	/**
	 * Gets the methods associated with an attribute ('set' and 'is'/'get'
	 * methods). If 'is' and 'get' methods are found, the 'is' method has
	 * preference.
	 * 
	 * @param bean
	 *            Object where to extract the bean methods of an attribute.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the methods of 'title' with the name for the attribute
	 *            (this argument) 'title'. If X contains object Y with an
	 *            attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the methods of 'age' with 'y.age'. The dot separates
	 *            each bean and you can make the navigation as deep as you want.
	 *            'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson', and finally 'getName' method to retrieve the
	 *            methods. You can also navigate through arrays, collections and
	 *            maps. Examples: 'agenda.friends[0].name' invokes 'getAgenda'
	 *            first, after that 'getFriends' and the element that exists in
	 *            the '0' position of the collection or array that it returns,
	 *            and finally the 'getName' methods in the object that exists in
	 *            that position of the array or collection. For maps, the value
	 *            inside of '[' and ']' must exists between single quotes.
	 *            Example: "agenda.friends['john'].name" retrieves this time
	 *            from the 'friends' attribute a <code>java.util.Map</code> and
	 *            uses 'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @return The methods associated with an attribute that exists in a bean.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the methods.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the methods.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the methods.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the methods.<br>
	 * <br>
	 */
	public static Method[] getAttributeMethods(Object bean, String attribute)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, NoSuchFieldException {

		// Get the object that is referenced by the name of the attribute.
		Object holder = extractAttributeHolder(bean, attribute);

		// Extract the last attribute name.
		String lastAttribute = lastStringAttribute(attribute);

		// Gets the name of the attribute if it is a collection.
		if (lastAttribute.indexOf('[') > 0) {
			lastAttribute = lastAttribute.substring(0,
					lastAttribute.indexOf('['));
		}

		// Get the class of the method
		Class<?> methodClass = holder.getClass();

		// Get the IS method.
		Method getMethod = null;
		try {
			getMethod = methodClass.getMethod(CommonValueL2Constants.STRING_IS
					+ StringL2Helper.firstLetterToUpperCase(lastAttribute),
					METHOD_EMPTY_ARGUMENT_TYPES);
		} catch (Exception e) {
			// DO NOTHING!!!
		}

		// Get the GET method.
		if (getMethod == null) {
			getMethod = methodClass.getMethod(CommonValueL2Constants.STRING_GET
					+ StringL2Helper.firstLetterToUpperCase(lastAttribute),
					METHOD_EMPTY_ARGUMENT_TYPES);
		}

		// Get the SET method.
		Method setMethod = methodClass.getMethod(
				CommonValueL2Constants.STRING_SET
						+ StringL2Helper.firstLetterToUpperCase(lastAttribute),
				new Class[] { getAttributeClass(bean, attribute) });

		// Return the methods.
		return new Method[] { getMethod, setMethod };

	}

	/**
	 * Gets the 'get' or 'is' method for an attribute that exists in a bean.
	 * 
	 * @param bean
	 *            Object where to extract the 'get' or 'is' method of an
	 *            attribute.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the method of 'title' with the name for the attribute
	 *            (this argument) 'title'. If X contains object Y with an
	 *            attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the method of 'age' with 'y.age'. The dot separates
	 *            each bean and you can make the navigation as deep as you want.
	 *            'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson', and finally 'getName' method to retrieve the
	 *            method. You can also navigate through arrays, collections and
	 *            maps. Examples: 'agenda.friends[0].name' invokes 'getAgenda'
	 *            first, after that 'getFriends' and the element that exists in
	 *            the '0' position of the collection or array that it returns,
	 *            and finally the 'getName' method in the object that exists in
	 *            that position of the array or collection. For maps, the value
	 *            inside of '[' and ']' must exists between single quotes.
	 *            Example: "agenda.friends['john'].name" retrieves this time
	 *            from the 'friends' attribute a <code>java.util.Map</code> and
	 *            uses 'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @return The 'get' or 'is' method for an attribute that exists in a bean.
	 *         If it does not exists then this method returns <code>null</code>.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static Method getAttributeGetter(Object bean, String attribute)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, NoSuchFieldException {

		// Get the methods associated with an attribute ('set' and 'is'/'get'
		// methods). If 'is' and 'get' methods are found, the 'is' method has
		// preference.
		Method[] methods = getAttributeMethods(bean, attribute);

		// Search for the 'get' method.
		for (int i = 0; i < methods.length; i++) {

			// Get a method.
			Method method = methods[i];

			// Validate if it's the 'get' method.
			if ((method.getName().startsWith(CommonValueL2Constants.STRING_IS))
					|| (method.getName()
							.startsWith(CommonValueL2Constants.STRING_GET))) {
				return method;
			}

		}

		// Nothing to return.
		return null;

	}

	/**
	 * Gets the 'set' method for an attribute that exists in a bean.
	 * 
	 * @param bean
	 *            Object where to extract the 'set' method of an attribute.<br>
	 * <br>
	 * @param attribute
	 *            Name of the property that exists in the bean. You can navigate
	 *            through the beans using the dot notation. Examples: if object
	 *            X has a property named 'title' and the method 'getTitle', you
	 *            can get the method of 'title' with the name for the attribute
	 *            (this argument) 'title'. If X contains object Y with an
	 *            attribute named 'y' and Y contains the attribute 'age', you
	 *            can get the method of 'age' with 'y.age'. The dot separates
	 *            each bean and you can make the navigation as deep as you want.
	 *            'travel.person.name' invokes 'getTravel' first, then
	 *            'getPerson', and finally 'getName' method to retrieve the
	 *            method. You can also navigate through arrays, collections and
	 *            maps. Examples: 'agenda.friends[0].name' invokes 'getAgenda'
	 *            first, after that 'getFriends' and the element that exists in
	 *            the '0' position of the collection or array that it returns,
	 *            and finally the 'getName' method in the object that exists in
	 *            that position of the array or collection. For maps, the value
	 *            inside of '[' and ']' must exists between single quotes.
	 *            Example: "agenda.friends['john'].name" retrieves this time
	 *            from the 'friends' attribute a <code>java.util.Map</code> and
	 *            uses 'john' as the key of the map to retrieve the value.<br>
	 * <br>
	 * @return The 'set' method for an attribute that exists in a bean. If it
	 *         does not exists then this method returns <code>null</code>.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 * @throws NoSuchFieldException
	 *             If there is an error when trying to get the method.<br>
	 * <br>
	 */
	public static Method getAttributeSetter(Object bean, String attribute)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, NoSuchFieldException {

		// Get the methods associated with an attribute ('set' and 'is'/'get'
		// methods). If 'is' and 'get' methods are found, the 'is' method has
		// preference.
		Method[] methods = getAttributeMethods(bean, attribute);

		// Search for the 'set' method.
		for (int i = 0; i < methods.length; i++) {

			// Get a method.
			Method method = methods[i];

			// Validate if it's the ' set' method.
			if (method.getName().startsWith(CommonValueL2Constants.STRING_SET)) {
				return method;
			}

		}

		// Nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Decides if a given annotation exists in a class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to search for.<br>
	 * <br>
	 * @return <code>true</code> if the annotation exists and false if not.<br>
	 * <br>
	 */
	public static boolean existsClassAnnotation(Class<?> clazz,
			Class<? extends Annotation> annotationType) {
		return clazz.isAnnotationPresent(annotationType);
	}

	/**
	 * Gets the annotation of a class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation to retrieve.<br>
	 * <br>
	 * @return Annotation that exists in a class. If annotation is not present
	 *         then this method returns <code>null</code>.<br>
	 * <br>
	 */
	public static Annotation getClassAnnotation(Class<?> clazz,
			Class<? extends Annotation> annotationType) {
		if (clazz.isAnnotationPresent(annotationType)) {
			return clazz.getAnnotation(annotationType);
		} else {
			return null;
		}
	}

	/**
	 * Gets the value of a parameter in an annotation that exists in a given
	 * class.
	 * 
	 * @param clazz
	 *            Class where to search for the annotation.<br>
	 * <br>
	 * @param annotationType
	 *            Type of the annotation where to search for the value.<br>
	 * <br>
	 * @param annotationParameter
	 *            Parameter of the annotation where to retrieve the value.<br>
	 * <br>
	 * @return Value of the annotation's parameter in a class.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	public static Object getClassAnnotationValue(Class<?> clazz,
			Class<? extends Annotation> annotationType,
			String annotationParameter) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		// Get the annotation.
		Annotation annotation = getClassAnnotation(clazz, annotationType);

		// Return the value of the parameter in the annotation.
		return invokeMethod(annotationParameter, annotation);

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a getter method for a given attribute.
	 * 
	 * @param attribute
	 *            Name of the attribute to use for the method.<br>
	 * <br>
	 * @return Name of the get method for the attribute.<br>
	 * <br>
	 */
	protected static String createGetMethodName(String attribute) {
		return createMethodName(attribute, CommonValueL2Constants.STRING_GET);
	}

	/**
	 * Creates a 'is' method for a given attribute.
	 * 
	 * @param attribute
	 *            Name of the attribute to use for the method.<br>
	 * <br>
	 * @return Name of the 'is' method for the attribute.<br>
	 * <br>
	 */
	protected static String createIsMethodName(String attribute) {
		return createMethodName(attribute, CommonValueL2Constants.STRING_IS);
	}

	/**
	 * Creates a setter method for a given attribute.
	 * 
	 * @param attribute
	 *            Name of the attribute to use for the method.<br>
	 * <br>
	 * @return Name of the set method for the attribute.<br>
	 * <br>
	 */
	protected static String createSetMethodName(String attribute) {
		return createMethodName(attribute, CommonValueL2Constants.STRING_SET);
	}

	/**
	 * Creates a setter/getter/is method for a given attribute.
	 * 
	 * @param attribute
	 *            Name of the attribute to use for the method.<br>
	 * <br>
	 * @param operation
	 *            Operation type (set/get/is).<br>
	 * <br>
	 * @return Name of the method for the attribute.<br>
	 * <br>
	 */
	protected static String createMethodName(String attribute, String operation) {
		return operation + StringL2Helper.firstLetterToUpperCase(attribute);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates an array of fields where any of the field returned represents the
	 * 'this&amp;' instance.
	 * 
	 * @param fields
	 *            Object fields.<br>
	 * <br>
	 * @return A new array without the 'this&amp;' instance.<br>
	 * <br>
	 */
	protected static Field[] removeDefaultInstances(Field[] fields) {

		// Prepare the result.
		Field[] result = null;

		//
		if (fields.length > 0) {

			// Create a temporal placeholder.
			Set<Field> fieldsFound = new HashSet<Field>();

			// Move the fields that do not represent the 'this$' instance to the
			// result.
			for (int i = 0; i < fields.length; i++) {

				// Get one field.
				Field field = fields[i];

				// Get the name of the filed.
				String fieldName = field.getName();

				// Copy the field.
				if ((!fieldName.startsWith(FIELD_NAME_THIS))
						&& (!fieldName.startsWith(FIELD_NAME_CLASS))
						&& (!fieldName
								.startsWith(FIELD_NAME_SERIAL_VERSION_UID))) {
					fieldsFound.add(field);
				}

			}

			// Transform the collection into an array.
			result = (Field[]) DataStructureL2Helper
					.toArray(fieldsFound, false);

		} else {
			result = new Field[0];
		}

		// Return the fields.
		if (result != null) {
			return result;
		} else {
			return new Field[] {};
		}

	}

	/**
	 * Gets the object that is referenced by the name of the attribute.
	 * 
	 * @param bean
	 *            Object where to search for the object that indicates the name
	 *            of the attribute.<br>
	 * <br>
	 * @param attribute
	 *            String that represents the location of the object.<br>
	 * <br>
	 * @return Object that is referenced by the name of the attribute.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	@SuppressWarnings("rawtypes")
	protected static Object extractAttributeHolder(Object bean, String attribute)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Search for the first '.' character in the name of the attribute.
		int dotIndex = attribute.indexOf(CommonValueL2Constants.CHAR_PERIOD);

		// Search for the first '[' character in the name of the attribute.
		int openBracketIndex = attribute.indexOf('[');

		// Decide how to process the name of the attribute.
		if ((dotIndex != -1) && (openBracketIndex != -1)) {

			if (dotIndex < openBracketIndex) {

				// Extract the name of the atttribute.
				String newAttribute = attribute.substring(0, dotIndex);

				// Get the next bean to process.
				Object newBean = invokeGetMethod(newAttribute, bean);

				// Process the next bean.
				return extractAttributeHolder(newBean,
						attribute.substring(dotIndex + 1, attribute.length()));

			} else {

				// Extract the name of the atttribute.
				String newAttribute = attribute.substring(0, openBracketIndex);

				// Get the next bean to process.
				Object newBean = invokeGetMethod(newAttribute, bean);

				// Validate that the position of closing bracket.
				if (attribute.indexOf(']') < openBracketIndex) {
					throw new IllegalAccessException();
				}

				// Remove the name of the attribute.
				String newAttributeRemoved = attribute.substring(
						openBracketIndex, attribute.length());

				// Get the name of the new attribute to process.
				newAttribute = newAttributeRemoved
						.substring(
								newAttributeRemoved
										.indexOf(CommonValueL2Constants.CHAR_PERIOD) + 1,
								newAttributeRemoved.length());

				// Validate the collection type.
				if ((newBean instanceof Collection) || (isArray(newBean))) {

					// Get the index of the collection/array.
					int index = Integer.parseInt(StringL2Helper
							.leaveDigits(newAttributeRemoved.substring(1,
									newAttributeRemoved.indexOf(']'))));

					// Return the object for the new attribute.
					return extractAttributeHolder(
							DataStructureL2Helper.get(newBean, index),
							newAttribute);

				} else if (newBean instanceof Map) {

					// Extract the key of the map.
					String key = StringL2Helper
							.removeSpaces(newAttributeRemoved
									.substring(1,
											newAttributeRemoved.indexOf(']'))
									.replace('\'', ' ').replace('\"', ' '));

					// Return the object for the new attribute.
					return extractAttributeHolder(((Map) newBean).get(key),
							newAttribute);

				} else {
					throw new IllegalAccessException();
				}

			}

		} else if (dotIndex != -1) {

			// Extract the name of the attribute.
			String newAttribute = attribute.substring(0, dotIndex);

			// Get the new bean.
			Object newBean = invokeGetMethod(newAttribute, bean);

			// Search again for the next attribute.
			return extractAttributeHolder(newBean,
					attribute.substring(dotIndex + 1, attribute.length()));

		} else {
			return bean;
		}

	}

	/**
	 * Gets the last attribute name.
	 * 
	 * @param attribute
	 *            Full attribute name.<br>
	 * <br>
	 * @return The last attribute name.<br>
	 * <br>
	 */
	protected static String lastStringAttribute(String attribute) {

		// Search for the last '.' character.
		int dotIndex = attribute
				.lastIndexOf(CommonValueL2Constants.CHAR_PERIOD);

		// Setup a default value for the index if required.
		if (dotIndex < 0) {
			dotIndex = -1;
		}

		// Return the last attribute name.
		return attribute.substring(dotIndex + 1, attribute.length());

	}

	/**
	 * Decides if the name of the attribute references a collection.
	 * 
	 * @param attribute
	 *            Attribute name.<br>
	 * <br>
	 * @return <code>true</code> if the attribute points to a collection and
	 *         false if not.<br>
	 * <br>
	 */
	protected static boolean isStringCollection(String attribute) {

		// Search for the '[' character.
		int openBracketIndex = attribute.indexOf('[');

		// Validate if the character exists.
		return (openBracketIndex >= 0);

	}

	/**
	 * Gets the value that exists in a collection represented by an attribute.
	 * 
	 * @param bean
	 *            Object where to get the value.<br>
	 * <br>
	 * @param attribute
	 *            Attribute that represents a collection.<br>
	 * <br>
	 * @return Value of the collection.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	@SuppressWarnings("rawtypes")
	protected static Object getCollectionValue(Object bean, String attribute)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Get the collection or array.
		Object newBean = getCollectionBean(bean, attribute);

		// Get the value.
		if ((newBean instanceof Collection) || (isArray(newBean))) {
			return DataStructureL2Helper.get(newBean,
					getCollectionIndex(attribute));
		} else if (newBean instanceof Map) {
			return ((Map) newBean).get(getCollectionKey(attribute));
		} else {
			throw new IllegalAccessException();
		}

	}

	/**
	 * Sets the value in a collection represented by an attribute.
	 * 
	 * @param bean
	 *            Object where to set the value.<br>
	 * <br>
	 * @param attribute
	 *            Attribute that represents a collection.<br>
	 * <br>
	 * @param value
	 *            Value to set in the collection.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to set the value.<br>
	 * <br>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static void setCollectionValue(Object bean, String attribute,
			Object value) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Get the collection or array.
		Object newBean = getCollectionBean(bean, attribute);

		// Set the value.
		if ((newBean instanceof Collection) || (isArray(newBean))) {
			DataStructureL2Helper.set(newBean, getCollectionIndex(attribute),
					value);
		} else if (newBean instanceof Map) {
			((Map) newBean).put(getCollectionKey(attribute), value);
		} else {
			throw new IllegalAccessException();
		}

	}

	/**
	 * Gets the bean that holds a collection.
	 * 
	 * @param bean
	 *            Object where to get the value.<br>
	 * <br>
	 * @param attribute
	 *            Attribute that represents a collection.<br>
	 * <br>
	 * @return An object that holds a collection.<br>
	 * <br>
	 * @throws NoSuchMethodException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 * @throws InvocationTargetException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	protected static Object getCollectionBean(Object bean, String attribute)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		// Get the name of the attribute.
		String newAttribute = attribute.substring(0, attribute.indexOf('['));

		// Return the object.
		return invokeGetMethod(newAttribute, bean);

	}

	/**
	 * Gets the index for a collection/array that exists in the name of an
	 * attribute.
	 * 
	 * @param attribute
	 *            Attribute that represents a collection.<br>
	 * <br>
	 * @return Index inside the '[' and ']' characters.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	protected static int getCollectionIndex(String attribute)
			throws IllegalAccessException {

		// Get the index.
		String indexString = getCollectionIndexString(attribute);

		// Return the value of the index.
		return Integer.parseInt(StringL2Helper.leaveDigits(indexString
				.substring(1, indexString.indexOf(']'))));

	}

	/**
	 * Gets the key for a map that exists in the name of an attribute.
	 * 
	 * @param attribute
	 *            Attribute that represents a map.<br>
	 * <br>
	 * @return Key inside the "['" and "']" characters.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	protected static String getCollectionKey(String attribute)
			throws IllegalAccessException {

		// Get the index.
		String indexString = getCollectionIndexString(attribute);

		// Return the value of the index.
		return StringL2Helper.removeSpaces(indexString
				.substring(1, indexString.indexOf(']')).replace('\'', ' ')
				.replace('\"', ' '));

	}

	/**
	 * Gets the index for an array/collection/map that exists in the name of an
	 * attribute.
	 * 
	 * @param attribute
	 *            Attribute that represents a map.<br>
	 * <br>
	 * @return Key inside the "[" and "]" characters.<br>
	 * <br>
	 * @throws IllegalAccessException
	 *             If there is an error when trying to get the value.<br>
	 * <br>
	 */
	protected static String getCollectionIndexString(String attribute)
			throws IllegalAccessException {

		// Search the beginning.
		int openBracketIndex = attribute.indexOf('[');

		// Search the end.
		int closeBracketIndex = attribute.indexOf(']');
		if (closeBracketIndex < openBracketIndex) {
			throw new IllegalAccessException();
		}

		// Return the index.
		return attribute.substring(openBracketIndex, closeBracketIndex + 1);

	}

}