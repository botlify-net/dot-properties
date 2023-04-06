package net.botlify.dotproperties.exceptions;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Thrown when a property is missing.
 */
public class PropertiesAreMissingException extends RuntimeException {

    /**
     * The properties that are missing.
     */
    @NotNull @Getter
    private final List<String> properties;

    /**
     * Constructor of the exception with the properties that are missing.
     * @param properties The properties that are missing.
     */
    public PropertiesAreMissingException(@NotNull final List<String> properties) {
        super("Properties are missing: " + properties);
        this.properties = properties;
    }

}
