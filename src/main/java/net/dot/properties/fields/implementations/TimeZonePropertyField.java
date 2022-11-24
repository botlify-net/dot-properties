package net.dot.properties.fields.implementations;

import net.dot.properties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.util.TimeZone;

public class TimeZonePropertyField extends PropertyField {

    public TimeZonePropertyField() {
        super(TimeZone.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull String value) {
        return (TimeZone.getTimeZone(value));
    }
}
