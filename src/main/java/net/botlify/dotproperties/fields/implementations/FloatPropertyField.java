package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link PropertyField} for {@link Character} values.
 */
public class FloatPropertyField extends PropertyField {

  /**
   * Creates a new {@link FloatPropertyField}.
   */
  public FloatPropertyField() {
    super(Float.class);
  }

  /**
   * Parses a {@link String} into a {@link Float}.
   *
   * @param value The {@link String} to parse.
   * @return The parsed {@link Float}.
   */
  @Override
  public @NotNull Object parseString(@NotNull final String value) {
    return (Float.parseFloat(value));
  }
}
