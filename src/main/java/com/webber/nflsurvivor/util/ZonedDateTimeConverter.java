package com.webber.nflsurvivor.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, String> {
    @Override
    public String convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        // Store as ISO-8601 format: UTC + Zone ID
        Instant utcInstant = zonedDateTime.toInstant(); // UTC instant
        String zoneId = zonedDateTime.getZone().toString(); // Zone ID
        return utcInstant.toString() + "|" + zoneId; // Combine with a separator
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(String dbData) {
        if (dbData == null || !dbData.contains("|")) {
            return null;
        }
        String[] parts = dbData.split("\\|");
        Instant utcInstant = Instant.parse(parts[0]); // Parse UTC time
        ZoneId zoneId = ZoneId.of(parts[1]);          // Parse time zone
        return ZonedDateTime.ofInstant(utcInstant, zoneId);
    }
}
