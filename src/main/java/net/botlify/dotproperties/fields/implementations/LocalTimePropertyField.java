package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

/**
 * A {@link PropertyField} for {@link LocalTime}.
 */
public class LocalTimePropertyField extends PropertyField {

    /**
     * Creates a new {@link LocalTimePropertyField}.
     */
    public LocalTimePropertyField() {
        super(LocalTime.class);
    }

    /**
     * Parses a {@link String} into a {@link LocalTime}.
     * @param value The {@link String} to parse.
     * @return The parsed {@link LocalTime}.
     */
    @Override
    public @NotNull Object parseString(@NotNull String value) {
        return (LocalTime.parse(value));
    }
}
