package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.util.TimeZone;

/**
 * A {@link PropertyField} for {@link TimeZone} values.
 */
public class TimeZonePropertyField extends PropertyField {

  /**
   * Creates a new {@link TimeZonePropertyField}.
   */
  public TimeZonePropertyField() {
    super(TimeZone.class);
  }

  /**
   * Parses a {@link String} into a {@link TimeZone}.
   *
   * @param value The {@link String} to parse.
   * @return The parsed {@link TimeZone}.
   */
  @Override
  public @NotNull TimeZone parseString(@NotNull String value) {
    return (TimeZone.getTimeZone(value));
  }
}
