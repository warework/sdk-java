package com.warework.core.util.helper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.warework.core.scope.AbstractModCoreExtTestCase;

/**
 * 
 */
public class DateL2HelperTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	private static final TimeZone TIME_ZONE_London = TimeZone
			.getTimeZone("Europe/London");

	private static final TimeZone TIME_ZONE_Madrid = TimeZone
			.getTimeZone("Europe/Madrid");;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testToCalendarA1() {

		try {

			//
			if (DateL2Helper.toCalendar(null, null, null) == null) {
				fail();
			}

			//
			Date date = new Date();
			if (!DateL2Helper.toCalendar(date, null, null).getTime()
					.equals(date)) {
				fail();
			}

			//
			{

				//
				Calendar calendar1 = DateL2Helper.toCalendar(date,
						TIME_ZONE_London, null);

				//
				Calendar calendar2 = DateL2Helper.toCalendar(date,
						TIME_ZONE_Madrid, null);

				//
				if (calendar1.get(Calendar.HOUR_OF_DAY) == (calendar2
						.get(Calendar.HOUR_OF_DAY))) {
					fail();
				}

			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToDateA1() {

		try {

			// Get the current time in London.
			// NOTE: Remember that when you do .toString() you apply your
			// TimeZone
			Date dateLondon = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			// Get the current time in Madrid.
			Date dateMadrid = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_Madrid);

			//
			if (dateLondon.getTime() == dateMadrid.getTime()) {
				fail();
			}

			// Validate that HOUR is the same.
			if (DateL2Helper.getHourOfDay(dateLondon, TIME_ZONE_London) != DateL2Helper
					.getHourOfDay(dateMadrid, TIME_ZONE_Madrid)) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToDateYYYYMMDD() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		Date date2 = null;
		try {
			date2 = DateL2Helper.toDateYYYYMMDD("2010/06/20", TIME_ZONE_London);
		} catch (ParseException e) {
			fail();
		}

		//
		if (!DateL2Helper.equalsYYYYMMDD(date1, TIME_ZONE_London, date2,
				TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToDateYYYYMMDDHHMM() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		Date date2 = null;
		try {
			date2 = DateL2Helper.toDateYYYYMMDDHHMM("2010/06/20 14:37",
					TIME_ZONE_London);
		} catch (ParseException e) {
			fail();
		}

		//
		if (!DateL2Helper.equalsYYYYMMDDHHMM(date1, TIME_ZONE_London, date2,
				TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToDateYYYYMMDDHHMMSS() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		Date date2 = null;
		try {
			date2 = DateL2Helper.toDateYYYYMMDDHHMMSS("2010/06/20 14:37:22",
					TIME_ZONE_London);
		} catch (ParseException e) {
			fail();
		}

		//
		if (!DateL2Helper.equalsYYYYMMDDHHMMSS(date1, TIME_ZONE_London, date2,
				TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToDateDDMMYYYY() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		Date date2 = null;
		try {
			date2 = DateL2Helper.toDateDDMMYYYY("20/06/2010", TIME_ZONE_London);
		} catch (ParseException e) {
			fail();
		}

		//
		if (!DateL2Helper.equalsYYYYMMDD(date1, TIME_ZONE_London, date2,
				TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToDateDDMMYYYYHHMM() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		Date date2 = null;
		try {
			date2 = DateL2Helper.toDateDDMMYYYYHHMM("20/06/2010 14:37",
					TIME_ZONE_London);
		} catch (ParseException e) {
			fail();
		}

		//
		if (!DateL2Helper.equalsYYYYMMDDHHMM(date1, TIME_ZONE_London, date2,
				TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToDateDDMMYYYYHHMMSS() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		Date date2 = null;
		try {
			date2 = DateL2Helper.toDateDDMMYYYYHHMMSS("20/06/2010 14:37:22",
					TIME_ZONE_London);
		} catch (ParseException e) {
			fail();
		}

		//
		if (!DateL2Helper.equalsYYYYMMDDHHMMSS(date1, TIME_ZONE_London, date2,
				TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStringYYYYMMDD() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.toStringYYYYMMDD(date1, TIME_ZONE_London).equals(
				"2010/06/20")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStringYYYYMMDDHHMM() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.toStringYYYYMMDDHHMM(date1, TIME_ZONE_London).equals(
				"2010/06/20 14:37")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStringYYYYMMDDHHMMSS() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.toStringYYYYMMDDHHMMSS(date1, TIME_ZONE_London)
				.equals("2010/06/20 14:37:22")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStringDDMMYYYY() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.toStringDDMMYYYY(date1, TIME_ZONE_London).equals(
				"20/06/2010")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStringDDMMYYYYHHMM() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.toStringDDMMYYYYHHMM(date1, TIME_ZONE_London).equals(
				"20/06/2010 14:37")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStringDDMMYYYYHHMMSS() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.toStringDDMMYYYYHHMMSS(date1, TIME_ZONE_London)
				.equals("20/06/2010 14:37:22")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStringHHMM() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.toStringHHMM(date1, TIME_ZONE_London).equals("14:37")) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testToStringHHMMSS() {

		//
		Date date1 = DateL2Helper.toDate(2010, 5, 20, 14, 37, 22, 456,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.toStringHHMMSS(date1, TIME_ZONE_London).equals(
				"14:37:22")) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetCurrentDateA1() {

		//
		Date dateLondon = DateL2Helper.getCurrentDate(TIME_ZONE_London);

		//
		Date dateMadrid = DateL2Helper.getCurrentDate(TIME_ZONE_Madrid);

		//
		if (DateL2Helper.getHourOfDay(dateLondon, TIME_ZONE_London) == DateL2Helper
				.getHourOfDay(dateMadrid, TIME_ZONE_Madrid)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetHourOfDayA1() {

		try {

			//
			Date date = new Date();

			//
			if (DateL2Helper.getHourOfDay(date, null) != DateL2Helper
					.getHourOfDay(date, null)) {
				fail();
			}

			//
			if (DateL2Helper.getHourOfDay(date, TIME_ZONE_London) == (DateL2Helper
					.getHourOfDay(date, TIME_ZONE_Madrid))) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetMillisecondsDifferenceA1() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date dateLondon2 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 5,
					TIME_ZONE_London);

			//
			if (DateL2Helper.getMillisecondsDifference(dateLondon1,
					TIME_ZONE_London, dateLondon2, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			if (DateL2Helper.getMillisecondsDifference(dateLondon2,
					TIME_ZONE_London, dateLondon1, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			Date dateMadrid = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_Madrid);

			//
			if (DateL2Helper.getMillisecondsDifference(dateLondon1,
					TIME_ZONE_London, dateMadrid, TIME_ZONE_Madrid) != DateL2Helper.HOUR_MILLIS) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetSecondsDifferenceA1() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date dateLondon2 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 5, 0,
					TIME_ZONE_London);

			//
			if (DateL2Helper.getSecondsDifference(dateLondon1,
					TIME_ZONE_London, dateLondon2, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			if (DateL2Helper.getSecondsDifference(dateLondon2,
					TIME_ZONE_London, dateLondon1, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetMinutesDifferenceA1() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date dateLondon2 = DateL2Helper.toDate(2000, 0, 1, 0, 5, 0, 0,
					TIME_ZONE_London);

			//
			if (DateL2Helper.getMinutesDifference(dateLondon1,
					TIME_ZONE_London, dateLondon2, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			if (DateL2Helper.getMinutesDifference(dateLondon2,
					TIME_ZONE_London, dateLondon1, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetHoursDifferenceA1() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date dateLondon2 = DateL2Helper.toDate(2000, 0, 1, 5, 0, 0, 0,
					TIME_ZONE_London);

			//
			if (DateL2Helper.getHoursDifference(dateLondon1, TIME_ZONE_London,
					dateLondon2, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			if (DateL2Helper.getHoursDifference(dateLondon2, TIME_ZONE_London,
					dateLondon1, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetDaysDifferenceA1() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date dateLondon2 = DateL2Helper.toDate(2000, 0, 6, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			if (DateL2Helper.getDaysDifference(dateLondon1, TIME_ZONE_London,
					dateLondon2, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			if (DateL2Helper.getDaysDifference(dateLondon2, TIME_ZONE_London,
					dateLondon1, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetYearsDifferenceA1() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date dateLondon2 = DateL2Helper.toDate(2005, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			if (DateL2Helper.getYearsDifference(dateLondon1, TIME_ZONE_London,
					dateLondon2, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			if (DateL2Helper.getYearsDifference(dateLondon2, TIME_ZONE_London,
					dateLondon1, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testRollMilliseconds() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date updated = DateL2Helper.rollMilliseconds(dateLondon1,
					TIME_ZONE_London, 5);

			//
			if (DateL2Helper.getMillisecondsDifference(dateLondon1,
					TIME_ZONE_London, updated, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			updated = DateL2Helper.rollMilliseconds(updated, TIME_ZONE_London,
					-10);
			if (DateL2Helper.getMillisecondsDifference(dateLondon1,
					TIME_ZONE_London, updated, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRollSeconds() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date updated = DateL2Helper.rollSeconds(dateLondon1,
					TIME_ZONE_London, 5);

			//
			if (DateL2Helper.getSecondsDifference(dateLondon1,
					TIME_ZONE_London, updated, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			updated = DateL2Helper.rollSeconds(updated, TIME_ZONE_London, -10);
			if (DateL2Helper.getSecondsDifference(dateLondon1,
					TIME_ZONE_London, updated, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRollMinutes() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date updated = DateL2Helper.rollMinutes(dateLondon1,
					TIME_ZONE_London, 5);

			//
			if (DateL2Helper.getMinutesDifference(dateLondon1,
					TIME_ZONE_London, updated, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			updated = DateL2Helper.rollMinutes(updated, TIME_ZONE_London, -10);
			if (DateL2Helper.getMinutesDifference(dateLondon1,
					TIME_ZONE_London, updated, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRollHours() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date updated = DateL2Helper.rollHours(dateLondon1,
					TIME_ZONE_London, 5);

			//
			if (DateL2Helper.getHoursDifference(dateLondon1, TIME_ZONE_London,
					updated, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			updated = DateL2Helper.rollHours(updated, TIME_ZONE_London, -10);
			if (DateL2Helper.getHoursDifference(dateLondon1, TIME_ZONE_London,
					updated, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRollDays() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date updated = DateL2Helper.rollDays(dateLondon1, TIME_ZONE_London,
					5);

			//
			if (DateL2Helper.getDaysDifference(dateLondon1, TIME_ZONE_London,
					updated, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			updated = DateL2Helper.rollDays(updated, TIME_ZONE_London, -10);
			if (DateL2Helper.getDaysDifference(dateLondon1, TIME_ZONE_London,
					updated, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRollYears() {

		try {

			//
			Date dateLondon1 = DateL2Helper.toDate(2000, 0, 1, 0, 0, 0, 0,
					TIME_ZONE_London);

			//
			Date updated = DateL2Helper.rollYears(dateLondon1,
					TIME_ZONE_London, 5);

			//
			if (DateL2Helper.getYearsDifference(dateLondon1, TIME_ZONE_London,
					updated, TIME_ZONE_London) != 5) {
				fail();
			}

			//
			updated = DateL2Helper.rollYears(updated, TIME_ZONE_London, -10);
			if (DateL2Helper.getYearsDifference(dateLondon1, TIME_ZONE_London,
					updated, TIME_ZONE_London) != 5) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testEqualsYYYYMMDDHHMMSS() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsYYYYMMDDHHMMSS(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsYYYYMMDDHHMM() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsYYYYMMDDHHMM(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsYYYYMMDD() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 22, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsYYYYMMDD(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

		//
		Date dateMadrid1 = DateL2Helper.toDate(1999, 11, 31, 0, 0, 0, 0,
				TIME_ZONE_Madrid);

		//
		if (!DateL2Helper.equalsYYYYMMDD(dateLondon1, TIME_ZONE_London,
				dateMadrid1, TIME_ZONE_Madrid)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsYYYYMM() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsYYYYMM(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsMMDD() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsMMDD(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsYear() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsYear(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsMonth() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsMonth(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsDayOfMonth() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsDayOfMonth(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsHHMMSS() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsHHMMSS(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsHHMM() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsHHMM(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsHourOfDay() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsHourOfDay(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsMinute() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		Date dateLondon2 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsMinute(dateLondon1, TIME_ZONE_London,
				dateLondon2, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsSecond() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsSecond(dateLondon1, TIME_ZONE_London,
				dateLondon1, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEqualsMillisecond() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 0, 0,
				TIME_ZONE_London);

		//
		if (!DateL2Helper.equalsMillisecond(dateLondon1, TIME_ZONE_London,
				dateLondon1, TIME_ZONE_London)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testZeroOutHHMMSS() {

		//
		Date dateLondon1 = DateL2Helper.toDate(1999, 11, 31, 23, 30, 55, 999,
				TIME_ZONE_London);

		//
		Date updated = DateL2Helper
				.zeroOutHHMMSS(dateLondon1, TIME_ZONE_London);

		//
		if ((DateL2Helper.getHourOfDay(updated, TIME_ZONE_London) != 0)
				|| (DateL2Helper.getMinute(updated, TIME_ZONE_London) != 0)
				|| (DateL2Helper.getSecond(updated, TIME_ZONE_London) != 0)
				|| (DateL2Helper.getMillisecond(updated, TIME_ZONE_London) != 0)) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testIsToday() {

		if (!DateL2Helper
				.isToday(DateL2Helper.toCalendar(null, TIME_ZONE_London, null)
						.getTime(), TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testIsBeforeToday() {

		//
		Date now = DateL2Helper.toCalendar(null, TIME_ZONE_London, null)
				.getTime();

		//
		Date date = DateL2Helper.rollDays(now, TIME_ZONE_London, -1);

		//
		if (!DateL2Helper.isBeforeToday(date, TIME_ZONE_London)) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testIsAfterToday() {

		//
		Date now = DateL2Helper.toCalendar(null, TIME_ZONE_London, null)
				.getTime();

		//
		Date date = DateL2Helper.rollDays(now, TIME_ZONE_London, 1);

		//
		if (!DateL2Helper.isAfterToday(date, TIME_ZONE_London)) {
			fail();
		}

	}

}
