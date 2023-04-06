package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

public class IntegerPropertyField extends PropertyField {

    public IntegerPropertyField() {
        super(Integer.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        return (Integer.parseInt(value));
    }
}
