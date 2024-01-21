package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * A {@link PropertyField} for {@link File} values.
 */
public class FilePropertyField extends PropertyField {

  /**
   * Creates a new {@link FilePropertyField}.
   */
  public FilePropertyField() {
    super(File.class);
  }

  /**
   * Parses a {@link String} into a {@link File}.
   *
   * @param value The {@link String} to parse.
   * @return The parsed {@link File}.
   */
  @Override
  public @NotNull Object parseString(@NotNull String value) {
    return new File(value);
  }
}
