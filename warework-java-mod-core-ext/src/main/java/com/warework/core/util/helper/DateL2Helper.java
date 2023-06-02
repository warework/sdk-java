package com.warework.core.util.helper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Performs common dates operations. <br>
 * <br>
 * Remember that:<br>
 * <ul>
 * <li>Dates do not store the TimeZone: Date is always UTC-based, there's no
 * notion of a "local instance of Date."</li>
 * <li>If you are going to create a Date, it is a good idea to always indicate
 * the TimeZone for that date.</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class DateL2Helper {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// CALENDAR VALUES

	/**
	 * Constant that defines the number of milliseconds in a second starting
	 * from 0.
	 */
	protected static final int MILLISECOND_OF_SECOND = 999;

	/**
	 * Constant that defines the number of seconds in a minute starting from 0.
	 */
	protected static final int SECONDS_OF_MINUTE = 59;

	/**
	 * Constant that defines the number of minutes in a hour starting from 0.
	 */
	protected static final int MINUTES_OF_HOUR = SECONDS_OF_MINUTE;

	/**
	 * Constant that defines the number of hours in a day starting from 0.
	 */
	protected static final int HOURS_OF_DAY = 23;

	/**
	 * Constant that defines the number of days in a month.
	 */
	protected static final int DAYS_OF_MONTH = 31;

	// TIME IN MILLISECONDS

	/**
	 * Constant that defines the number of milliseconds in a second.
	 */
	public static final long SECOND_MILLIS = MILLISECOND_OF_SECOND + 1;

	/**
	 * Constant that defines the number of seconds in one minute.
	 */
	public static final long MINUTE_MILLIS = SECOND_MILLIS
			* (SECONDS_OF_MINUTE + 1);

	/**
	 * Constant that defines the number of minutes in one hour.
	 */
	public static final long HOUR_MILLIS = MINUTE_MILLIS
			* (MINUTES_OF_HOUR + 1);

	/**
	 * Constant that defines the number of hours in a day.
	 */
	public static final long DAY_MILLIS = HOUR_MILLIS * (HOURS_OF_DAY + 1);

	/**
	 * Constant that defines the number of days in a year.
	 */
	public static final long YEAR_MILLIS = DAY_MILLIS * 365;

	// DATE FORMAT PATTERNS

	/**
	 * Constants that defines a date pattern for days.
	 */
	public static final String DATE_PATTERN_DAY = "dd";

	/**
	 * Constants that defines a date pattern for months.
	 */
	public static final String DATE_PATTERN_MONTH = "MM";

	/**
	 * Constants that defines a date pattern for years (four digit).
	 */
	public static final String DATE_PATTERN_YEAR = "yyyy";

	/**
	 * Constants that defines a date pattern for hours.
	 */
	public static final String DATE_PATTERN_HOUR = "HH";

	/**
	 * Constants that defines a date pattern for minutes.
	 */
	public static final String DATE_PATTERN_MINUTES = "mm";

	/**
	 * Constants that defines a date pattern for seconds.
	 */
	public static final String DATE_PATTERN_SECONDS = "ss";

	/**
	 * Constants that defines a date pattern for milliseconds.
	 */
	public static final String DATE_PATTERN_MILLISECONDS = "SSS";

	/**
	 * Constants that defines the character that separates days, months and
	 * years.
	 */
	public static final String DATE_CHARACTER_SEPARATOR = StringL2Helper.CHARACTER_FORWARD_SLASH;

	/**
	 * Constants that defines the character that separates hours, minutes,
	 * seconds and milliseconds.
	 */
	public static final String HOUR_CHARACTER_SEPARATOR = StringL2Helper.CHARACTER_COLON;

	/**
	 * Constants that defines the character that separates dates and hours.
	 */
	public static final String HOUR_DATE_CHARACTER_SEPARATOR = StringL2Helper.CHARACTER_SPACE;

	/**
	 * Constants that defines a date pattern in the form of 'yyyy/MM/dd'.
	 */
	public static final String DATE_PATTERN_YYYYMMDD = DATE_PATTERN_YEAR
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_DAY;

	/**
	 * Constants that defines a date pattern in the form of 'yyyy/MM/dd HH'.
	 */
	public static final String DATE_PATTERN_YYYYMMDD_HH = DATE_PATTERN_YEAR
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_DAY
			+ HOUR_DATE_CHARACTER_SEPARATOR + DATE_PATTERN_HOUR;

	/**
	 * Constants that defines a date pattern in the form of 'yyyy/MM/dd HH:mm'.
	 */
	public static final String DATE_PATTERN_YYYYMMDD_HHMM = DATE_PATTERN_YEAR
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_DAY
			+ HOUR_DATE_CHARACTER_SEPARATOR + DATE_PATTERN_HOUR
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_MINUTES;

	/**
	 * Constants that defines a date pattern in the form of 'yyyy/MM/dd
	 * HH:mm:ss'.
	 */
	public static final String DATE_PATTERN_YYYYMMDD_HHMMSS = DATE_PATTERN_YEAR
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_DAY
			+ HOUR_DATE_CHARACTER_SEPARATOR + DATE_PATTERN_HOUR
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_MINUTES
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_SECONDS;

	/**
	 * Constants that defines a date pattern in the form of 'yyyy/MM/dd
	 * HH:mm:ss:SSS'.
	 */
	public static final String DATE_PATTERN_YYYYMMDD_HHMMSSSSS = DATE_PATTERN_YEAR
			+ DATE_CHARACTER_SEPARATOR
			+ DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR
			+ DATE_PATTERN_DAY
			+ HOUR_DATE_CHARACTER_SEPARATOR
			+ DATE_PATTERN_HOUR
			+ HOUR_CHARACTER_SEPARATOR
			+ DATE_PATTERN_MINUTES
			+ HOUR_CHARACTER_SEPARATOR
			+ DATE_PATTERN_SECONDS
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_MILLISECONDS;

	/**
	 * Constants that defines a date pattern in the form of 'dd/MM/yyyy'.
	 */
	public static final String DATE_PATTERN_DDMMYYYY = DATE_PATTERN_DAY
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_YEAR;

	/**
	 * Constants that defines a date pattern in the form of 'dd/MM/yyyy HH'.
	 */
	public static final String DATE_PATTERN_DDMMYYYY_HH = DATE_PATTERN_DAY
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_YEAR
			+ HOUR_DATE_CHARACTER_SEPARATOR + DATE_PATTERN_HOUR;

	/**
	 * Constants that defines a date pattern in the form of 'dd/MM/yyyy HH:mm'.
	 */
	public static final String DATE_PATTERN_DDMMYYYY_HHMM = DATE_PATTERN_DAY
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_YEAR
			+ HOUR_DATE_CHARACTER_SEPARATOR + DATE_PATTERN_HOUR
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_MINUTES;

	/**
	 * Constants that defines a date pattern in the form of 'dd/MM/yyyy
	 * HH:mm:ss'.
	 */
	public static final String DATE_PATTERN_DDMMYYYY_HHMMSS = DATE_PATTERN_DAY
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR + DATE_PATTERN_YEAR
			+ HOUR_DATE_CHARACTER_SEPARATOR + DATE_PATTERN_HOUR
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_MINUTES
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_SECONDS;

	/**
	 * Constants that defines a date pattern in the form of 'dd/MM/yyyy
	 * HH:mm:ss:SSS'.
	 */
	public static final String DATE_PATTERN_DDMMYYYY_HHMMSSSSS = DATE_PATTERN_DAY
			+ DATE_CHARACTER_SEPARATOR
			+ DATE_PATTERN_MONTH
			+ DATE_CHARACTER_SEPARATOR
			+ DATE_PATTERN_YEAR
			+ HOUR_DATE_CHARACTER_SEPARATOR
			+ DATE_PATTERN_HOUR
			+ HOUR_CHARACTER_SEPARATOR
			+ DATE_PATTERN_MINUTES
			+ HOUR_CHARACTER_SEPARATOR
			+ DATE_PATTERN_SECONDS
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_MILLISECONDS;

	/**
	 * Constants that defines a date pattern in the form of 'HH:mm'.
	 */
	public static final String DATE_PATTERN_HHMM = DATE_PATTERN_HOUR
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_MINUTES;

	/**
	 * Constants that defines a date pattern in the form of 'HH:mm:ss'.
	 */
	public static final String DATE_PATTERN_HHMMSS = DATE_PATTERN_HOUR
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_MINUTES
			+ HOUR_CHARACTER_SEPARATOR + DATE_PATTERN_SECONDS;

	// DEFAULT DATE VALUES

	/**
	 * Constants that defines a representative value for an unknown date.
	 */
	public static final Date DATE_UNKNOWN = createUnknownDate();

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	protected DateL2Helper() {
	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a <code>java.text.SimpleDateFormat</code>.
	 * 
	 * @param timeZone
	 *            TimeZone for the format patern. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @param format
	 *            Format pattern to apply. Check out the
	 *            <code>java.text.SimpleDateFormat</code> API to review the
	 *            format patterns.<br>
	 * <br>
	 * @return A <code>java.text.SimpleDateFormat</code>.<br>
	 * <br>
	 */
	public static SimpleDateFormat toSimpleDateFormat(TimeZone timeZone,
			String format) {

		// Create the date format.
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		// Add the time zone.
		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		// Return the date formar.
		return dateFormat;

	}

	/**
	 * Creates a <code>java.util.Calendar</code>.
	 * 
	 * @param date
	 *            Date to set in the calendar. If <code>null</code> then current
	 *            date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the calendar. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param locale
	 *            Locale for the calendar. If <code>null</code> then default
	 *            locale is used.<br>
	 * <br>
	 * @return A new <code>java.util.Calendar</code>.<br>
	 * <br>
	 */
	public static Calendar toCalendar(Date date, TimeZone timeZone,
			Locale locale) {

		// Get an instance of a calendar.
		Calendar calendar = null;
		if ((timeZone != null) && (locale != null)) {
			calendar = Calendar.getInstance(timeZone, locale);
		} else if (timeZone != null) {
			calendar = Calendar.getInstance(timeZone);
		} else if (locale != null) {
			calendar = Calendar.getInstance(locale);
		} else {
			calendar = Calendar.getInstance();
		}

		// Set the date in the calendar.
		if (date != null) {
			calendar.setTime(date);
		}

		// Return the calendar.
		return calendar;

	}

	/**
	 * Creates a <code>java.util.Calendar</code>.
	 * 
	 * @param year
	 *            Year to set in the calendar. If -1 or less then current year
	 *            is used.<br>
	 * <br>
	 * @param month
	 *            Month to set in the calendar. If -1 or less then current month
	 *            is used.<br>
	 * <br>
	 * @param dayOfMonth
	 *            Day of the month to set in the calendar. If -1 or less then
	 *            current day is used.<br>
	 * <br>
	 * @param hourOfDay
	 *            Hour of the day to set in the calendar. If -1 or less then
	 *            current hour is used.<br>
	 * <br>
	 * @param minute
	 *            Minute to set in the calendar. If -1 or less then current
	 *            minute is used.<br>
	 * <br>
	 * @param second
	 *            Second to set in the calendar. If -1 or less then current
	 *            second is used.<br>
	 * <br>
	 * @param millisecond
	 *            Millisecond to set in the calendar. If -1 or less then current
	 *            millisecond is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the calendar. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param locale
	 *            Locale for the calendar. If <code>null</code> then default
	 *            locale is used.<br>
	 * <br>
	 * @return A new <code>java.util.Calendar</code>.<br>
	 * <br>
	 */
	public static Calendar toCalendar(int year, int month, int dayOfMonth,
			int hourOfDay, int minute, int second, int millisecond,
			TimeZone timeZone, Locale locale) {

		// Create a calendar.
		Calendar calendar = toCalendar(null, timeZone, locale);

		// Set the date and time.
		if (year >= 0) {
			calendar.set(Calendar.YEAR, year);
		}
		if (month >= 0) {
			calendar.set(Calendar.MONTH, month);
		}
		if (dayOfMonth >= 0) {
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		}
		if (hourOfDay >= 0) {
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		}
		if (minute >= 0) {
			calendar.set(Calendar.MINUTE, minute);
		}
		if (second >= 0) {
			calendar.set(Calendar.SECOND, second);
		}
		if (millisecond >= 0) {
			calendar.set(Calendar.MILLISECOND, millisecond);
		}

		// Return the calendar.
		return calendar;

	}

	/**
	 * Creates a <code>java.util.Date</code>.
	 * 
	 * @param year
	 *            Year to set in the date. If -1 or less then current year is
	 *            used.<br>
	 * <br>
	 * @param month
	 *            Month to set in the date. If -1 or less then current month is
	 *            used.<br>
	 * <br>
	 * @param dayOfMonth
	 *            Day of the month to set in the date. If -1 or less then
	 *            current day is used.<br>
	 * <br>
	 * @param hourOfDay
	 *            Hour of the day to set in the date. If -1 or less then current
	 *            hour is used.<br>
	 * <br>
	 * @param minute
	 *            Minute to set in the date. If -1 or less then current minute
	 *            is used.<br>
	 * <br>
	 * @param second
	 *            Second to set in the date. If -1 or less then current second
	 *            is used.<br>
	 * <br>
	 * @param millisecond
	 *            Millisecond to set in the date. If -1 then current millisecond
	 *            is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 */
	public static Date toDate(int year, int month, int dayOfMonth,
			int hourOfDay, int minute, int second, int millisecond,
			TimeZone timeZone) {
		return toCalendar(year, month, dayOfMonth, hourOfDay, minute, second,
				millisecond, timeZone, null).getTime();
	}

	/**
	 * Creates a date from a string.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param format
	 *            Format pattern to apply. Check out the
	 *            <code>java.text.SimpleDateFormat</code> API to review the
	 *            format patterns.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDate(String date, TimeZone timeZone, String format)
			throws ParseException {

		// Create a date format.
		DateFormat dateFormat = toSimpleDateFormat(timeZone, format);

		// Parse the date.
		return dateFormat.parse(date);

	}

	/**
	 * Creates a date from a string with the format 'yyyy/MM/dd'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateYYYYMMDD(String date, TimeZone timeZone)
			throws ParseException {

		// Create the date.
		Date result = toDate(date, timeZone, DATE_PATTERN_YYYYMMDD);

		// Set hours, minutes and seconds to zero.
		return zeroOutHHMMSS(result, timeZone);

	}

	/**
	 * Creates a date from a string with the format 'yyyy/MM/dd HH'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateYYYYMMDDHH(String date, TimeZone timeZone)
			throws ParseException {

		// Create the date.
		Date result = toDate(date, timeZone, DATE_PATTERN_YYYYMMDD_HH);

		// Set minutes and seconds to zero.
		return zeroOutMMSS(result, timeZone);

	}

	/**
	 * Creates a date from a string with the format 'yyyy/MM/dd HH:mm'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateYYYYMMDDHHMM(String date, TimeZone timeZone)
			throws ParseException {

		// Create the date.
		Date result = toDate(date, timeZone, DATE_PATTERN_YYYYMMDD_HHMM);

		// Set seconds to zero.
		return zeroOutSecond(result, timeZone);

	}

	/**
	 * Creates a date from a string with the format 'yyyy/MM/dd HH:mm:ss'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateYYYYMMDDHHMMSS(String date, TimeZone timeZone)
			throws ParseException {

		// Create the date.
		Date result = toDate(date, timeZone, DATE_PATTERN_YYYYMMDD_HHMMSS);

		// Set milliseconds to zero.
		return zeroOutMillisecond(result, timeZone);

	}

	/**
	 * Creates a date from a string with the format 'yyyy/MM/dd HH:mm:ss:SSS'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateYYYYMMDDHHMMSSSSS(String date, TimeZone timeZone)
			throws ParseException {
		return toDate(date, timeZone, DATE_PATTERN_YYYYMMDD_HHMMSSSSS);
	}

	/**
	 * Creates a date from a string with the format 'dd/MM/yyyy'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateDDMMYYYY(String date, TimeZone timeZone)
			throws ParseException {

		// Create the date.
		Date result = toDate(date, timeZone, DATE_PATTERN_DDMMYYYY);

		// Set hours, minutes and seconds to zero.
		return zeroOutHHMMSS(result, timeZone);

	}

	/**
	 * Creates a date from a string with the format 'dd/MM/yyyy HH'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateDDMMYYYYHH(String date, TimeZone timeZone)
			throws ParseException {

		// Create the date.
		Date result = toDate(date, timeZone, DATE_PATTERN_DDMMYYYY_HH);

		// Set minutes and seconds to zero.
		return zeroOutMMSS(result, timeZone);

	}

	/**
	 * Creates a date from a string with the format 'dd/MM/yyyy HH:mm'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateDDMMYYYYHHMM(String date, TimeZone timeZone)
			throws ParseException {

		// Create the date.
		Date result = toDate(date, timeZone, DATE_PATTERN_DDMMYYYY_HHMM);

		// Set seconds to zero.
		return zeroOutSecond(result, timeZone);

	}

	/**
	 * Creates a date from a string with the format 'dd/MM/yyyy HH:mm:ss'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateDDMMYYYYHHMMSS(String date, TimeZone timeZone)
			throws ParseException {

		// Create the date.
		Date result = toDate(date, timeZone, DATE_PATTERN_DDMMYYYY_HHMMSS);

		// Set milliseconds to zero.
		return zeroOutMillisecond(result, timeZone);

	}

	/**
	 * Creates a date from a string with the format 'dd/MM/yyyy HH:mm:ss:SSS'.
	 * 
	 * @param date
	 *            String with the date.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A new <code>java.util.Date</code>.<br>
	 * <br>
	 * @throws ParseException
	 *             If there is an error when trying to parse the date.<br>
	 * <br>
	 */
	public static Date toDateDDMMYYYYHHMMSSSSS(String date, TimeZone timeZone)
			throws ParseException {
		return toDate(date, timeZone, DATE_PATTERN_DDMMYYYY_HHMMSSSSS);
	}

	/**
	 * Creates a <code>java.sql.Timestamp</code> from a date.
	 * 
	 * @param date
	 *            Date to set in the Timestamp.<br>
	 * <br>
	 * @return The <code>java.sql.Timestamp</code> object with the date.<br>
	 * <br>
	 */
	public static Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * Converts a date object into a string using a specified format pattern.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param locale
	 *            Locale for the date. If <code>null</code> then default loce is
	 *            used.<br>
	 * <br>
	 * @param pattern
	 *            Date pattern.<br>
	 * <br>
	 * @return A string with the date pattern form that represents a date.<br>
	 * <br>
	 */
	public static String toString(Date date, TimeZone timeZone, Locale locale,
			String pattern) {
		return StringL2Helper.toString(date, pattern, locale, timeZone);
	}

	/**
	 * Converts a date object into a string with the 'yyyy/MM/dd' form.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A string with the 'yyyy/MM/dd' form that represents a date.<br>
	 * <br>
	 */
	public static String toStringYYYYMMDD(Date date, TimeZone timeZone) {
		return StringL2Helper.toString(date, DATE_PATTERN_YYYYMMDD, null,
				timeZone);
	}

	/**
	 * Converts a date object into a string with the 'yyyy/MM/dd HH:mm' form.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A string with the 'yyyy/MM/dd HH:mm' form that represents a date.<br>
	 * <br>
	 */
	public static String toStringYYYYMMDDHHMM(Date date, TimeZone timeZone) {
		return StringL2Helper.toString(date, DATE_PATTERN_YYYYMMDD_HHMM, null,
				timeZone);
	}

	/**
	 * Converts a date object into a string with the 'yyyy/MM/dd HH:mm:ss' form.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A string with the 'yyyy/MM/dd HH:mm:ss' form that represents a
	 *         date.<br>
	 * <br>
	 */
	public static String toStringYYYYMMDDHHMMSS(Date date, TimeZone timeZone) {
		return StringL2Helper.toString(date, DATE_PATTERN_YYYYMMDD_HHMMSS,
				null, timeZone);
	}

	/**
	 * Converts a date object into a string with the 'dd/MM/yyyy' form.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A string with the 'dd/MM/yyyy' form that represents a date.<br>
	 * <br>
	 */
	public static String toStringDDMMYYYY(Date date, TimeZone timeZone) {
		return StringL2Helper.toString(date, DATE_PATTERN_DDMMYYYY, null,
				timeZone);
	}

	/**
	 * Converts a date object into a string with the 'dd/MM/yyyy HH:mm' form.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A string with the 'dd/MM/yyyy' form that represents a date.<br>
	 * <br>
	 */
	public static String toStringDDMMYYYYHHMM(Date date, TimeZone timeZone) {
		return StringL2Helper.toString(date, DATE_PATTERN_DDMMYYYY_HHMM, null,
				timeZone);
	}

	/**
	 * Converts a date object into a string with the 'dd/MM/yyyy HH:mm:ss' form.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A string with the 'dd/MM/yyyy HH:mm:ss' form that represents a
	 *         date.<br>
	 * <br>
	 */
	public static String toStringDDMMYYYYHHMMSS(Date date, TimeZone timeZone) {
		return StringL2Helper.toString(date, DATE_PATTERN_DDMMYYYY_HHMMSS,
				null, timeZone);
	}

	/**
	 * Converts a date object into a string with the 'HH:mm' form.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A string with the 'HH:mm' form that represents a date.<br>
	 * <br>
	 */
	public static String toStringHHMM(Date date, TimeZone timeZone) {
		return StringL2Helper.toString(date, DATE_PATTERN_HHMM, null, timeZone);
	}

	/**
	 * Converts a date object into a string with the 'HH:mm:ss' form.
	 * 
	 * @param date
	 *            Date to transform.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return A string with the 'HH:mm:ss' form that represents a date.<br>
	 * <br>
	 */
	public static String toStringHHMMSS(Date date, TimeZone timeZone) {
		return StringL2Helper.toString(date, DATE_PATTERN_HHMMSS, null,
				timeZone);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the current time in a specific time zone.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Current date.<br>
	 * <br>
	 */
	public static Date getCurrentDate(TimeZone timeZone) {

		// Create the calendar.
		Calendar calendar = toCalendar(null, timeZone, null);

		// Return the date.
		return calendar.getTime();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the year.
	 * 
	 * @param date
	 *            Date where to extract the year.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Year of the date.<br>
	 * <br>
	 */
	public static int getYear(Date date, TimeZone timeZone) {

		// Create a calendar where to perform date operations.
		Calendar calendario = toCalendar(date, timeZone, null);

		// Return the hour.
		return calendario.get(Calendar.YEAR);

	}

	/**
	 * Gets the month.
	 * 
	 * @param date
	 *            Date where to extract the month.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Month of the date.<br>
	 * <br>
	 */
	public static int getMonth(Date date, TimeZone timeZone) {

		// Create a calendar where to perform date operations.
		Calendar calendario = toCalendar(date, timeZone, null);

		// Return the hour.
		return calendario.get(Calendar.MONTH);

	}

	/**
	 * Gets the day of the month.
	 * 
	 * @param date
	 *            Date where to extract the day.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Day of the date.<br>
	 * <br>
	 */
	public static int getDayOfMonth(Date date, TimeZone timeZone) {

		// Create a calendar where to perform date operations.
		Calendar calendario = toCalendar(date, timeZone, null);

		// Return the hour.
		return calendario.get(Calendar.DAY_OF_MONTH);

	}

	/**
	 * Gets the day of the week.
	 * 
	 * @param date
	 *            Date where to extract the day of week.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param locale
	 *            Locale for the calendar. If <code>null</code> then default
	 *            locale is used.<br>
	 * <br>
	 * @return Day of the week of the given date.<br>
	 * <br>
	 */
	public static int getDayOfWeek(Date date, TimeZone timeZone, Locale locale) {

		// Create a calendar where to perform date operations.
		Calendar calendario = toCalendar(date, timeZone, locale);

		// Return the hour.
		return calendario.get(Calendar.DAY_OF_WEEK);

	}

	/**
	 * Gets the hour.
	 * 
	 * @param date
	 *            Date where to extract the hour.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Hour of the date (24-hour clock).<br>
	 * <br>
	 */
	public static int getHourOfDay(Date date, TimeZone timeZone) {

		// Create a calendar where to perform date operations.
		Calendar calendario = toCalendar(date, timeZone, null);

		// Return the hour.
		return calendario.get(Calendar.HOUR_OF_DAY);

	}

	/**
	 * Gets the minutes.
	 * 
	 * @param date
	 *            Date where to extract the minutes.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Minutes.<br>
	 * <br>
	 */
	public static int getMinute(Date date, TimeZone timeZone) {

		// Create a calendar where to perform date operations.
		Calendar calendario = toCalendar(date, timeZone, null);

		// Return the minutes.
		return calendario.get(Calendar.MINUTE);

	}

	/**
	 * Gets the seconds.
	 * 
	 * @param date
	 *            Date where to extract the seconds.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Seconds.<br>
	 * <br>
	 */
	public static int getSecond(Date date, TimeZone timeZone) {

		// Create a calendar where to perform date operations.
		Calendar calendario = toCalendar(date, timeZone, null);

		// Return the minutes.
		return calendario.get(Calendar.SECOND);

	}

	/**
	 * Gets the milliseconds.
	 * 
	 * @param date
	 *            Date where to extract the milliseconds.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Milliseconds.<br>
	 * <br>
	 */
	public static int getMillisecond(Date date, TimeZone timeZone) {

		// Create a calendar where to perform date operations.
		Calendar calendario = toCalendar(date, timeZone, null);

		// Return the minutes.
		return calendario.get(Calendar.MILLISECOND);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the current year.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Current year.<br>
	 * <br>
	 */
	public static int getCurrentYear(TimeZone timeZone) {
		return getYear(null, timeZone);
	}

	/**
	 * Gets the current month.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Current month.<br>
	 * <br>
	 */
	public static int getCurrentMonth(TimeZone timeZone) {
		return getMonth(null, timeZone);
	}

	/**
	 * Gets the current day of the month.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Current day.<br>
	 * <br>
	 */
	public static int getCurrentDayOfMonth(TimeZone timeZone) {
		return getDayOfMonth(null, timeZone);
	}

	/**
	 * Gets the current day of the week.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param locale
	 *            Locale for the calendar. If <code>null</code> then default
	 *            locale is used.<br>
	 * <br>
	 * @return Current day of the week.<br>
	 * <br>
	 */
	public static int getCurrentDayOfWeek(TimeZone timeZone, Locale locale) {
		return getDayOfWeek(null, timeZone, locale);
	}

	/**
	 * Gets the current hour.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Current hour (24-hour clock).<br>
	 * <br>
	 */
	public static int getCurrentHourOfDay(TimeZone timeZone) {
		return getHourOfDay(null, timeZone);
	}

	/**
	 * Gets the current minutes.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Current minutes.<br>
	 * <br>
	 */
	public static int getCurrentMinute(TimeZone timeZone) {
		return getMinute(null, timeZone);
	}

	/**
	 * Gets the current seconds.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Current seconds.<br>
	 * <br>
	 */
	public static int getCurrentSecond(TimeZone timeZone) {
		return getSecond(null, timeZone);
	}

	/**
	 * Gets the current milliseconds.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Current milliseconds.<br>
	 * <br>
	 */
	public static int getCurrentMillisecond(TimeZone timeZone) {
		return getMillisecond(null, timeZone);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Get the lastest day of the month for a given date.
	 * 
	 * @param date
	 *            Date where to extract the last the of the month.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Last day of the month.<br>
	 * <br>
	 */
	public static int getLastDayOfMonth(Date date, TimeZone timeZone) {

		// Create the calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Return the value.
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/**
	 * Get the lastest day of the current month.
	 * 
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Last day of the current month.<br>
	 * <br>
	 */
	public static int getLastDayOfCurrentMonth(TimeZone timeZone) {
		return getLastDayOfMonth(null, timeZone);
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Calculates the difference in milliseconds between two dates.
	 * 
	 * @param date1
	 *            First date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return A positive integer that represents the difference in
	 *         milliseconds.<br>
	 * <br>
	 */
	public static int getMillisecondsDifference(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Get the milliseconds of the first date.
		long millis1 = toCalendar(date1, timeZone1, null).getTimeInMillis();

		// Get the milliseconds of the second date.
		long millis2 = toCalendar(date2, timeZone2, null).getTimeInMillis();

		// Setup the difference.
		long difference = -1;

		// Get the difference.
		if ((millis2 - millis1) >= 0) {
			difference = millis2 - millis1;
		} else {
			difference = millis1 - millis2;
		}

		// Return the difference.
		return (int) difference;

	}

	/**
	 * Calculates the difference in seconds between two dates.
	 * 
	 * @param date1
	 *            First date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return A positive integer that represents the difference in seconds.<br>
	 * <br>
	 */
	public static int getSecondsDifference(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Get the milliseconds of the first date.
		long millis1 = toCalendar(date1, timeZone1, null).getTimeInMillis();

		// Get the milliseconds of the second date.
		long millis2 = toCalendar(date2, timeZone2, null).getTimeInMillis();

		// Setup the difference.
		long difference = -1;

		// Get the difference.
		if ((millis2 - millis1) >= 0) {
			difference = (millis2 / SECOND_MILLIS) - (millis1 / SECOND_MILLIS);
		} else {
			difference = (millis1 / SECOND_MILLIS) - (millis2 / SECOND_MILLIS);
		}

		// Return the difference.
		return (int) difference;

	}

	/**
	 * Calculates the difference in minutes between two dates.
	 * 
	 * @param date1
	 *            First date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return A positive integer that represents the difference in minutes.<br>
	 * <br>
	 */
	public static int getMinutesDifference(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Get the milliseconds of the first date.
		long millis1 = toCalendar(date1, timeZone1, null).getTimeInMillis();

		// Get the milliseconds of the second date.
		long millis2 = toCalendar(date2, timeZone2, null).getTimeInMillis();

		// Setup the difference.
		long difference = -1;

		// Get the difference.
		if ((millis2 - millis1) >= 0) {
			difference = (millis2 / MINUTE_MILLIS) - (millis1 / MINUTE_MILLIS);
		} else {
			difference = (millis1 / MINUTE_MILLIS) - (millis2 / MINUTE_MILLIS);
		}

		// Return the difference.
		return (int) difference;

	}

	/**
	 * Calculates the difference in hours between two dates.
	 * 
	 * @param date1
	 *            First date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return A positive integer that represents the difference in hours.<br>
	 * <br>
	 */
	public static int getHoursDifference(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Get the milliseconds of the first date.
		long millis1 = toCalendar(date1, timeZone1, null).getTimeInMillis();

		// Get the milliseconds of the second date.
		long millis2 = toCalendar(date2, timeZone2, null).getTimeInMillis();

		// Setup the difference.
		long difference = -1;

		// Get the difference.
		if ((millis2 - millis1) >= 0) {
			difference = (millis2 / HOUR_MILLIS) - (millis1 / HOUR_MILLIS);
		} else {
			difference = (millis1 / HOUR_MILLIS) - (millis2 / HOUR_MILLIS);
		}

		// Return the difference.
		return (int) difference;

	}

	/**
	 * Calculates the difference in days between two dates.
	 * 
	 * @param date1
	 *            First date. If null then current date is used.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If null then default time zone is
	 *            used.<br>
	 * <br>
	 * @param date2
	 *            Second date. If null then current date is used.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If null then default time zone
	 *            is used.<br>
	 * <br>
	 * @return A positive integer that represents the difference in days.<br>
	 * <br>
	 */
	public static int getDaysDifference(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Get the milliseconds of the first date.
		long millis1 = toCalendar(date1, timeZone1, null).getTimeInMillis();

		// Get the milliseconds of the second date.
		long millis2 = toCalendar(date2, timeZone2, null).getTimeInMillis();

		// Setup the difference.
		long difference = -1;

		// Get the difference.
		if ((millis2 - millis1) >= 0) {
			difference = (millis2 / DAY_MILLIS) - (millis1 / DAY_MILLIS);
		} else {
			difference = (millis1 / DAY_MILLIS) - (millis2 / DAY_MILLIS);
		}

		// Return the difference.
		return (int) difference;

	}

	/**
	 * Calculates the difference in years between two dates.
	 * 
	 * @param date1
	 *            First date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date. If <code>null</code> then current date is used.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return A positive integer that represents the difference in years.<br>
	 * <br>
	 */
	public static int getYearsDifference(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Get the milliseconds of the first date.
		long millis1 = toCalendar(date1, timeZone1, null).getTimeInMillis();

		// Get the milliseconds of the second date.
		long millis2 = toCalendar(date2, timeZone2, null).getTimeInMillis();

		// Setup the difference.
		long difference = -1;

		// Get the difference.
		if ((millis2 - millis1) >= 0) {
			difference = (millis2 / YEAR_MILLIS) - (millis1 / YEAR_MILLIS);
		} else {
			difference = (millis1 / YEAR_MILLIS) - (millis2 / YEAR_MILLIS);
		}

		// Return the difference.
		return (int) difference;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Rolls forward or backward a date with a given number of milliseconds.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param amount
	 *            Number of milliseconds to roll. Use negative values to roll
	 *            backwards.<br>
	 * <br>
	 * @return Date rolled.<br>
	 * <br>
	 */
	public static Date rollMilliseconds(Date date, TimeZone timeZone, int amount) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Add.
		calendar.add(Calendar.MILLISECOND, amount);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls forward or backward a date with a given number of seconds.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param amount
	 *            Number of seconds to roll. Use negative values to roll
	 *            backwards.<br>
	 * <br>
	 * @return Date rolled.<br>
	 * <br>
	 */
	public static Date rollSeconds(Date date, TimeZone timeZone, int amount) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Add.
		calendar.add(Calendar.SECOND, amount);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls forward or backward a date with a given number of minutes.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param amount
	 *            Number of minutes to roll. Use negative values to roll
	 *            backwards.<br>
	 * <br>
	 * @return Date rolled.<br>
	 * <br>
	 */
	public static Date rollMinutes(Date date, TimeZone timeZone, int amount) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Add.
		calendar.add(Calendar.MINUTE, amount);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls forward or backward a date with a given number of hours.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param amount
	 *            Number of hours to roll. Use negative values to roll
	 *            backwards.<br>
	 * <br>
	 * @return Date rolled.<br>
	 * <br>
	 */
	public static Date rollHours(Date date, TimeZone timeZone, int amount) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Add.
		calendar.add(Calendar.HOUR_OF_DAY, amount);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls forward or backward a date with a given number of days.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param amount
	 *            Number of days to roll. Use negative values to roll backwards.<br>
	 * <br>
	 * @return Date rolled.<br>
	 * <br>
	 */
	public static Date rollDays(Date date, TimeZone timeZone, int amount) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Add.
		calendar.add(Calendar.DAY_OF_MONTH, amount);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls forward or backward a date with a given number of months.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param amount
	 *            Number of months to roll. Use negative values to roll
	 *            backwards.<br>
	 * <br>
	 * @return Date rolled.<br>
	 * <br>
	 */
	public static Date rollMonths(Date date, TimeZone timeZone, int amount) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Add.
		calendar.add(Calendar.MONTH, amount);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls forward or backward a date with a given number of years.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @param amount
	 *            Number of years to roll. Use negative values to roll
	 *            backwards.<br>
	 * <br>
	 * @return Date rolled.<br>
	 * <br>
	 */
	public static Date rollYears(Date date, TimeZone timeZone, int amount) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Add.
		calendar.add(Calendar.YEAR, amount);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls forward the date to 23 hours, 59 minutes, 59 seconds and 999
	 * milliseconds.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Date rolled to 23:59:59:999.<br>
	 * <br>
	 */
	public static Date rollToLastHHMMSSSSS(Date date, TimeZone timeZone) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Set the time to 23:59:59:999.
		calendar.set(Calendar.HOUR_OF_DAY, HOURS_OF_DAY);
		calendar.set(Calendar.MINUTE, MINUTES_OF_HOUR);
		calendar.set(Calendar.SECOND, SECONDS_OF_MINUTE);
		calendar.set(Calendar.MILLISECOND, MILLISECOND_OF_SECOND);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls forward the date to december 31, 23 hours, 59 minutes, 59 seconds
	 * and 999 milliseconds.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Date rolled to 31 december, 23:59:59:999.<br>
	 * <br>
	 */
	public static Date rollToLastMMDDHHMMSSSSS(Date date, TimeZone timeZone) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Set the time to 23:59:59:999.
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, DAYS_OF_MONTH);
		calendar.set(Calendar.HOUR_OF_DAY, HOURS_OF_DAY);
		calendar.set(Calendar.MINUTE, MINUTES_OF_HOUR);
		calendar.set(Calendar.SECOND, SECONDS_OF_MINUTE);
		calendar.set(Calendar.MILLISECOND, MILLISECOND_OF_SECOND);

		// Return the new date.
		return calendar.getTime();

	}

	/**
	 * Rolls backward the date to janurary 1, 0 hours, 0 minutes, 0 seconds and
	 * 0 milliseconds.
	 * 
	 * @param date
	 *            Date to roll. If <code>null</code> then default date is used.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Date rolled to 31 december, 23:59:59:999.<br>
	 * <br>
	 */
	public static Date rollToFirstMMDDHHMMSSSSS(Date date, TimeZone timeZone) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Set the time to 00:00:00:000.
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// Return the new date.
		return calendar.getTime();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Compares two dates and decides if both of them are the same if year,
	 * month, day, hour, minute and second values are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the year, month, day, hour, minute and
	 *         second of both dates given are the same.<br>
	 * <br>
	 */
	public static boolean equalsYYYYMMDDHHMMSS(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Validate day.
		boolean equalsDayOfMonth = (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2
				.get(Calendar.DAY_OF_MONTH));

		// Validate month.
		boolean equalsMonth = (calendar1.get(Calendar.MONTH) == calendar2
				.get(Calendar.MONTH));

		// Validate year.
		boolean equalsYear = (calendar1.get(Calendar.YEAR) == calendar2
				.get(Calendar.YEAR));

		// Validate hour.
		boolean equalsHourOfDay = (calendar1.get(Calendar.HOUR_OF_DAY) == calendar2
				.get(Calendar.HOUR_OF_DAY));

		// Validate minutes.
		boolean equalsMinute = (calendar1.get(Calendar.MINUTE) == calendar2
				.get(Calendar.MINUTE));

		// Validate seconds.
		boolean equalsSecond = equalsMinute
				&& (calendar1.get(Calendar.SECOND) == calendar2
						.get(Calendar.SECOND));

		// Validate day, month and year.
		boolean validateDayMonthYear = (equalsDayOfMonth && equalsMonth && equalsYear);

		// Validate day, month and year.
		boolean validateHourMinuteSecond = (equalsHourOfDay && equalsMinute && equalsSecond);

		// Compare dates.
		return (validateDayMonthYear && validateHourMinuteSecond);

	}

	/**
	 * Compares two dates and decides if both of them are the same if year,
	 * month, day, hour and minute values are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the year, month, day, hour and minute of
	 *         both dates given are the same.<br>
	 * <br>
	 */
	public static boolean equalsYYYYMMDDHHMM(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Validate day.
		boolean equalsDayOfMonth = (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2
				.get(Calendar.DAY_OF_MONTH));

		// Validate month.
		boolean equalsMonth = (calendar1.get(Calendar.MONTH) == calendar2
				.get(Calendar.MONTH));

		// Validate year.
		boolean equalsYear = (calendar1.get(Calendar.YEAR) == calendar2
				.get(Calendar.YEAR));

		// Validate hour.
		boolean equalsHourOfDay = (calendar1.get(Calendar.HOUR_OF_DAY) == calendar2
				.get(Calendar.HOUR_OF_DAY));

		// Validate minutes.
		boolean equalsMinute = (calendar1.get(Calendar.MINUTE) == calendar2
				.get(Calendar.MINUTE));

		// Validate day, month and year.
		boolean validateDayMonthYear = (equalsDayOfMonth && equalsMonth && equalsYear);

		// Validate day, month and year.
		boolean validateHourMinute = (equalsHourOfDay && equalsMinute);

		// Compare dates.
		return (validateDayMonthYear && validateHourMinute);

	}

	/**
	 * Compares two dates and decides if both of them are the same if year,
	 * month and day values are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the year, month and day of both dates given
	 *         are the same.<br>
	 * <br>
	 */
	public static boolean equalsYYYYMMDD(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Validate day.
		boolean equalsDayOfMonth = (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2
				.get(Calendar.DAY_OF_MONTH));

		// Validate month.
		boolean equalsMonth = (calendar1.get(Calendar.MONTH) == calendar2
				.get(Calendar.MONTH));

		// Validate year.
		boolean equalsYear = (calendar1.get(Calendar.YEAR) == calendar2
				.get(Calendar.YEAR));

		// Compare dates.
		return (equalsDayOfMonth && equalsMonth && equalsYear);

	}

	/**
	 * Compares two dates and decides if both of them are the same if year and
	 * month values are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the year and month of both dates given are
	 *         the same.<br>
	 * <br>
	 */
	public static boolean equalsYYYYMM(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Validate month.
		boolean equalsMonth = (calendar1.get(Calendar.MONTH) == calendar2
				.get(Calendar.MONTH));

		// Validate year.
		boolean equalsYear = (calendar1.get(Calendar.YEAR) == calendar2
				.get(Calendar.YEAR));

		// Compare dates.
		return (equalsMonth && equalsYear);

	}

	/**
	 * Compares two dates and decides if both of them are the same if month and
	 * day values are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the month and day of both dates given are
	 *         the same.<br>
	 * <br>
	 */
	public static boolean equalsMMDD(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return ((calendar1.get(Calendar.DAY_OF_MONTH) == calendar2
				.get(Calendar.DAY_OF_MONTH)) && (calendar1.get(Calendar.MONTH) == calendar2
				.get(Calendar.MONTH)));

	}

	/**
	 * Compares two dates and decides if both of them are the same if the years
	 * are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the year of both dates given are the same.<br>
	 * <br>
	 */
	public static boolean equalsYear(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR));

	}

	/**
	 * Compares two dates and decides if both of them are the same if the months
	 * are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the months of both dates given are the same.<br>
	 * <br>
	 */
	public static boolean equalsMonth(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));

	}

	/**
	 * Compares two dates and decides if both of them are the same if the day of
	 * the months are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the day of the months of both dates given
	 *         are the same.<br>
	 * <br>
	 */
	public static boolean equalsDayOfMonth(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2
				.get(Calendar.DAY_OF_MONTH));

	}

	/**
	 * Compares two dates and decides if both of them are the same if hour,
	 * minute and second values are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the hours, minutes and seconds of both dates
	 *         given are the same.<br>
	 * <br>
	 */
	public static boolean equalsHHMMSS(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return ((calendar1.get(Calendar.HOUR_OF_DAY) == calendar2
				.get(Calendar.HOUR_OF_DAY))
				&& (calendar1.get(Calendar.MINUTE) == calendar2
						.get(Calendar.MINUTE)) && (calendar1
					.get(Calendar.SECOND) == calendar2.get(Calendar.SECOND)));

	}

	/**
	 * Compares two dates and decides if both of them are the same if hour and
	 * minute values are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the hours and minutes of both dates given
	 *         are the same.<br>
	 * <br>
	 */
	public static boolean equalsHHMM(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return ((calendar1.get(Calendar.HOUR_OF_DAY) == calendar2
				.get(Calendar.HOUR_OF_DAY)) && (calendar1.get(Calendar.MINUTE) == calendar2
				.get(Calendar.MINUTE)));

	}

	/**
	 * Compares two dates and decides if both of them are the same if the hours
	 * of the day are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the hours of the day of both dates given are
	 *         the same.<br>
	 * <br>
	 */
	public static boolean equalsHourOfDay(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return (calendar1.get(Calendar.HOUR_OF_DAY) == calendar2
				.get(Calendar.HOUR_OF_DAY));

	}

	/**
	 * Compares two dates and decides if both of them are the same if the
	 * minutes are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the minutes of both dates given are the
	 *         same.<br>
	 * <br>
	 */
	public static boolean equalsMinute(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return (calendar1.get(Calendar.MINUTE) == calendar2
				.get(Calendar.MINUTE));

	}

	/**
	 * Compares two dates and decides if both of them are the same if the
	 * seconds are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the seconds of both dates given are the
	 *         same.<br>
	 * <br>
	 */
	public static boolean equalsSecond(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return (calendar1.get(Calendar.SECOND) == calendar2
				.get(Calendar.SECOND));

	}

	/**
	 * Compares two dates and decides if both of them are the same if the
	 * milliseconds are the same.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return <code>true</code> if the milliseconds of both dates given are the
	 *         same.<br>
	 * <br>
	 */
	public static boolean equalsMillisecond(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {

		// Create the first calendar where to perform the operations with the
		// first date.
		Calendar calendar1 = toCalendar(date1, timeZone1, null);

		// Create the second calendar where to perform the operations with the
		// second date.
		Calendar calendar2 = toCalendar(date2, timeZone2, null);

		// Compare dates.
		return (calendar1.get(Calendar.SECOND) == calendar2
				.get(Calendar.SECOND));

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Compares the year, month and date of two dates.
	 * 
	 * @param date1
	 *            First date to compare.<br>
	 * <br>
	 * @param timeZone1
	 *            TimeZone for the first date. If <code>null</code> then default
	 *            time zone is used.<br>
	 * <br>
	 * @param date2
	 *            Second date to compare.<br>
	 * <br>
	 * @param timeZone2
	 *            TimeZone for the second date. If <code>null</code> then
	 *            default time zone is used.<br>
	 * <br>
	 * @return Zero if the argument Date1 is equal to Date2; a value less than 0
	 *         if Date1 is before the Date2 argument; and a value greater than 0
	 *         if Date1 is after the Date2 argument.<br>
	 * <br>
	 */
	public static int compareYYYYMMDD(Date date1, TimeZone timeZone1,
			Date date2, TimeZone timeZone2) {
		return zeroOutHHMMSS(date1, timeZone1).compareTo(
				zeroOutHHMMSS(date2, timeZone2));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Decides if given value is a valid hour.
	 * 
	 * @param hour
	 *            Hour.<br>
	 * <br>
	 * @return <code>true</code> if it's in [0..23] range.<br>
	 * <br>
	 */
	public static boolean validateHourOfDay(int hour) {
		return ((hour >= 0) && (hour <= HOURS_OF_DAY));
	}

	/**
	 * Decides if given value is a valid minute.
	 * 
	 * @param minute
	 *            Minute.<br>
	 * <br>
	 * @return <code>true</code> if it's in [0..59] range.<br>
	 * <br>
	 */
	public static boolean validateMinute(int minute) {
		return ((minute >= 0) && (minute <= SECONDS_OF_MINUTE));
	}

	/**
	 * Decides if given value is a valid second.
	 * 
	 * @param second
	 *            Second.<br>
	 * <br>
	 * @return <code>true</code> if it's in [0..59] range.<br>
	 * <br>
	 */
	public static boolean validateSecond(int second) {
		return validateMinute(second);
	}

	/**
	 * Decides if given value is a valid millisecond.
	 * 
	 * @param millisecond
	 *            Millisecond.<br>
	 * <br>
	 * @return <code>true</code> if it's in [0..999] range.<br>
	 * <br>
	 */
	public static boolean validateMillisecond(int millisecond) {
		return ((millisecond >= 0) && (millisecond <= MILLISECOND_OF_SECOND));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets to zero the hours, minutes, seconds and milliseconds of a given
	 * date.
	 * 
	 * @param date
	 *            Date to change.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Date with the hours, minutes, seconds and milliseconds set to
	 *         zero.<br>
	 * <br>
	 */
	public static Date zeroOutHHMMSS(Date date, TimeZone timeZone) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Set hours to zero.
		if (calendar.get(Calendar.HOUR_OF_DAY) != 0) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
		}

		// Set minutes to zero.
		if (calendar.get(Calendar.MINUTE) != 0) {
			calendar.set(Calendar.MINUTE, 0);
		}

		// Set seconds to zero.
		if (calendar.get(Calendar.SECOND) != 0) {
			calendar.set(Calendar.SECOND, 0);
		}

		// Set milliseconds to zero.
		if (calendar.get(Calendar.MILLISECOND) != 0) {
			calendar.set(Calendar.MILLISECOND, 0);
		}

		//
		return calendar.getTime();

	}

	/**
	 * Sets to zero the minutes, seconds and milliseconds of a given date.
	 * 
	 * @param date
	 *            Date to change.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Date with the minutes, seconds and milliseconds set to zero.<br>
	 * <br>
	 */
	public static Date zeroOutMMSS(Date date, TimeZone timeZone) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Set minutes to zero.
		if (calendar.get(Calendar.MINUTE) != 0) {
			calendar.set(Calendar.MINUTE, 0);
		}

		// Set seconds to zero.
		if (calendar.get(Calendar.SECOND) != 0) {
			calendar.set(Calendar.SECOND, 0);
		}

		// Set milliseconds to zero.
		if (calendar.get(Calendar.MILLISECOND) != 0) {
			calendar.set(Calendar.MILLISECOND, 0);
		}

		//
		return calendar.getTime();

	}

	/**
	 * Sets to zero the seconds and milliseconds of a given date.
	 * 
	 * @param date
	 *            Date to change.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Date with the seconds and milliseconds set to zero.<br>
	 * <br>
	 */
	public static Date zeroOutSecond(Date date, TimeZone timeZone) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Set seconds to zero.
		if (calendar.get(Calendar.SECOND) != 0) {
			calendar.set(Calendar.SECOND, 0);
		}

		// Set milliseconds to zero.
		if (calendar.get(Calendar.MILLISECOND) != 0) {
			calendar.set(Calendar.MILLISECOND, 0);
		}

		//
		return calendar.getTime();

	}

	/**
	 * Sets to zero the milliseconds of a given date.
	 * 
	 * @param date
	 *            Date to change.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Date with the milliseconds set to zero.<br>
	 * <br>
	 */
	public static Date zeroOutMillisecond(Date date, TimeZone timeZone) {

		// Create a calendar.
		Calendar calendar = toCalendar(date, timeZone, null);

		// Set milliseconds to zero.
		if (calendar.get(Calendar.MILLISECOND) != 0) {
			calendar.set(Calendar.MILLISECOND, 0);
		}

		//
		return calendar.getTime();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Decides if a given date is today.
	 * 
	 * @param date
	 *            Date to validate.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return <code>true</code> if given date is today and false if not.<br>
	 * <br>
	 */
	public static boolean isToday(Date date, TimeZone timeZone) {

		// Create a calendar with the current date.
		Calendar today = toCalendar(null, timeZone, null);

		// Validate if given date is equals to YYYY-MM-DD of calendar date.
		return (equalsYYYYMMDD(date, timeZone, today.getTime(), timeZone));

	}

	/**
	 * Decides if a given date is before today.
	 * 
	 * @param date
	 *            Date to validate.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return <code>true</code> if given date is before today and false if not.<br>
	 * <br>
	 */
	public static boolean isBeforeToday(Date date, TimeZone timeZone) {

		// Create a calendar with the current date.
		Calendar today = toCalendar(null, timeZone, null);

		// Get the year of the date.
		int dateYear = getYear(date, timeZone);

		// Validate year.
		if (dateYear < today.get(Calendar.YEAR)) {
			return true;
		} else if (dateYear > today.get(Calendar.YEAR)) {
			return false;
		} else {

			// Get the month of the date.
			int dateMonth = getMonth(date, timeZone);

			// Validate month.
			if (dateMonth < today.get(Calendar.MONTH)) {
				return true;
			} else if (dateMonth > today.get(Calendar.MONTH)) {
				return false;
			} else {
				return (getDayOfMonth(date, timeZone) < today
						.get(Calendar.DAY_OF_MONTH));
			}

		}

	}

	/**
	 * Decides if a given date is after today.
	 * 
	 * @param date
	 *            Date to validate.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return <code>true</code> if given date is after today and false if not.<br>
	 * <br>
	 */
	public static boolean isAfterToday(Date date, TimeZone timeZone) {

		// Create a calendar with the current date.
		Calendar today = toCalendar(null, timeZone, null);

		// Get the year of the date.
		int dateYear = getYear(date, timeZone);

		// Validate year.
		if (dateYear > today.get(Calendar.YEAR)) {
			return true;
		} else if (dateYear < today.get(Calendar.YEAR)) {
			return false;
		} else {

			// Get the month of the date.
			int dateMonth = getMonth(date, timeZone);

			// Validate month.
			if (dateMonth > today.get(Calendar.MONTH)) {
				return true;
			} else if (dateMonth < today.get(Calendar.MONTH)) {
				return false;
			} else {
				return (getDayOfMonth(date, timeZone) > today
						.get(Calendar.DAY_OF_MONTH));
			}

		}

	}

	/**
	 * Decides if today is birthday.
	 * 
	 * @param birthdate
	 *            Date to validate.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return <code>true</code> if given date is birthday and false if not.<br>
	 * <br>
	 */
	public static boolean isBirthday(Date birthdate, TimeZone timeZone) {

		// Get the current date.
		Date now = getCurrentDate(timeZone);

		// Compare two dates and decides if both of them are the same if month
		// and day values are the same.
		return equalsMMDD(birthdate, timeZone, now, timeZone);

	}

	/**
	 * Decides if a date is in the range of two dates.
	 * 
	 * @param date
	 *            Date to validate.<br>
	 * <br>
	 * @param min
	 *            Upper limit.<br>
	 * <br>
	 * @param max
	 *            Lower limit.<br>
	 * <br>
	 * @return It is true if given date is greater or equals to min argument and
	 *         lower or equals to max argument.<br>
	 * <br>
	 */
	public static boolean isDateInRange(Date date, Date min, Date max) {
		return ((date.compareTo(min) >= 0) && (date.compareTo(max) <= 0));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Calculates the age of someone or something.
	 * 
	 * @param dateOfBirth
	 *            Date of birth.<br>
	 * <br>
	 * @param timeZone
	 *            TimeZone for the date. If <code>null</code> then default time
	 *            zone is used.<br>
	 * <br>
	 * @return Age.<br>
	 * <br>
	 */
	public static int getAge(Date dateOfBirth, TimeZone timeZone) {

		// Create the calendar for the date of birth.
		Calendar birthCalendar = toCalendar(dateOfBirth, timeZone, null);

		// Get a calendar with the current date.
		Calendar currentDateCalendar = toCalendar(null, timeZone, null);

		// Calculate the difference between the current year and the year of
		// birth.
		int difference = currentDateCalendar.get(Calendar.YEAR)
				- birthCalendar.get(Calendar.YEAR);

		// Substract one year if required.
		if ((birthCalendar.get(Calendar.MONTH) > currentDateCalendar
				.get(Calendar.MONTH))
				|| (birthCalendar.get(Calendar.MONTH) == currentDateCalendar
						.get(Calendar.MONTH) && birthCalendar
						.get(Calendar.DAY_OF_MONTH) > currentDateCalendar
						.get(Calendar.DAY_OF_MONTH))) {
			difference--;
		}

		// Return the difference.
		return difference;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Specifies a default value for an unknown date.
	 * 
	 * @return Unknown date.<br>
	 * <br>
	 */
	private static Date createUnknownDate() {
		try {
			return toDateDDMMYYYYHHMMSSSSS("1900/01/01 00:00:00:000",
					TimeZone.getTimeZone("Europe/London"));
		} catch (ParseException e) {
			return null;
		}
	}

}
