package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

/**
 * A {@link PropertyField} for {@link ZonedDateTime}.
 */
public class ZonedDateTimePropertyField extends PropertyField {

  /**
   * Creates a new {@link ZonedDateTimePropertyField}.
   */
  public ZonedDateTimePropertyField() {
    super(ZonedDateTime.class);
  }

  /**
   * Parses a {@link String} into a {@link ZonedDateTime}.
   *
   * @param value The {@link String} to parse.
   * @return The parsed {@link ZonedDateTime}.
   */
  public @NotNull Object parseString(@NotNull final String value) {
    return (ZonedDateTime.parse(value));
  }

}
