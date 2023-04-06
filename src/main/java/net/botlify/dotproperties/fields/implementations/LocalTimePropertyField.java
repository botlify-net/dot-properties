package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

public class LocalTimePropertyField extends PropertyField {

    public LocalTimePropertyField() {
        super(LocalTime.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull String value) {
        return (LocalTime.parse(value));
    }
}
