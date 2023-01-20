package net.dot.properties.fields.implementations;

import net.dot.properties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

public class LongPropertyField extends PropertyField {


    public LongPropertyField() {
        super(Long.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull String value) {
        return (Long.parseLong(value));
    }
}
