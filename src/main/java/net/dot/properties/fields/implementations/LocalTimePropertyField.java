package net.dot.properties.fields.implementations;

import net.dot.properties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class LocalTimePropertyField extends PropertyField {

    public LocalTimePropertyField() {
        super(ZonedDateTime.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull String value) {
        return (LocalTime.parse(value));
    }
}
