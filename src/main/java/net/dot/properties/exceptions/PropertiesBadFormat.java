package net.dot.properties.exceptions;

import lombok.Getter;
import net.dot.properties.PropertiesFormat;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PropertiesBadFormat extends Exception {

    @NotNull @Getter
    private final List<PropertiesFormat> propertiesFormat;

    public PropertiesBadFormat(@NotNull List<PropertiesFormat> propertiesFormat) {
        super("Properties are in bad format: " + String.join(", ", propertiesFormat.stream()
                .map(PropertiesFormat::getName)
                .toList()));
        this.propertiesFormat = propertiesFormat;
    }

}
