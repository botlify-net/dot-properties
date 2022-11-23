package net.dot.properties.fields.implementations;

import net.dot.properties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

public class BooleanPropertyField extends PropertyField {

    public BooleanPropertyField() {
        super(Boolean.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        return (Boolean.parseBoolean(value));
    }

}
