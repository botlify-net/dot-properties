package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link PropertyField} for {@link Integer} values.
 */
public class IntegerPropertyField extends PropertyField {

  /**
   * Creates a new {@link IntegerPropertyField}.
   */
  public IntegerPropertyField() {
    super(Integer.class);
  }

  /**
   * Parses a {@link String} into a {@link Integer}.
   *
   * @param value The {@link String} to parse.
   * @return The parsed {@link Integer}.
   */
  @Override
  public @NotNull Object parseString(@NotNull final String value) {
    return (Integer.parseInt(value));
  }
}
