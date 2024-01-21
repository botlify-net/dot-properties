package net.botlify.dotproperties.fields;

import lombok.extern.log4j.Log4j2;
import net.botlify.dotproperties.fields.implementations.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A manager for {@link PropertyField} instances.
 */
@Log4j2
public abstract class PropertyFieldManager {

  /**
   * The list of fields that can be parsed.
   */
  @NotNull
  private static final List<PropertyField> fields = new ArrayList<>();

  /**
   * The lock used to synchronize the access to the field list.
   */
  private static final ReentrantLock lock = new ReentrantLock();

  static {
    fields.add(new BooleanPropertyField());
    fields.add(new BytePropertyField());
    fields.add(new CharacterPropertyField());
    fields.add(new DatePropertyField());
    fields.add(new DurationPropertyField());
    fields.add(new FilePropertyField());
    fields.add(new FloatPropertyField());
    fields.add(new InstantPropertyField());
    fields.add(new IntegerPropertyField());
    fields.add(new LocalTimePropertyField());
    fields.add(new LongPropertyField());
    fields.add(new StringPropertyField());
    fields.add(new TimeZonePropertyField());
    fields.add(new ZonedDateTimePropertyField());
    fields.add(new ZoneIdPropertyField());
  }

  /**
   * Creates a new {@link PropertyFieldManager}.
   * This constructor is private because this class not need to be instantiated.
   */
  private PropertyFieldManager() {
    // Nothing to do.
  }

  /**
   * Add a new {@link PropertyField} to the list of fields.
   *
   * @param field The field to add.
   */
  public static void addPropertyField(@NotNull final PropertyField field) {
    lock.lock();
    try {
      fields.add(field);
    } finally {
      lock.unlock();
    }
  }

  /**
   * Update the value of the field given in parameter with the value given in parameter.
   * This method will use the {@link PropertyField} of the field to parse the value.
   *
   * @param bean  The bean to update.
   * @param field The field to update.
   * @param value The value to update the field with.
   * @return True if the field was updated, false otherwise.
   * @throws IllegalAccessException If the field is final.
   */
  public static boolean parseField(@NotNull final Object bean,
                                   @NotNull final Field field,
                                   @NotNull final String value) throws IllegalAccessException {
    lock.lock();
    try {
      // Verify if the field is final.
      if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
        log.warn("Field {} is final, skipping updating", field.getName());
        return (false);
      }
      // Verify if the field is primitive.
      if (parsePrimitiveField(bean, field, value))
        return (true);
      for (PropertyField propertyField : fields) {
        if (propertyField.getType() == field.getType()) {
          try {
            field.setAccessible(true);
            field.set(bean, propertyField.parseString(value));
            return (true);
          } catch (IllegalAccessException e) {
            log.error("Unable to set field {} to value {}", field.getName(), value, e);
            return (false);
          }
        }
      }
    } finally {
      lock.unlock();
    }
    log.warn("Unsupported field type {} for field {}",
        field.getType().getName(), field.getName());
    return (false);
  }

  /**
   * Parse a primitive field.
   *
   * @param bean  The bean to update.
   * @param field The field to update.
   * @param value The value to update the field with.
   * @return True if the field was updated, false otherwise.
   * @throws IllegalAccessException If the field is not accessible.
   */
  private static boolean parsePrimitiveField(@NotNull final Object bean,
                                             @NotNull final Field field,
                                             @NotNull final String value) throws IllegalAccessException {
    if (!field.getType().isPrimitive() && !field.getType().isEnum())
      return (false);
    field.setAccessible(true);
    if (int.class.equals(field.getType())) {
      field.setInt(bean, Integer.parseInt(value));
    } else if (float.class.equals(field.getType())) {
      field.setFloat(bean, Float.parseFloat(value));
    } else if (boolean.class.equals(field.getType())) {
      field.setBoolean(bean, Boolean.parseBoolean(value));
    } else if (long.class.equals(field.getType())) {
      field.setLong(bean, Long.parseLong(value));
    } else if (double.class.equals(field.getType())) {
      field.setDouble(bean, Double.parseDouble(value));
    } else if (short.class.equals(field.getType())) {
      field.setShort(bean, Short.parseShort(value));
    } else if (byte.class.equals(field.getType())) {
      field.setByte(bean, Byte.parseByte(value));
    } else if (char.class.equals(field.getType())) {
      field.setChar(bean, value.charAt(0));
    } else if (field.getType().isEnum()) {
      field.set(bean, Enum.valueOf((Class<Enum>) field.getType(), value));
    }
    field.setAccessible(false);
    return (true);
  }

  /**
   * Return a list of all the fields that are supported by the manager.
   *
   * @return A list of all the fields that are supported by the manager.
   */
  public static @NotNull List<Class<?>> getImplementedClassList() {
    List<Class<?>> classes = new ArrayList<>();
    lock.lock();
    try {
      for (PropertyField field : fields) {
        classes.add(field.getType());
      }
    } finally {
      lock.unlock();
    }
    return (classes);
  }

}
