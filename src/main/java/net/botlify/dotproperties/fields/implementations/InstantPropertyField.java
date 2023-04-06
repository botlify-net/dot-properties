package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class InstantPropertyField extends PropertyField {

    public InstantPropertyField() {
        super(Instant.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        return (Instant.parse(value));
    }

}
