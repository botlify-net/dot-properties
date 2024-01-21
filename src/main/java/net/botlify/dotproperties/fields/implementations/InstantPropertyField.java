package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * A property field for {@link Instant} values.
 */
public class InstantPropertyField extends PropertyField<Instant> {

  /**
   * Creates a new {@link InstantPropertyField}.
   */
  public InstantPropertyField() {
    super(Instant.class);
  }

  /**
   * Parses a {@link String} into an {@link Instant}.
   *
   * @param value The {@link String} to parse.
   * @return The parsed {@link Instant}.
   */
  @Override
  public @NotNull Instant parseFromString(@NotNull final String value) {
    return (Instant.parse(value));
  }

}
