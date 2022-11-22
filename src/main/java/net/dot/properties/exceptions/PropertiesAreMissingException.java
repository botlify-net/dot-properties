package net.dot.properties.exceptions;

import java.util.List;

public class PropertiesAreMissingException extends Exception {

    private final List<String> properties;

    public PropertiesAreMissingException(List<String> properties) {
        super("Properties are missing: " + properties.toString());
        this.properties = properties;
    }

    public List<String> getProperties() {
        return properties;
    }
}
