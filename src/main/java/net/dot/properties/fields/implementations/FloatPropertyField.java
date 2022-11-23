package net.dot.properties.fields.implementations;

import net.dot.properties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

public class FloatPropertyField extends PropertyField {

    public FloatPropertyField() {
        super(Float.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        return (Float.parseFloat(value));
    }
}
