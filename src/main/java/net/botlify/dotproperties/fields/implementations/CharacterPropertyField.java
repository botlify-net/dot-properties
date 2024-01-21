package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link PropertyField} for {@link Character} values.
 */
public class CharacterPropertyField extends PropertyField {

  /**
   * Creates a new {@link CharacterPropertyField}.
   */
  public CharacterPropertyField() {
    super(Character.class);
  }

  /**
   * Parses a {@link Character} from a {@link String}.
   *
   * @param value The string to parse.
   * @return The parsed {@link Character}.
   */
  @Override
  public @NotNull Object parseString(@NotNull final String value) {
    if (value.length() != 1)
      throw (new IllegalArgumentException("CharacterPropertyField can only parse strings with a length of 1"));
    return (value.charAt(0));
  }

}
