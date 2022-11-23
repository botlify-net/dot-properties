package net.dot.properties.fields.implementations;

import net.dot.properties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

public class BytePropertyField extends PropertyField {

    public BytePropertyField() {
        super(Byte.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull String value) {
        return (Byte.parseByte(value));
    }
}
