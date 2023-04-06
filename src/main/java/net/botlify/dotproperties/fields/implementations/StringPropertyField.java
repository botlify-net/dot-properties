package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link PropertyField} for {@link String}.
 */
public class StringPropertyField extends PropertyField {

    /**
     * Creates a new {@link StringPropertyField}.
     */
    public StringPropertyField() {
        super(String.class);
    }

    /**
     * Parses a {@link String} into a {@link String}.
     * @param value The {@link String} to parse.
     * @return The parsed {@link String}.
     */
    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        return (value);
    }

}
