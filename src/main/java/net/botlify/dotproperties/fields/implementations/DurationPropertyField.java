package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

/**
 * A {@link PropertyField} for {@link Duration} values.
 */
public class DurationPropertyField extends PropertyField<Duration> {

  /**
   * Creates a new {@link DurationPropertyField}.
   */
  public DurationPropertyField() {
    super(Duration.class);
  }

  /**
   * Parses a {@link String} into a {@link Duration}.
   *
   * @param value The {@link String} to parse.
   * @return The parsed {@link Duration}.
   */
  @Override
  public @NotNull Duration parseFromString(@NotNull final String value) {
    return (Duration.parse(value));
  }

}
