package net.botlify.dotproperties.fields;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public abstract class PropertyField {

    @Getter
    private final Class<?> type;

    public PropertyField(@NotNull final Class<?> type) {
        this.type = type;
    }

    public @NotNull Object getValue(@NotNull final String value) {
        Object result = parseString(value);
        // if (result.getClass() != type)
        //     throw new IllegalArgumentException("The value is " + result.getClass().getName() + " but should be " + type.getName());
        return (result);
    }

    public abstract @NotNull Object parseString(@NotNull final String value);

}
