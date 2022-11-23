package net.dot.properties.fields;

import net.dot.properties.enums.Property;
import net.dot.properties.fields.implementations.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static net.dot.properties.DotProperties.logger;

public class PropertyFieldManager {

    private PropertyFieldManager() {

    }

    private static final List<PropertyField> fields = new ArrayList<>();

    private static final ReentrantLock lock = new ReentrantLock();

    static {
        fields.add(new BooleanPropertyField());
        fields.add(new BytePropertyField());
        fields.add(new CharacterPropertyField());
        fields.add(new DatePropertyField());
        fields.add(new DurationPropertyField());
        fields.add(new FloatPropertyField());
        fields.add(new InstantPropertyField());
        fields.add(new IntegerPropertyField());
        fields.add(new LocalTimePropertyField());
        fields.add(new StringPropertyField());
        fields.add(new ZonedDateTimePropertyField());
    }

    public static void addPropertyField(@NotNull PropertyField field) {
        lock.lock();
        fields.add(field);
        lock.unlock();
    }

    /**
     * Update the value of the field given in parameter with the value given in parameter.
     * This method will use the {@link PropertyField} of the field to parse the value.
     * @param field The field to update.
     * @param value The value to update the field with.
     * @return True if the field was updated, false otherwise.
     */
    public static boolean parseField(@NotNull Object bean,
                                     @NotNull Field field,
                                     @NotNull String value) throws IllegalAccessException {
        lock.lock();
        // Verify if the field is final.
        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
            logger.warn("Field {} is final, skipping updating", field.getName());
            return (false);
        }
        // Verify if the field is private.
        if ((field.getModifiers() & Modifier.PRIVATE) == Modifier.PRIVATE) {
            logger.warn("Field {} is private, skipping updating", field.getName());
            return (false);
        }
        // Verify if the field is primitive.
        if (parsePrimitiveField(bean, field, value))
            return (true);
        for (PropertyField propertyField : fields) {
            if (propertyField.getType() == field.getType()) {
                try {
                    field.setAccessible(true);
                    field.set(bean, propertyField.getValue(value));
                    return (true);
                } catch (IllegalAccessException e) {
                    logger.error("Unable to set field {} to value {}",
                            field.getName(), value);
                    return (false);
                }
            }
        }
        lock.unlock();
        logger.warn("Unsupported field type {} for field {}",
                field.getType().getName(), field.getName());
        return (false);
    }

    private static boolean parsePrimitiveField(@NotNull Object bean,
                                               @NotNull Field field,
                                               @NotNull String value) throws IllegalAccessException {
        if (!field.getType().isPrimitive() && !field.getType().isEnum())
            return (false);
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
        return (true);
    }

    /**
     * Return a list of all the fields that are supported by the manager.
     * @return A list of all the fields that are supported by the manager.
     */
    public @NotNull List<Class<?>> getImplementedClassList() {
        List<Class<?>> classes = new ArrayList<>();
        lock.lock();
        for (PropertyField field : fields)
            classes.add(field.getType());
        lock.unlock();
        return (classes);
    }

}
