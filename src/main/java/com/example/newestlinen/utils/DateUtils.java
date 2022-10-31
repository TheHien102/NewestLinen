package com.example.newestlinen.utils;


import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

@Slf4j
public class DateUtils {

	public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";

	private DateUtils(){

	}

	public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
          .atZone(ZoneId.systemDefault())
          .toInstant());
    }
	
	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		return format.format(date);
	}

	public static String formatDate(Date date, String format ) {
		SimpleDateFormat fm  = new SimpleDateFormat(format);
		return fm.format(date);
	}
	public static Date converDate(String date, String format) {

		try {
			SimpleDateFormat fm = new SimpleDateFormat(format);
			return fm.parse(date);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	public static Date converDate(String date) {
		
		try {
			SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
			return format.parse(date);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	public static boolean isInRangeXMinutesAgo(Date date, int minutes) {
	    Instant instant = Instant.ofEpochMilli(date.getTime());
	    Instant minutesAgo = Instant.now().minus(Duration.ofMinutes(minutes));

	    try {
	        return minutesAgo.isBefore(instant);
	    } catch (Exception e) {
			log.error(e.getMessage(),e);
	    }
	    return false;
	}
	public static boolean isAtLeastXSecondsAgo(Date date, int seconds) {
	    Instant instant = Instant.ofEpochMilli(date.getTime());
	    Instant secondsAgo = Instant.now().minus(Duration.ofSeconds(seconds));

	    try {
	        return instant.isBefore(secondsAgo);
	    } catch (Exception e) {
			log.error(e.getMessage(),e);
	    }
	    return false;
	}

	public static Date startOfDay(Date date) {
		OffsetDateTime offsetDateTime = date.toInstant()
				.atOffset(ZoneOffset.UTC);
		OffsetDateTime reallyStartOfDay = offsetDateTime.withHour(0).withMinute(0).withSecond(0).withNano(000000000);

		return Date.from(reallyStartOfDay.toLocalDateTime().toInstant(ZoneOffset.UTC));

	}
	public static Date convertLocalDate2Date(LocalDate localDate){
		ZoneId defaultZoneId = ZoneId.systemDefault();
		return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
	}

	public static LocalDate convertDate2LocalDate(Date date) {
		return date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}
	public static Date endOfDay(Date date) {
		OffsetDateTime offsetDateTime = date.toInstant()
				.atOffset(ZoneOffset.UTC);
		OffsetDateTime reallyEndOfDay = offsetDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
		return Date.from(reallyEndOfDay.toLocalDateTime().toInstant(ZoneOffset.UTC));
	}

}
