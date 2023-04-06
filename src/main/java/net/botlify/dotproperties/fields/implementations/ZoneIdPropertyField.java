package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;

public class ZoneIdPropertyField extends PropertyField {

    public ZoneIdPropertyField() {
        super(ZoneId.class);
    }

    @Override
    public @NotNull ZoneId parseString(@NotNull String value) {
        return (ZoneId.of(value));
    }
}
