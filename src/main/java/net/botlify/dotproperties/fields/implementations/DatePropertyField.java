package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A property field for {@link Date} objects.
 */
public class DatePropertyField extends PropertyField<Date> {

  /**
   * Creates a new date property field.
   */
  public DatePropertyField() {
    super(Date.class);
  }

  /**
   * Parses a string into a date.
   *
   * @param value The string to parse.
   * @return The parsed date.
   */
  @Override
  public @NotNull Date parseFromString(@NotNull final String value) {
    final SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    try {
      return (format.parse(value));
    } catch (ParseException e) {
      throw (new RuntimeException(e));
    }
  }

}
