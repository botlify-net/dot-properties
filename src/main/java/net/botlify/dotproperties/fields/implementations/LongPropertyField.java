package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link PropertyField} for {@link Character} values.
 */
public class LongPropertyField extends PropertyField {

    /**
     * Creates a new {@link LongPropertyField}.
     */
    public LongPropertyField() {
        super(Long.class);
    }

    /**
     * Parses a {@link String} into a {@link Long}.
     * @param value The {@link String} to parse.
     * @return The parsed {@link Long}.
     */
    @Override
    public @NotNull Object parseString(@NotNull String value) {
        return (Long.parseLong(value));
    }
}
