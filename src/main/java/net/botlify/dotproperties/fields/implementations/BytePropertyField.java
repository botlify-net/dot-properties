package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

/**
 * A property field that can parse a {@link Byte} value.
 */
public class BytePropertyField extends PropertyField {

  /**
   * Creates a new instance.
   */
  public BytePropertyField() {
    super(Byte.class);
  }

  /**
   * Parses a string into a byte.
   *
   * @param value The string to parse.
   * @return The parsed byte.
   */
  @Override
  public @NotNull Object parseString(@NotNull String value) {
    return (Byte.parseByte(value));
  }
}
