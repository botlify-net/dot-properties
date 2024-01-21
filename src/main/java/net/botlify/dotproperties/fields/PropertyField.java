package net.botlify.dotproperties.fields;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link PropertyField} is a field that can be parsed from a {@link String}.
 */
public abstract class PropertyField {

  /**
   * The type of the field.
   */
  @Getter
  private final Class<?> type;

  /**
   * Creates a new {@link PropertyField}.
   *
   * @param type The type of the field.
   */
  public PropertyField(@NotNull final Class<?> type) {
    this.type = type;
  }

  /**
   * Parses a {@link String} into a value.
   *
   * @param value The {@link String} to parse.
   * @return The parsed {@link Object}.
   */
  public abstract @NotNull Object parseString(@NotNull final String value);

}
