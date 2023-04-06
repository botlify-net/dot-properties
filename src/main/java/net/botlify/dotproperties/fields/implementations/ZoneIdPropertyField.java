package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;

/**
 * A {@link PropertyField} for {@link ZoneId} values.
 */
public class ZoneIdPropertyField extends PropertyField {

    /**
     * Creates a new {@link ZoneIdPropertyField}.
     */
    public ZoneIdPropertyField() {
        super(ZoneId.class);
    }

    /**
     * Parses a {@link String} into a {@link ZoneId}.
     * @param value The {@link String} to parse.
     * @return The parsed {@link ZoneId}.
     */
    @Override
    public @NotNull ZoneId parseString(@NotNull String value) {
        return (ZoneId.of(value));
    }

}
