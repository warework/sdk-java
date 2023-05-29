package com.warework.core.util.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Performs common List, Map, Enumeration and Iterator operations.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public abstract class DataStructureL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected DataStructureL1Helper() {
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Copies the values from one <code>java.util.List</code> to another one.
	 * 
	 * @param source
	 *            Source List where to retrieve the values.<br>
	 * <br>
	 * @param target
	 *            Target List where to copy the values of the source List.<br>
	 * <br>
	 * @param <T>
	 *            The type of the list.<br>
	 * <br>
	 * @return List with the values from the source Vector.<br>
	 * <br>
	 */
	public static <T> List<T> copyList(List<T> source, List<T> target) {

		// Copy the values from the source Vector to the target Vector.
		for (Iterator<T> iterator = source.iterator(); iterator.hasNext();) {

			// Get an element from the source Vector.
			T element = iterator.next();

			// Add the element.
			target.add(element);

		}

		// Return the target Vector.
		return target;

	}

	/**
	 * Copies the values from one <code>java.util.Map</code> to another one.
	 * 
	 * @param source
	 *            Source Map where to retrieve the values.<br>
	 * <br>
	 * @param target
	 *            Target Map where to copy the values of the source Map.<br>
	 * <br>
	 * @param <K>
	 *            The key type of the map.<br>
	 * <br>
	 * @param <V>
	 *            The value type of the map.<br>
	 * <br>
	 * @return Map with the values from the source Map.<br>
	 * <br>
	 */
	public static <K, V> Map<K, V> copyMap(Map<K, V> source, Map<K, V> target) {

		// Get the keys from the source Map.
		Set<K> sourceKeys = source.keySet();

		// Copy the values from the source Map to the target Map.
		for (Iterator<K> iterator = sourceKeys.iterator(); iterator.hasNext();) {

			// Get a key from the source Map.
			K key = iterator.next();

			// Copy the value from the source Map to the target Map.
			target.put(key, source.get(key));

		}

		// Return the target Map.
		return target;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Copies the values of a <code>java.util.List</code> into a new one only if
	 * values match given type.
	 * 
	 * @param source
	 *            Source Vector where to retrieve the values.<br>
	 * <br>
	 * @param type
	 *            Type of the value. The value of each position in the Vector
	 *            must be the same to be included in the result.<br>
	 * <br>
	 * @return A new <code>java.util.ArrayList</code>. Null if no value in the
	 *         source Vector match given type.<br>
	 * <br>
	 */
	public static List<Object> filterByType(List<Object> source, Class<?> type) {

		// Filter only if parameters exist.
		if ((source != null) && (source.size() > 0)) {

			// Create a vector where to store good values.
			List<Object> filtered = new ArrayList<Object>();

			// Search for good values.
			for (int index = 0; index < source.size(); index++) {

				// Get one object.
				Object object = source.get(index);

				// Use it only if it matches the type.
				if (ReflectionL1Helper.isType(object.getClass(), type)) {
					filtered.add(object);
				}

			}

			// Return the values.
			if (filtered.size() > 0) {
				return filtered;
			}

		}

		// At this point, nothing to return.
		return null;

	}

	/**
	 * Copies the values of a <code>java.util.Map</code> into a new
	 * <code>java.util.HashMap</code> only if keys and values match given types.
	 * 
	 * @param source
	 *            Source Map where to retrieve the values.<br>
	 * <br>
	 * @param keyType
	 *            Type of the key. The key of the Map must be the same to be
	 *            included in the result.<br>
	 * <br>
	 * @param valueType
	 *            Type of the value. The value of the Map must be the same to be
	 *            included in the result.<br>
	 * <br>
	 * @return A new <code>java.util.HashMap</code>. Null if no key-value in the
	 *         source Map match given types.<br>
	 * <br>
	 */
	public static Map<Object, Object> filterByType(Map<Object, Object> source,
			Class<?> keyType, Class<?> valueType) {

		// Filter only if parameters exist.
		if ((source != null) && (source.size() > 0)) {

			// Get the keys.
			Set<Object> parameterKeys = source.keySet();

			// Create a map where to store good values.
			Map<Object, Object> filtered = new HashMap<Object, Object>();

			// Search for good values.
			for (Iterator<Object> iterator = parameterKeys.iterator(); iterator
					.hasNext();) {

				// Get one key.
				Object parameterKey = iterator.next();

				// Use it only if it matches the type.
				if (ReflectionL1Helper.isType(parameterKey.getClass(), keyType)) {

					// Get the value.
					Object parameterValue = source.get(parameterKey);

					// Use it only if it matches the type.
					if (ReflectionL1Helper.isType(parameterValue.getClass(),
							valueType)) {
						filtered.put(parameterKey, parameterValue);
					}

				}

			}

			// Return the values.
			if (filtered.size() > 0) {
				return filtered;
			}

		}

		// At this point, nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes duplicated objects in a <code>java.util.List</code>.
	 * 
	 * @param list
	 *            List where to search for the objects.<br>
	 * <br>
	 * @param <T>
	 *            The type of the list.<br>
	 * <br>
	 * @return A new <code>java.util.List</code> without dupplicated objects of
	 *         the given list.<br>
	 * <br>
	 */
	public static <T> List<T> removeDuplicated(List<T> list) {

		// Collection for objects found.
		List<T> result = new ArrayList<T>();

		// Search objects to delete.
		for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {

			// Get an object.
			T object = iterator.next();

			// Add if it does not exists.
			if (!result.contains(object)) {
				result.add(object);
			}

		}

		// Return the collections without duplicated objects.
		return result;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Updates the value of each key in a map with a given value.
	 * 
	 * @param source
	 *            Source Map where to retrieve the keys.<br>
	 * <br>
	 * @param value
	 *            Value to set for each key in the output Map.<br>
	 * <br>
	 * @param <K>
	 *            The key type of the map.<br>
	 * <br>
	 * @param <V>
	 *            The value type of the map.<br>
	 * <br>
	 * @return A new <code>java.util.HashMap</code> where each key have the same
	 *         value.<br>
	 * <br>
	 */
	public static <K, V> Map<K, V> updateValues(Map<K, V> source, V value) {

		// Copy the source map.
		Map<K, V> values = new HashMap<K, V>(source);

		// Get the keys of the source map.
		Set<K> keys = source.keySet();

		// Update each value.
		for (Iterator<K> iterator = keys.iterator(); iterator.hasNext();) {

			// Get one key from the source map.
			K key = iterator.next();

			// Update the value in the target map.
			values.put(key, value);

		}

		// Return the new map.
		return values;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Merges the names (string values) of two enumerations and remove
	 * duplicated entries.
	 * 
	 * @param enum1
	 *            First enumeration. If it is <code>null</code> then parameter
	 *            <code>enum2</code> is returned. If both parameters are
	 *            <code>null</code> then <code>null</code> is returned.<br>
	 * <br>
	 * @param enum2
	 *            Second enumeration. If it is <code>null</code> then parameter
	 *            <code>enum1</code> is returned. If both parameters are
	 *            <code>null</code> then <code>null</code> is returned.<br>
	 * <br>
	 * @param <T>
	 *            The enumeration type.<br>
	 * <br>
	 * @return Names of the first and second enumeration. <code>null</code> if
	 *         both given enumerations are empty or <code>null</code>.<br>
	 * <br>
	 */
	public static <T> Enumeration<T> mergeNames(Enumeration<T> enum1,
			Enumeration<T> enum2) {

		// Transform the enumeration into a vector.
		List<T> names1 = null;
		if (enum1 != null) {
			names1 = toArrayList(enum1);
		}

		// Copy second enumeration only if it exists.
		if (enum2 != null) {

			// Transform the enumeration to a vector.
			List<T> names2 = toArrayList(enum2);

			// Merge enumerations.
			if ((names1 != null) && (names1.size() > 0)) {

				// Copy parent's providers with the providers of this
				// system.
				copyList(names2, names1);

				// Remove duplicate names.
				names1 = removeDuplicated(names1);

			} else {
				names1 = names2;
			}

		}

		// Transform the vector to an enumeration.
		if ((names1 != null) && (names1.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(names1);
		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Merges the names (string values) of two enumerations and remove
	 * duplicated entries.
	 * 
	 * @param iter1
	 *            First enumeration. If it is <code>null</code> then parameter
	 *            <code>enum2</code> is returned. If both parameters are
	 *            <code>null</code> then <code>null</code> is returned.<br>
	 * <br>
	 * @param iter2
	 *            Second enumeration. If it is <code>null</code> then parameter
	 *            <code>enum1</code> is returned. If both parameters are
	 *            <code>null</code> then <code>null</code> is returned.<br>
	 * <br>
	 * @param <T>
	 *            The type of the iterators.<br>
	 * <br>
	 * @return Names of the first and second enumeration. <code>null</code> if
	 *         both given enumerations are empty or <code>null</code>.<br>
	 * <br>
	 */
	public static <T> Iterator<T> mergeNames(Iterator<T> iter1,
			Iterator<T> iter2) {

		// Transform the enumeration into a vector.
		List<T> names1 = null;
		if (iter1 != null) {
			names1 = toArrayList(iter1);
		}

		// Copy second enumeration only if it exists.
		if (iter2 != null) {

			// Transform the enumeration to a vector.
			List<T> names2 = toArrayList(iter2);

			// Merge enumerations.
			if ((names1 != null) && (names1.size() > 0)) {

				// Copy parent's providers with the providers of this
				// system.
				copyList(names2, names1);

				// Remove duplicate names.
				names1 = removeDuplicated(names1);

			} else {
				names1 = names2;
			}

		}

		// Transform the vector to an enumeration.
		if ((names1 != null) && (names1.size() > 0)) {
			return names1.iterator();
		}

		// Nothing to return at this point.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Converts a <code>java.util.Collection</code> into an
	 * <code>java.util.Enumeration</code>.
	 * 
	 * @param collection
	 *            Source collection to transform.<br>
	 * <br>
	 * @param <T>
	 *            The type of the collection.<br>
	 * <br>
	 * @return Enumeration with the values of the List.<br>
	 * <br>
	 */
	public static <T> Enumeration<T> toEnumeration(Collection<T> collection) {
		return Collections.enumeration(new HashSet<T>(collection));
	}

	/**
	 * Creates a <code>java.util.ArrayList</code> from an array of objects.
	 * 
	 * @param objects
	 *            Array of objects to copy into a new
	 *            <code>java.util.ArrayList</code>.<br>
	 * <br>
	 * @param <T>
	 *            The type of the array.<br>
	 * <br>
	 * @return A new <code>java.util.ArrayList</code> with the values of the
	 *         array.<br>
	 * <br>
	 */
	public static <T> ArrayList<T> toArrayList(T[] objects) {

		// Set up the result.
		ArrayList<T> result = new ArrayList<T>();

		// Copy the values from the array to the list.
		for (int i = 0; i < objects.length; i++) {
			result.add(objects[i]);
		}

		// Return the list.
		return result;

	}

	/**
	 * Creates a <code>java.util.Vector</code> from an array of objects.
	 * 
	 * @param objects
	 *            Array of objects to copy into a new
	 *            <code>java.util.Vector</code>.<br>
	 * <br>
	 * @param <T>
	 *            The type of the array.<br>
	 * <br>
	 * @return A new <code>java.util.Vector</code> with the values of the array.<br>
	 * <br>
	 */
	public static <T> Vector<T> toVector(T[] objects) {

		// Set up the result.
		Vector<T> result = new Vector<T>();

		// Copy the values from the array to the list.
		for (int i = 0; i < objects.length; i++) {
			result.add(objects[i]);
		}

		// Return the list.
		return result;

	}

	/**
	 * Creates a <code>java.util.ArrayList</code> from a
	 * <code>java.util.Iterator</code>.
	 * 
	 * @param iterator
	 *            Source iterator where to get the values.<br>
	 * <br>
	 * @param <T>
	 *            The type of the enumeration.<br>
	 * <br>
	 * @return A new <code>java.util.ArrayList</code> with the values of the
	 *         iterator.<br>
	 * <br>
	 */
	public static <T> ArrayList<T> toArrayList(Enumeration<T> iterator) {

		// Create a new list.
		ArrayList<T> list = new ArrayList<T>();

		// Copy the values of the iterator into the list.
		while (iterator.hasMoreElements()) {

			// Get one element.
			T element = iterator.nextElement();

			// Add the element into the list.
			list.add(element);

		}

		// Return the list.
		return list;

	}

	/**
	 * Creates a <code>java.util.Vector</code> from a
	 * <code>java.util.Enumeration</code>.
	 * 
	 * @param enumeration
	 *            Source enumeration where to get the values.<br>
	 * <br>
	 * @param <T>
	 *            The type of the enumeration.<br>
	 * <br>
	 * @return A new <code>java.util.Vector</code> with the values of the
	 *         enumeration.<br>
	 * <br>
	 */
	public static <T> Vector<T> toVector(Enumeration<T> enumeration) {

		// Create a new vector.
		Vector<T> vector = new Vector<T>();

		// Copy the values of the enumeration into the vector.
		while (enumeration.hasMoreElements()) {

			// Get one value.
			T object = enumeration.nextElement();

			// Add the value into the vector.
			vector.addElement(object);

		}

		// Return the vector.
		return vector;

	}

	/**
	 * Creates a <code>java.util.ArrayList</code> from a
	 * <code>java.util.Iterator</code>.
	 * 
	 * @param iterator
	 *            Source iterator where to get the values.<br>
	 * <br>
	 * @param <T>
	 *            The type of the iterator.<br>
	 * <br>
	 * @return A new <code>java.util.ArrayList</code> with the values of the
	 *         iterator.<br>
	 * <br>
	 */
	public static <T> ArrayList<T> toArrayList(Iterator<T> iterator) {

		// Create a new list.
		ArrayList<T> list = new ArrayList<T>();

		// Copy the values of the iterator into the list.
		while (iterator.hasNext()) {

			// Get one element.
			T element = iterator.next();

			// Add the element into the list.
			list.add(element);

		}

		// Return the list.
		return list;

	}

	/**
	 * Creates a <code>java.util.Hashtable</code> with the keys and values
	 * specified in a string.
	 * 
	 * @param properties
	 *            A string like "[name=John,age=18,friend=David]". In this case,
	 *            the result will be a Hashtable where the keys are "name",
	 *            "age" and "friend" (each one as a string object) and the
	 *            values (for each key) are "John", "18" and "David" (also as
	 *            string objects).<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates two properties (i.e.: ',').<br>
	 * <br>
	 * @return A <code>java.util.Hashtable</code> with the keys and values
	 *         specified in the string.<br>
	 * <br>
	 */
	public static Hashtable<String, String> toHashtable(String properties,
			char delimiter) {

		// Get each property.
		String[] props = StringL1Helper.toStrings(
				properties.substring(1, properties.length() - 1), delimiter);

		// Prepare the result.
		Hashtable<String, String> result = new Hashtable<String, String>();

		// Insert each property in the result.
		for (int i = 0; i < props.length; i++) {

			// Get the property.
			String property = props[i];

			// Get the key of the property.
			String key = StringL1Helper.getPropertyKey(property);

			// Get the value of the property.
			String value = StringL1Helper.getPropertyValue(property);

			// Add the property.
			result.put(key, value);

		}

		// Return the result.
		return result;

	}

	/**
	 * Creates a <code>java.util.HashMap</code> with the keys and values
	 * specified in a string.
	 * 
	 * @param properties
	 *            A string like "[name=John,age=18,friend=David]". In this case,
	 *            the result will be a HashMap where the keys are "name", "age"
	 *            and "friend" (each one as a string object) and the values (for
	 *            each key) are "John", "18" and "David" (also as string
	 *            objects).<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates two properties (i.e.: ',').<br>
	 * <br>
	 * @return A <code>java.util.HashMap</code> with the keys and values
	 *         specified in the string.<br>
	 * <br>
	 */
	public static HashMap<String, String> toHashMap(String properties,
			char delimiter) {

		// Get each property.
		String[] props = StringL1Helper.toStrings(
				properties.substring(1, properties.length() - 1), delimiter);

		// Prepare the result.
		HashMap<String, String> result = new HashMap<String, String>();

		// Insert each property in the result.
		for (int i = 0; i < props.length; i++) {

			// Get the property.
			String property = props[i];

			// Get the key of the property.
			String key = StringL1Helper.getPropertyKey(property);

			// Get the value of the property.
			String value = StringL1Helper.getPropertyValue(property);

			// Add the property.
			result.put(key, value);

		}

		// Return the result.
		return result;

	}

}
