package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class DurationPropertyField extends PropertyField {

    public DurationPropertyField() {
        super(Duration.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        return (Duration.parse(value));
    }

}
