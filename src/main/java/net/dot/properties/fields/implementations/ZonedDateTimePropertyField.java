package net.dot.properties.fields.implementations;

import net.dot.properties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

public class ZonedDateTimePropertyField extends PropertyField {

    public ZonedDateTimePropertyField() {
        super(ZonedDateTime.class);
    }

    public @NotNull Object parseString(@NotNull final String value) {
        return (ZonedDateTime.parse(value));
    }

}
