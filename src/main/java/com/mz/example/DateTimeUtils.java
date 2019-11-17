package com.mz.example;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@UtilityClass
public class DateTimeUtils {

    private static DatatypeFactory DATATYPE_FACTORY;

    static {
        try{
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        }catch(DatatypeConfigurationException ex){
            log.error("Unable to configure application. Shutting down.", ex);
            System.exit(-1);
        }
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(ZonedDateTime zdt) {
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(zdt);
        return DATATYPE_FACTORY.newXMLGregorianCalendar(gregorianCalendar);
    }

    private static Optional<ZonedDateTime> xmlGregorianCalendarToZonedDateTimeInternal(XMLGregorianCalendar calendar, ZoneId zoneId){
        return Optional.ofNullable(xmlGregorianCalendarToZonedDateTime(calendar)).map(zdt -> zdt.withZoneSameInstant(zoneId));
    }

    private static <T> T xmlGregorianCalendarToGivenType(XMLGregorianCalendar calendar, ZoneId zoneId,
                                                         Function<ZonedDateTime, T> converter){
        return xmlGregorianCalendarToZonedDateTimeInternal(calendar, zoneId).map(converter).orElse(null);
    }

    /**
     * @param calendar Date and time as {@link XMLGregorianCalendar}
     * @return {@link ZonedDateTime} in timezone same as provided in {@link XMLGregorianCalendar}
     */
    public static ZonedDateTime xmlGregorianCalendarToZonedDateTime(XMLGregorianCalendar calendar){
        if(calendar == null){
            return null;
        }
        return calendar.toGregorianCalendar().toZonedDateTime();
    }

    /**
     * @param calendar Date and time as {@link XMLGregorianCalendar}
     * @return {@link LocalDate} object in GMT timezone
     */
    public static LocalDate xmlGregorianCalendarToLocalDateInGMT(XMLGregorianCalendar calendar){
        return xmlGregorianCalendarToLocalDate(calendar, ZoneId.of("GMT"));
    }

    /**
     * @param calendar Date and time as {@link XMLGregorianCalendar}
     * @return {@link LocalDate} object in {@link ZoneId#systemDefault()} timezone
     */
    public static LocalDate xmlGregorianCalendarToLocalDate(XMLGregorianCalendar calendar){
        return xmlGregorianCalendarToLocalDate(calendar, ZoneId.systemDefault());
    }

    /**
     * @param calendar Date and time as {@link XMLGregorianCalendar}
     * @param zoneId Timezone to be used when converting to {@link LocalDate} object
     * @return {@link LocalDate} object in given {@link ZoneId} - timezone
     */
    public static LocalDate xmlGregorianCalendarToLocalDate(XMLGregorianCalendar calendar, ZoneId zoneId){
        return xmlGregorianCalendarToGivenType(calendar, zoneId, ZonedDateTime::toLocalDate);
    }

    /**
     * @param calendar Date and time as {@link XMLGregorianCalendar}
     * @return {@link LocalDateTime} object in GMT timezone
     */
    public static LocalDateTime xmlGregorianCalendarToLocalDateTimeInGMT(XMLGregorianCalendar calendar){
        return xmlGregorianCalendarToLocalDateTime(calendar, ZoneId.of("GMT"));
    }

    /**
     * @param calendar Date and time as {@link XMLGregorianCalendar}
     * @return {@link LocalDateTime} object in {@link ZoneId#systemDefault()} timezone
     */
    public static LocalDateTime xmlGregorianCalendarToLocalDateTime(XMLGregorianCalendar calendar){
        return xmlGregorianCalendarToLocalDateTime(calendar, ZoneId.systemDefault());
    }

    /**
     * @param calendar Date and time as {@link XMLGregorianCalendar}
     * @param zoneId Timezone to be used when converting to {@link LocalDateTime} object
     * @return {@link LocalDateTime} object in given {@link ZoneId} - timezone
     */
    public static LocalDateTime xmlGregorianCalendarToLocalDateTime(XMLGregorianCalendar calendar, ZoneId zoneId){
        return xmlGregorianCalendarToGivenType(calendar, zoneId, ZonedDateTime::toLocalDateTime);
    }
}
