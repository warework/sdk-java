package com.warework.core.util.helper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.warework.core.util.data.ComparableBean;
import com.warework.core.util.data.OrderBy;

/**
 * Performs common array, <code>java.util.Collection</code> and
 * <code>java.util.Map</code> operations.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class DataStructureL2Helper extends DataStructureL1Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected DataStructureL2Helper() {
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the element at the specified position in a collection, array or map.
	 * 
	 * @param objects A collection, array or map data structure. If it's none of
	 *                these you'll get <code>null</code>.<br>
	 *                <br>
	 * @param index   Index of the element to return. For map structures this index
	 *                is used as the key, so keys in maps for this method should be
	 *                of Integer type.<br>
	 *                <br>
	 * @return The element at the specified position.<br>
	 *         <br>
	 */
	public static Object get(final Object objects, final int index) {

		// Get the element.
		if (ReflectionL2Helper.isArray(objects)) {
			return Array.get(objects, index);
		} else if (objects instanceof List<?>) {
			return ((List<?>) objects).get(index);
		} else if (objects instanceof Map<?, ?>) {
			return ((Map<?, ?>) objects).get(Integer.valueOf(index));
		} else if (objects instanceof Set<?>) {

			// Setup a counter to find the object.
			int counter = 0;

			// Search the object.
			for (Iterator<?> iterator = ((Collection<?>) objects).iterator(); iterator.hasNext();) {

				// Get one element.
				Object object = iterator.next();

				// Return the object if the counter matches the index.
				if (counter == index) {
					return object;
				} else {
					counter = counter + 1;
				}

			}

		}

		// Nothing found.
		return null;

	}

	/**
	 * Gets a random element from an array.
	 * 
	 * @param values Array where to get the value from a random position.<br>
	 *               <br>
	 * @return Value from a random position of the array.<br>
	 *         <br>
	 */
	public static <T> T getRandom(final T[] values) {
		return values[(int) (Math.random() * values.length)];
	}

	/**
	 * Sets an element at the specified position in a collection, array or map.
	 * 
	 * @param collection A collection, array or map data structure. If it's none of
	 *                   these then no action is performed.<br>
	 *                   <br>
	 * @param index      Index where to add the element. For set structures this
	 *                   index is not used. For map structures this index is used as
	 *                   the key, so keys in maps for this method should be of
	 *                   Integer type.<br>
	 *                   <br>
	 * @param value      Value to set in the collection, array or map.<br>
	 *                   <br>
	 * @param <T>        The type of the collection.<br>
	 *                   <br>
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> void set(final Object collection, final int index, final T value) {
		if (ReflectionL2Helper.isArray(collection)) {
			Array.set(collection, index, value);
		} else if (collection instanceof List<?>) {
			((List<T>) collection).add(index, value);
		} else if (collection instanceof Map<?, ?>) {
			((Map<Number, T>) collection).put(Integer.valueOf(index), value);
		} else if (collection instanceof Set<?>) {
			((Set<T>) collection).add(value);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static boolean[] mergeArrays(final boolean[] first, final boolean[] second) {

		// Create the array where to copy the values.
		final boolean[] result = new boolean[first.length + second.length];

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static byte[] mergeArrays(final byte[] first, final byte[] second) {

		// Create the array where to copy the values.
		final byte[] result = new byte[first.length + second.length];

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static short[] mergeArrays(final short[] first, final short[] second) {

		// Create the array where to copy the values.
		final short[] result = new short[first.length + second.length];

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static int[] mergeArrays(final int[] first, final int[] second) {

		// Create the array where to copy the values.
		final int[] result = new int[first.length + second.length];

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static long[] mergeArrays(final long[] first, final long[] second) {

		// Create the array where to copy the values.
		final long[] result = new long[first.length + second.length];

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static float[] mergeArrays(final float[] first, final float[] second) {

		// Create the array where to copy the values.
		final float[] result = new float[first.length + second.length];

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static double[] mergeArrays(final double[] first, final double[] second) {

		// Create the array where to copy the values.
		final double[] result = new double[first.length + second.length];

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static char[] mergeArrays(final char[] first, final char[] second) {

		// Create the array where to copy the values.
		final char[] result = new char[first.length + second.length];

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	/**
	 * Merges the values of two arrays.
	 * 
	 * @param first  First array.<br>
	 *               <br>
	 * @param second Second array.<br>
	 *               <br>
	 * @return An array with the values of the first and second array.<br>
	 *         <br>
	 */
	public static <T> T[] mergeArrays(final T[] first, final T[] second) {

		// Create the array where to copy the values.
		@SuppressWarnings("unchecked")
		final T[] result = (T[]) Array.newInstance(ReflectionL2Helper.getArrayType(first),
				first.length + second.length);

		// Position in the array.
		int counter = 0;

		// Copy the values of the first array.
		for (int i = 0; i < first.length; i++) {
			result[counter++] = first[i];
		}

		// Copy the values of the second array.
		for (int i = 0; i < second.length; i++) {
			result[counter++] = second[i];
		}

		// Return the result.
		return result;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sorts a collection of beans and copies the result into a given list.
	 * 
	 * @param source  Source collection where to retrieve the beans to sort.<br>
	 *                <br>
	 * @param orderBy Order clause to apply in the sort operation.<br>
	 *                <br>
	 * @param target  Target collection where to copy the sorted beans.<br>
	 *                <br>
	 */
	public static <T> void sort(final Iterable<T> source, final OrderBy orderBy, final List<T> target) {

		// Create a list where to wrap each bean to compare.
		final List<ComparableBean<T>> comparableBeans = new ArrayList<ComparableBean<T>>();

		// Wrap every bean to compare and include them in the list.
		for (final T bean : source) {
			comparableBeans.add(new ComparableBean<T>(bean, orderBy));
		}

		// Sort the list.
		sort(comparableBeans, target);

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static boolean[] shuffle(final boolean[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final boolean[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final boolean value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static byte[] shuffle(final byte[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final byte[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final byte value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static short[] shuffle(final short[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final short[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final short value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static int[] shuffle(final int[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final int[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final int value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static long[] shuffle(final long[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final long[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final long value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static float[] shuffle(final float[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final float[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final float value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static double[] shuffle(final double[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final double[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final double value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static char[] shuffle(final char[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final char[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final char value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	/**
	 * Randomly sorts the values of an array implementing Fisher–Yates shuffle
	 * algorithm.
	 * 
	 * @param <T>    Type of the array.<br>
	 *               <br>
	 * @param values Array to sort randomly.<br>
	 *               <br>
	 * @param clone  <code>true</code> to make a copy of the given array so provided
	 *               values keep unchanged <code>false</code> to make changes
	 *               directly in given array.
	 * @return Array sorted randomly.
	 */
	public static <T> T[] shuffle(final T[] values, final boolean clone) {

		// Create a new random number generator.
		final Random random = new Random();

		// Setup target array to return.
		final T[] output = clone ? values.clone() : values;

		// Iterate each array value.
		for (int i = output.length - 1; i > 0; i--) {

			// Create random index.
			final int index = random.nextInt(i + 1);

			// Get the value.
			final T value = output[index];

			// Simple swap of values.
			output[index] = output[i];
			output[i] = value;

		}

		// Return sorted array.
		return output;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Copies the objects from a specific page of a collection into a target
	 * collection.
	 * 
	 * @param source   Source collection where to retrieve the beans to sort.<br>
	 *                 <br>
	 * @param page     Page to get from source collection. When you specify the
	 *                 number of rows (check out <code>pageRows</code> argument of
	 *                 this method) that you want in the target collection, Warework
	 *                 automatically calculates the number of pages that hold this
	 *                 number of rows. You have to pass an integer value greater
	 *                 than zero to retrieve a specific page from the source
	 *                 collection. This value must be greater than 0.<br>
	 *                 <br>
	 * @param pageRows Number of rows that you want in the target collection. Set
	 *                 this argument to <code>-1</code> to retrieve every row. This
	 *                 value must be greater than 0<br>
	 *                 <br>
	 * @param target   Target collection where to copy the specified page.<br>
	 *                 <br>
	 */
	public static <T> void page(final List<T> source, final int page, final int pageRows, final List<T> target) {

		// Start position in the list.
		final int start = (page - 1) * pageRows;

		// End position in the list.
		int end = start + pageRows - 1;
		if (end > (source.size() - 1)) {
			end = source.size() - 1;
		}

		// Extract the page.
		for (int index = start; index <= end; index++) {
			target.add(source.get(index));
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Copies the values from one map to another one.
	 * 
	 * @param source Source map where to retrieve the values.<br>
	 *               <br>
	 * @param target Target map where to copy the values of the source map.<br>
	 *               <br>
	 * @param <K>    The key type of the map.<br>
	 *               <br>
	 * @param <V>    The value type of the map.<br>
	 *               <br>
	 * @return Map with the values from the source map.<br>
	 *         <br>
	 */
	public static <K, V> Map<K, V> copyMap(final Map<K, V> source, final Map<K, V> target) {

		// Get the keys from the source map.
		final Set<K> sourceKeys = source.keySet();

		// Copy the values from the source map to the target map.
		for (Iterator<K> iter = sourceKeys.iterator(); iter.hasNext();) {

			// Get a key from the source map.
			final K key = iter.next();

			// Copy the value from the source map to the target map.
			target.put(key, source.get(key));

		}

		// Return the target map.
		return target;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes duplicated objects in a collection.
	 * 
	 * @param <T>        The type of the collection.<br>
	 *                   <br>
	 * @param collection Collection where to remove dupplicated objects.<br>
	 *                   <br>
	 * @return Collections without dupplicated objects.<br>
	 *         <br>
	 */
	public static <T> Collection<T> removeDuplicated(final Collection<T> collection) {

		// Collection for objects found.
		final Collection<T> found = new HashSet<T>();

		// Collection for objects to delete.
		final Collection<T> toDelete = new HashSet<T>();

		// Search objects to delete.
		for (final Iterator<T> iterator = collection.iterator(); iterator.hasNext();) {

			// Get an object.
			final T object = iterator.next();

			// Mark as 'to delete' if object was previously found and does not
			// exists at 'toDelete' set.
			if (found.contains(object)) {
				if (!toDelete.contains(object)) {
					toDelete.add(object);
				}
			} else {
				found.add(object);
			}

		}

		// Remove duplicated objects.
		for (final Iterator<T> iterator = toDelete.iterator(); iterator.hasNext();) {

			// Get an object to remove.
			final T object = iterator.next();

			// Remove the object in the collection.
			collection.remove(object);

		}

		// Return the collections without duplicated objects.
		return collection;

	}

	/**
	 * Removes the values indicated in an array in another array.
	 * 
	 * @param sources Array where to remove the values.<br>
	 *                <br>
	 * @param values  Array with the values to remove.<br>
	 *                <br>
	 * @return A new array with the values of the sources argument array that do no
	 *         exist in the values argument array.<br>
	 *         <br>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object removeValues(final Object sources, final Object values) {

		// Collection where to store the final values.
		final List result = new ArrayList();

		// Do I have to add the value in the result collection?
		boolean add = true;

		// Size of the sources array.
		final int maxI = Array.getLength(sources);

		// Size of the values array.
		final int maxJ = Array.getLength(values);

		// Copy the values.
		for (int i = 0; i < maxI; i++) {

			// Get one value from the sources array.
			final Object source = Array.get(sources, i);

			// Search if this value exists in the values array.
			for (int j = 0; j < maxJ; j++) {
				if (source.equals(Array.get(values, j))) {
					add = false;
					break;
				}
			}

			// Add the value if required.
			if (add) {
				result.add(source);
			} else {
				add = true;
			}

		}

		// Return a new array.
		return toArray(result, true);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Counts the amount of different values in an array.
	 * 
	 * @param array Array of values.<br>
	 *              <br>
	 * @return Number of different values.<br>
	 *         <br>
	 */
	public static int countDifferent(final Object array) {

		// Storage for each different number.
		final Set<Object> valuesFound = new HashSet<Object>();

		// Get the size of the array.
		final int arrayLength = Array.getLength(array);

		// Search for the numbers.
		for (int index = 0; index < arrayLength; index++) {

			// Get a number.
			final Object number = Array.get(array, index);

			// Validate if number exists
			if (!valuesFound.contains(number)) {
				valuesFound.add(number);
			}

		}

		// Return the amount of different values.
		return valuesFound.size();

	}

	/**
	 * Counts the amount of different values in a collection.
	 * 
	 * @param <T>        The type of the collection.<br>
	 *                   <br>
	 * @param collection Collection of values.<br>
	 *                   <br>
	 * @return Number of different values.<br>
	 *         <br>
	 */
	public static <T> int countDifferent(final Collection<T> collection) {

		// Storage for each different number.
		final Set<T> valuesFound = new HashSet<T>();

		// Search for the numbers.
		for (final Iterator<T> iterator = collection.iterator(); iterator.hasNext();) {

			// Get a number.
			final T number = iterator.next();

			// Validate if number exists
			if (!valuesFound.contains(number)) {
				valuesFound.add(number);
			}

		}

		// Return the amount of different values.
		return valuesFound.size();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates an array and initializes each position with a given value.
	 * 
	 * @param type   Type for the array to create.<br>
	 *               <br>
	 * @param length Size of the array.<br>
	 *               <br>
	 * @param value  Value for each position of the array.<br>
	 *               <br>
	 * @return A new array.<br>
	 *         <br>
	 */
	public static Object toArray(final Class<?> type, final int length, final Object value) {

		// Create a new array.
		final Object result = Array.newInstance(type, length);

		// Add the value.
		if (value != null) {
			for (int index = 0; index < length; index++) {
				Array.set(result, index, value);
			}
		}

		// Return the array.
		return result;

	}

	/**
	 * Copies a <code>java.util.Collection</code> data structure into a simple
	 * array.
	 * 
	 * @param collection      Source collection where to get the values. This
	 *                        collection must contain at least one object, if not
	 *                        this method will return <code>null</code>.<br>
	 *                        <br>
	 * @param toPrimitiveType If true and the first object found is a Byte, Short,
	 *                        Integer, Long, Float, Double, Boolean or Character
	 *                        data type, then the array that you'll get is a simple
	 *                        type array like int[], float[] and so on. If it's
	 *                        false then Integer, Short, Character, ... objects will
	 *                        appear in the result.<br>
	 *                        <br>
	 * @return Array with the values of the collection.<br>
	 *         <br>
	 */
	public static Object toArray(final Collection<?> collection, final boolean toPrimitiveType) {

		if (collection.size() > 0) {

			// Set up a counter for the result.
			int counter = 0;

			// Get the first element of the collection.
			Object firstObject = null;
			for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {

				// Get an object.
				final Object object = iterator.next();

				// Use it if it's not null.
				if (object != null) {

					// Use this object as the first object in the collection.
					firstObject = object;

					// Stop the loop.
					break;

				}

			}

			// Define the type of the array.
			Class<?> arrayType = null;
			if (toPrimitiveType) {
				if (firstObject instanceof Number) {

					// Get the class of the object (Integer.class, Float.class,
					// Short.class, ...).
					final Class<?> firstObjectClass = firstObject.getClass();

					// Try to get the primitive type of the number. Some
					// numbers, like BigDecimal, will throw an exception.
					try {

						// Get the static field with the primitive type class.
						final Field typeField = firstObjectClass.getDeclaredField("TYPE");

						// Get the primitive type class of the number
						// (int.class, float.class, short.class, ...).
						arrayType = (Class<?>) typeField.get(firstObject);

					} catch (Exception e) {
						arrayType = firstObject.getClass();
					}

				} else if (firstObject instanceof Boolean) {
					arrayType = boolean.class;
				} else if (firstObject instanceof Character) {
					arrayType = char.class;
				} else {
					arrayType = firstObject.getClass();
				}
			} else {
				arrayType = firstObject.getClass();
			}

			// Create the array.
			final Object result = Array.newInstance(arrayType, collection.size());

			// Copy each value from the collection to the result.
			for (final Iterator<?> iter = collection.iterator(); iter.hasNext();) {
				Array.set(result, counter++, iter.next());
			}

			// Return the array.
			return result;

		} else {
			return null;
		}

	}

	/**
	 * Copies a <code>java.util.Enumeration</code> into a simple array.
	 * 
	 * @param enumeration     Source enumeration where to get the values. This
	 *                        enumeration must contain at least one object, if not
	 *                        this method will return <code>null</code>.<br>
	 *                        <br>
	 * @param toPrimitiveType If true and the first object found is a Byte, Short,
	 *                        Integer, Long, Float, Double, Boolean or Character
	 *                        data type, then the array that you'll get is a simple
	 *                        type array like int[], float[] and so on. If it's
	 *                        false then Integer, Short, Character, ... objects will
	 *                        appear in the result.<br>
	 *                        <br>
	 * @param <T>             The type of the enumeration.<br>
	 *                        <br>
	 * @return Array with the values of the enumeration.<br>
	 *         <br>
	 */
	public static <T> Object toArray(final Enumeration<T> enumeration, final boolean toPrimitiveType) {

		// Create a collection where to copy the enumeration.
		final List<T> directories = new ArrayList<T>();

		// Copy the enumeration into the collection.
		while (enumeration.hasMoreElements()) {
			directories.add(enumeration.nextElement());
		}

		// Transform the collection into an array.
		return toArray(directories, toPrimitiveType);

	}

	/**
	 * Splits this string around matches of the given string delimiter.
	 * 
	 * @param values    String with a set of values. For example:
	 *                  "james,arnold,steve".<br>
	 *                  <br>
	 * @param delimiter String that separates the values. For example: ",".<br>
	 *                  <br>
	 * @return Array with the values of the string. For example: with a string like
	 *         "james,arnold,steve" and a delimiter like "," this method returns
	 *         {"james", "arnold", "steve"}.<br>
	 *         <br>
	 */
	public static String[] toArray(final String values, final String delimiter) {
		return values.split(delimiter);
	}

	/**
	 * Creates a collection and copies the values of an array into it.
	 * 
	 * @param type   Type of the collection to create.<br>
	 *               <br>
	 * @param values Array of values to copy into the collection.<br>
	 *               <br>
	 * @return A new collection.<br>
	 *         <br>
	 * @throws InstantiationException If there is an error when trying to create a
	 *                                new instance of the collection.<br>
	 *                                <br>
	 * @throws IllegalAccessException If there is an error when trying to create a
	 *                                new instance of the collection.<br>
	 *                                <br>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection toCollection(final Class<? extends Collection> type, final Object values)
			throws InstantiationException, IllegalAccessException {

		// Create the collection.
		final Collection result = type.newInstance();

		// Get the maximum length of the array.
		final int max = Array.getLength(values);

		// Copy each value into the collection.
		for (int index = 0; index < max; index++) {
			result.add(Array.get(values, index));
		}

		// Return the collection.
		return result;

	}

	/**
	 * Creates a <code>java.util.ArrayList</code> from an array of objects.
	 * 
	 * @param objects Array of objects to copy into a new
	 *                <code>java.util.ArrayList</code>.<br>
	 *                <br>
	 * @param <T>     The type of the source array and target list.<br>
	 *                <br>
	 * @return A new <code>java.util.ArrayList</code> with the values of the
	 *         array.<br>
	 *         <br>
	 */
	public static <T> ArrayList<T> toArrayList(final T[] objects) {

		// Set up the result.
		final ArrayList<T> result = new ArrayList<T>();

		// Copy the values from the array to the list.
		for (int i = 0; i < objects.length; i++) {
			result.add(objects[i]);
		}

		// Return the list.
		return result;

	}

	/**
	 * Creates a <code>java.util.ArrayList</code> from a
	 * <code>java.util.Collection</code>.
	 * 
	 * @param objects Collection of objects to copy into a new
	 *                <code>java.util.ArrayList</code>.<br>
	 *                <br>
	 * @param <T>     The type of the collections.<br>
	 *                <br>
	 * @return A new <code>java.util.ArrayList</code> with the values of the
	 *         collection.<br>
	 *         <br>
	 */
	public static <T> ArrayList<T> toArrayList(final Collection<T> objects) {
		return new ArrayList<T>(objects);
	}

	/**
	 * Creates a <code>java.util.ArrayList</code> from a
	 * <code>java.util.Enumeration</code>.
	 * 
	 * @param objects Source enumeration where to get the values.<br>
	 *                <br>
	 * @param <T>     The type of the source enumeration and target collection.<br>
	 *                <br>
	 * @return A new <code>java.util.ArrayList</code> with the values of the
	 *         enumeration.<br>
	 *         <br>
	 */
	public static <T> ArrayList<T> toArrayList(final Enumeration<T> objects) {
		return Collections.list(objects);
	}

	/**
	 * Creates a <code>java.util.Vector</code> from a
	 * <code>java.util.Collection</code>.
	 * 
	 * @param objects Collection of objects to copy into a new
	 *                <code>java.util.Vector</code>.<br>
	 *                <br>
	 * @param <T>     The type of the collections.<br>
	 *                <br>
	 * @return A new <code>java.util.Vector</code> with the values of the
	 *         collection.<br>
	 *         <br>
	 */
	public static <T> Vector<T> toVector(final Collection<T> objects) {
		return new Vector<T>(objects);
	}

	/**
	 * Creates a <code>java.util.LinkedList</code> from an array of objects.
	 * 
	 * @param objects Array of objects to copy into a new
	 *                <code>java.util.LinkedList</code>.<br>
	 *                <br>
	 * @param <T>     The type of the source array and target collection.<br>
	 *                <br>
	 * @return A new <code>java.util.LinkedList</code> with the values of the
	 *         array.<br>
	 *         <br>
	 */
	public static <T> LinkedList<T> toLinkedList(final T[] objects) {

		// Set up the result.
		final LinkedList<T> result = new LinkedList<T>();

		// Copy the values from the array to the list.
		for (int i = 0; i < objects.length; i++) {
			result.add(objects[i]);
		}

		// Return the list.
		return result;

	}

	/**
	 * Creates a <code>java.util.LinkedList</code> from a
	 * <code>java.util.Collection</code>.
	 * 
	 * @param objects Collection of objects to copy into a new
	 *                <code>java.util.LinkedList</code>.<br>
	 *                <br>
	 * @param <T>     The type of the collections.<br>
	 *                <br>
	 * @return A new <code>java.util.LinkedList</code> with the values of the
	 *         collection.<br>
	 *         <br>
	 */
	public static <T> LinkedList<T> toLinkedList(final Collection<T> objects) {
		return new LinkedList<T>(objects);
	}

	/**
	 * Creates a <code>java.util.LinkedList</code> from a
	 * <code>java.util.Enumeration</code>.
	 * 
	 * @param objects Source enumeration where to get the values.<br>
	 *                <br>
	 * @param <T>     The type of the source enumeration and target collection.<br>
	 *                <br>
	 * @return A new <code>java.util.LinkedList</code> with the values of the
	 *         enumeration.<br>
	 *         <br>
	 */
	public static <T> LinkedList<T> toLinkedList(final Enumeration<T> objects) {
		return new LinkedList<T>(toArrayList(objects));
	}

	/**
	 * Creates a <code>java.util.HashMap</code> from a <code>java.util.Map</code>.
	 * 
	 * @param map Source map where to get the values.<br>
	 *            <br>
	 * @param <K> The key type of the map.<br>
	 *            <br>
	 * @param <V> The value type of the map.<br>
	 *            <br>
	 * @return A new <code>java.util.HashMap</code>.<br>
	 *         <br>
	 */
	public static <K, V> HashMap<K, V> toHashMap(final Map<K, V> map) {
		return (HashMap<K, V>) copyMap(map, new HashMap<K, V>());
	}

	/**
	 * Creates a <code>java.util.HashMap</code> from a
	 * <code>java.util.Properties</code>.
	 * 
	 * @param properties Source properties where to get the values.<br>
	 *                   <br>
	 * @return A new <code>java.util.HashMap</code>.<br>
	 *         <br>
	 */
	public static Map<String, String> toHashMap(final Properties properties) {

		// Create a new instance of the collection.
		final Map<String, String> map = new HashMap<String, String>();

		// Get the keys of the properties file.
		final Collection<Object> keys = properties.keySet();

		// Copy each key/value pair in the HashMap.
		for (final Iterator<Object> iterator = keys.iterator(); iterator.hasNext();) {

			// Get the key of the properties file.
			final String key = (String) iterator.next();

			// Copy the key and the value into the HashMap.
			map.put(key, properties.getProperty(key));

		}

		// Return the map.
		return map;

	}

	/**
	 * Creates a <code>java.util.Hashtable</code> from a <code>java.util.Map</code>.
	 * 
	 * @param map Source map where to get the values.<br>
	 *            <br>
	 * @param <K> The key type of the map.<br>
	 *            <br>
	 * @param <V> The value type of the map.<br>
	 *            <br>
	 * @return A new <code>java.util.Hashtable</code>.<br>
	 *         <br>
	 */
	public static <K, V> Hashtable<K, V> toHashtable(final Map<K, V> map) {
		return (Hashtable<K, V>) copyMap(map, new Hashtable<K, V>());
	}

	/**
	 * Creates a <code>java.util.concurrent.ConcurrentHashMap</code> from a
	 * <code>java.util.Map</code>.
	 * 
	 * @param map Source map where to get the values.<br>
	 *            <br>
	 * @param <K> The key type of the map.<br>
	 *            <br>
	 * @param <V> The value type of the map.<br>
	 *            <br>
	 * @return A new <code>java.util.concurrent.ConcurrentHashMap</code>.<br>
	 *         <br>
	 */
	public static <K, V> ConcurrentHashMap<K, V> toConcurrentHashMap(final Map<K, V> map) {
		return (ConcurrentHashMap<K, V>) copyMap(map, new ConcurrentHashMap<K, V>());
	}

	/**
	 * Creates a <code>java.util.LinkedHashMap</code> from a
	 * <code>java.util.Map</code>.
	 * 
	 * @param map Source map where to get the values.<br>
	 *            <br>
	 * @param <K> The key type of the map.<br>
	 *            <br>
	 * @param <V> The value type of the map.<br>
	 *            <br>
	 * @return A new <code>java.util.LinkedHashMap</code>.<br>
	 *         <br>
	 */
	public static <K, V> LinkedHashMap<K, V> toLinkedHashMap(final Map<K, V> map) {
		return (LinkedHashMap<K, V>) copyMap(map, new LinkedHashMap<K, V>());
	}

	/**
	 * Creates a <code>java.util.Properties</code> from a
	 * <code>java.util.Map</code>.
	 * 
	 * @param map Source map where to get the values.<br>
	 *            <br>
	 * @return A new <code>java.util.Properties</code>.<br>
	 *         <br>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Properties toProperties(final Map map) {
		return (Properties) copyMap(map, new Properties());
	}

	/**
	 * Creates a <code>java.util.TreeMap</code> from a <code>java.util.Map</code>.
	 * 
	 * @param map Source map where to get the values.<br>
	 *            <br>
	 * @param <K> The key type of the map.<br>
	 *            <br>
	 * @param <V> The value type of the map.<br>
	 *            <br>
	 * @return A new <code>java.util.TreeMap</code>.<br>
	 *         <br>
	 */
	public static <K, V> TreeMap<K, V> toTreeMap(final Map<K, V> map) {
		return (TreeMap<K, V>) copyMap(map, new TreeMap<K, V>());
	}

	/**
	 * Creates a <code>java.util.WeakHashMap</code> from a
	 * <code>java.util.Map</code>.
	 * 
	 * @param map Source map where to get the values.<br>
	 *            <br>
	 * @param <K> The key type of the map.<br>
	 *            <br>
	 * @param <V> The value type of the map.<br>
	 *            <br>
	 * @return A new <code>java.util.WeakHashMap</code>.<br>
	 *         <br>
	 */
	public static <K, V> WeakHashMap<K, V> toWeakHashMap(final Map<K, V> map) {
		return (WeakHashMap<K, V>) copyMap(map, new WeakHashMap<K, V>());
	}

	/**
	 * Creates a <code>java.util.HashSet</code> from an
	 * <code>java.util.Collection</code>.
	 * 
	 * @param collection Collection of objects to copy into a new
	 *                   <code>java.util.HashSet</code>.<br>
	 *                   <br>
	 * @param <T>        The type of the collections.<br>
	 *                   <br>
	 * @return A new <code>java.util.HashSet</code><br>
	 *         <br>
	 */
	public static <T> HashSet<T> toHashSet(final Collection<T> collection) {
		return new HashSet<T>(collection);
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sorts a collection of beans and copies the result into a given list.
	 * 
	 * @param comparableBeans Collection of wrapped beans ready to be sorted.<br>
	 *                        <br>
	 * @param target          Target collection where to copy the sorted beans.<br>
	 *                        <br>
	 */
	private static <T> void sort(final List<ComparableBean<T>> comparableBeans, final List<T> target) {

		// Sort the collection.
		Collections.sort(comparableBeans);

		// Copy beans from source collection into target collection.
		for (final Iterator<ComparableBean<T>> iterator = comparableBeans.iterator(); iterator.hasNext();) {

			// Get a wrapped bean.
			final ComparableBean<T> comparableBean = iterator.next();

			// Store the bean in target collection.
			target.add(comparableBean.getBean());

		}

	}

}