package de.borisskert.springboot.liquibaseexample.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Converts UUID to String and vice versa.
 * See: <a href="https://stackoverflow.com/a/72657194/13213024">PSQLException: ERROR: operator does not exist: character varying = uuid</a>
 */
@Component
@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, String> {
    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UUID.fromString(dbData);
    }
}
