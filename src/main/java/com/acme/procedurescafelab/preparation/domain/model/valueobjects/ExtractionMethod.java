package com.acme.procedurescafelab.preparation.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum ExtractionMethod {
    ESPRESSO("espresso"),
    POUR_OVER("pour-over"),
    FRENCH_PRESS("french-press"),
    COLD_BREW("cold-brew"),
    AEROPRESS("aeropress"),
    CHEMEX("chemex"),
    V60("v60"),
    CLEVER("clever");

    private final String value;

    ExtractionMethod(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ExtractionMethod fromString(String text) {
        if (text == null) {
            return null;
        }
        
        for (ExtractionMethod method : ExtractionMethod.values()) {
            if (method.value.equalsIgnoreCase(text)) {
                return method;
            }
        }
        
        try {
            return valueOf(text);
        } catch (IllegalArgumentException e) {
            String normalized = text.replace("_", "-").toLowerCase();
            for (ExtractionMethod method : ExtractionMethod.values()) {
                if (method.value.equals(normalized)) {
                    return method;
                }
            }
        }
        
        throw new IllegalArgumentException("Método de extracción no válido: " + text);
    }

    @Override
    public String toString() {
        return value;
    }

    @Converter(autoApply = true)
    public static class ExtractionMethodConverter implements AttributeConverter<ExtractionMethod, String> {
        @Override
        public String convertToDatabaseColumn(ExtractionMethod attribute) {
            return attribute == null ? null : attribute.name();
        }

        @Override
        public ExtractionMethod convertToEntityAttribute(String dbData) {
            return dbData == null ? null : ExtractionMethod.fromString(dbData);
        }
    }
}
