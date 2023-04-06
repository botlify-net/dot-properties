package net.botlify.dotproperties.exceptions;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Thrown when a property is in bad format.
 */
public class PropertiesBadFormat extends RuntimeException {

    /**
     * The properties that are in bad format.
     */
    @NotNull @Getter
    private final List<String> propertiesInBadFormat;

    /**
     * Constructor of the exception with the properties that are in bad format.
     * @param propertiesFormat The properties that are in bad format.
     */
    public PropertiesBadFormat(@NotNull final List<String> propertiesFormat) {
        super("Properties are in bad format: " + propertiesFormat);
        this.propertiesInBadFormat = propertiesFormat;
    }

}
