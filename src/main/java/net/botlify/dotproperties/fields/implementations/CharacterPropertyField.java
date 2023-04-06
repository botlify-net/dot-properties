package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

public class CharacterPropertyField extends PropertyField {

    public CharacterPropertyField() {
        super(Character.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        if (value.length() != 1)
            throw (new IllegalArgumentException("CharacterPropertyField can only parse strings with a length of 1"));
        return (value.charAt(0));
    }

}
