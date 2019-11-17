package com.mz.example;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

public class DateTimeUtilsTest {

    @Test
    public void testConvertingGTMTimeToGMT() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-04-11T07:30:47Z");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTimeInGMT(calendar);
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.APRIL, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
        Assert.assertEquals(7, converted.getHour());
        Assert.assertEquals(30, converted.getMinute());
        Assert.assertEquals(47, converted.getSecond());
    }

    @Test
    public void testConvertingGTMTimeToCEST() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-04-11T07:30:47Z");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTime(calendar, ZoneId.of("CET"));//This will be GMT+2 for April
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.APRIL, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
        Assert.assertEquals(9, converted.getHour());
        Assert.assertEquals(30, converted.getMinute());
        Assert.assertEquals(47, converted.getSecond());
    }

    @Test
    public void testConvertingCESTTimeToGMT() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-04-11T07:30:47+02:00");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTimeInGMT(calendar);
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.APRIL, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
        Assert.assertEquals(5, converted.getHour());
        Assert.assertEquals(30, converted.getMinute());
        Assert.assertEquals(47, converted.getSecond());
    }

    @Test
    public void testConvertingCESTTimeToCEST() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-04-11T07:30:47+02:00");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTime(calendar, ZoneId.of("CET"));//This will be GMT+2 for April
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.APRIL, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
        Assert.assertEquals(7, converted.getHour());
        Assert.assertEquals(30, converted.getMinute());
        Assert.assertEquals(47, converted.getSecond());
    }

    @Test
    public void testConvertingGTMDateToCESTSameDay() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-04-11T07:30:47Z");
        LocalDate converted = DateTimeUtils.xmlGregorianCalendarToLocalDate(calendar, ZoneId.of("CET"));//This will be GMT+2 for April
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.APRIL, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
    }

    @Test
    public void testConvertingGTMDateToCESTDifferentDay() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-04-10T23:30:47Z");
        LocalDate converted = DateTimeUtils.xmlGregorianCalendarToLocalDate(calendar, ZoneId.of("CET"));//This will be GMT+2 for April
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.APRIL, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
    }

    @Test
    public void testConvertingCESTDateToGMTSameDay() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-04-11T07:30:47+02:00");
        LocalDate converted = DateTimeUtils.xmlGregorianCalendarToLocalDateInGMT(calendar);
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.APRIL, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
    }

    @Test
    public void testConvertingCESTDateToGMTDifferentDay() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-04-11T01:30:47+02:00");
        LocalDate converted = DateTimeUtils.xmlGregorianCalendarToLocalDateInGMT(calendar);
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.APRIL, converted.getMonth());
        Assert.assertEquals(10, converted.getDayOfMonth());
    }

    @Test
    public void testConvertingCETTimeToGMT() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-01-11T07:30:47Z");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTime(calendar, ZoneId.of("CET"));//This will be GMT+1 for January
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.JANUARY, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
        Assert.assertEquals(8, converted.getHour());
        Assert.assertEquals(30, converted.getMinute());
        Assert.assertEquals(47, converted.getSecond());
    }

    @Test
    public void testConvertingCETDateToGMTSameDay() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-01-11T07:30:47+01:00");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTimeInGMT(calendar);
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.JANUARY, converted.getMonth());
        Assert.assertEquals(11, converted.getDayOfMonth());
    }

    @Test
    public void testConvertingCETDateToGMTDifferentDay() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-01-11T00:30:47+01:00");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTimeInGMT(calendar);
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.JANUARY, converted.getMonth());
        Assert.assertEquals(10, converted.getDayOfMonth());
    }

    @Test
    public void testConvertingGMTTimeToCETNoTimeSwitch() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-03-31T00:59:59Z");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTime(calendar, ZoneId.of("CET"));//This will be GMT+1 for 31 March before 2:00AM and GTM+2 since 2:00AM
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.MARCH, converted.getMonth());
        Assert.assertEquals(31, converted.getDayOfMonth());
        Assert.assertEquals(1, converted.getHour());
        Assert.assertEquals(59, converted.getMinute());
        Assert.assertEquals(59, converted.getSecond());
    }

    @Test
    public void testConvertingGMTTimeToCETTimeSwitchToCEST() throws Exception {
        XMLGregorianCalendar calendar = createCalendarForDateTimeString("2019-03-31T01:00:00Z");
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTime(calendar, ZoneId.of("CET"));//This will be GMT+1 for 31 March before 2:00AM and GTM+2 since 2:00AM
        Assert.assertEquals(2019, converted.getYear());
        Assert.assertEquals(Month.MARCH, converted.getMonth());
        Assert.assertEquals(31, converted.getDayOfMonth());
        Assert.assertEquals(3, converted.getHour());
        Assert.assertEquals(0, converted.getMinute());
        Assert.assertEquals(0, converted.getSecond());
    }

    @Test
    public void testConvertingToLocalDateTimeAllowsNullInput() {
        LocalDateTime converted = DateTimeUtils.xmlGregorianCalendarToLocalDateTime(null);
        Assert.assertNull(converted);
    }

    @Test
    public void testConvertingToLocalDateAllowsNullInput() {
        LocalDate converted = DateTimeUtils.xmlGregorianCalendarToLocalDate(null);
        Assert.assertNull(converted);
    }

    private XMLGregorianCalendar createCalendarForDateTimeString(String dateTimeString) throws Exception{
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
    }
}
