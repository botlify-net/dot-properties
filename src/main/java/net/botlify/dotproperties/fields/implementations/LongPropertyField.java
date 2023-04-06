package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
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
