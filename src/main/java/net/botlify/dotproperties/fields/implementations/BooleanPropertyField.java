package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

/**
 * A property field for booleans.
 */
public class BooleanPropertyField extends PropertyField {

    /**
     * Creates a new boolean property field.
     */
    public BooleanPropertyField() {
        super(Boolean.class);
    }

    /**
     * Parses a string into a boolean.
     * @param value The string to parse.
     * @return The parsed boolean.
     */
    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        return (Boolean.parseBoolean(value));
    }

}
