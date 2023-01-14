package net.dot.properties.exceptions;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PropertiesAreMissingException extends Exception {

    @NotNull @Getter
    private final List<String> properties;

    public PropertiesAreMissingException(@NotNull final List<String> properties) {
        super("Properties are missing: " + properties);
        this.properties = properties;
    }

}
