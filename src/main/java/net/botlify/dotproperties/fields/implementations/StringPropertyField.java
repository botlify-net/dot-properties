package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

public class StringPropertyField extends PropertyField {

    public StringPropertyField() {
        super(String.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        return (value);
    }

}
