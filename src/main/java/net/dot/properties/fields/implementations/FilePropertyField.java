package net.dot.properties.fields.implementations;

import net.dot.properties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FilePropertyField extends PropertyField {

    public FilePropertyField() {
        super(File.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull String value) {
        return new File(value);
    }
}
