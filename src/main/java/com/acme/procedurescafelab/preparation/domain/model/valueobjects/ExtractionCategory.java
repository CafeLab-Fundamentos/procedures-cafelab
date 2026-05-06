package com.acme.procedurescafelab.preparation.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum ExtractionCategory {
    COFFEE("coffee"),
    ESPRESSO("espresso");

    private final String value;

    ExtractionCategory(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ExtractionCategory fromString(String text) {
        if (text == null) {
            return null;
        }
        
        for (ExtractionCategory category : ExtractionCategory.values()) {
            if (category.value.equalsIgnoreCase(text)) {
                return category;
            }
        }
        
        try {
            return valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoría de extracción no válida: " + text);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Converter(autoApply = true)
    public static class ExtractionCategoryConverter implements AttributeConverter<ExtractionCategory, String> {
        @Override
        public String convertToDatabaseColumn(ExtractionCategory attribute) {
            return attribute == null ? null : attribute.name();
        }

        @Override
        public ExtractionCategory convertToEntityAttribute(String dbData) {
            return dbData == null ? null : ExtractionCategory.fromString(dbData);
        }
    }
}
