package net.botlify.dotproperties;

import lombok.extern.log4j.Log4j2;
import net.botlify.dotproperties.annotations.Property;
import net.botlify.dotproperties.exceptions.PropertiesAreMissingException;
import net.botlify.dotproperties.exceptions.PropertiesBadFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * The main class of the library.
 * This class is used to load properties from a file and to inject them into a bean.
 */
@Log4j2
public final class DotProperties {

  /**
   * The config object to use for this DotProperties instance.
   */
  @NotNull
  private final DotPropertiesConfig config;

  /**
   * Load a new dot properties file with the default config.
   */
  DotProperties() {
    this(new DotPropertiesConfig());
  }

  /**
   * Constructor of the DotProperties.
   *
   * @param config The config object to use for this DotProperties instance.
   */
  DotProperties(@NotNull final DotPropertiesConfig config) {
    log.trace("Initializing new DotProperties");
    this.config = config;
  }

  /**
   * Load the properties from the file and inject them into the bean.
   * This method should be called only once, otherwise an exception will be thrown.
   *
   * @param been The bean to inject the properties into.
   * @return The instance of the class {@link DotProperties}.
   */
  public synchronized DotProperties load(@NotNull final Object been) {
    // Read the properties from the file.
    final Properties properties = FileUtils.getProperties(been, config);
    // Set the properties in the system.
    setAllPropertiesInSystem(properties);
    // Get the properties elements.
    final Map<Field, Property> propertiesElements = getPropertiesElements(been);
    setAllInBeen(been, properties, propertiesElements);
    // Verify the regex of the properties.
    verifyPropertiesRegex(propertiesElements);
    return (this);
  }

    /*
     $      Private methods
     */

  /**
   * Set all the properties in the system.
   *
   * @param properties The properties to set.
   */
  private void setAllPropertiesInSystem(@NotNull final Properties properties) {
    properties.forEach((k, v) -> {
      log.trace("Setting property {} to {}", k, v);
      System.setProperty((String) k, (String) v);
    });
  }

  /**
   * Set all the properties in the bean.
   *
   * @param been               The bean to set the properties in.
   * @param properties         The properties to set.
   * @param propertiesElements The properties elements to use.
   */
  private void setAllInBeen(@NotNull final Object been,
                            @NotNull final Properties properties,
                            @NotNull Map<@NotNull Field, Property> propertiesElements) {
    // This list stores all the missing properties.
    final List<String> missingProperties = new ArrayList<>();

    final PropertyFieldManager propertyFieldManager = new PropertyFieldManager();
    propertyFieldManager.addPropertyField(config.getPropertyFields());

    propertiesElements.forEach((field, propElem) -> {
      final String value = properties.getProperty(propElem.name());
      // Check if the value is required.
      if (value == null && propElem.required()) {
        missingProperties.add(propElem.name());
        return;
      }
      if (value == null)
        return;
      // Update field in been
      try {
        propertyFieldManager.parseField(been, field, value);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
    if (!missingProperties.isEmpty())
      throw (new PropertiesAreMissingException(missingProperties));
  }

  /**
   * This method will verify the regex of the properties.
   * If a property is in bad format, an exception will be thrown.
   *
   * @param fieldPropertiesMap The map of the properties elements.
   */
  private void verifyPropertiesRegex(@NotNull final Map<Field, Property> fieldPropertiesMap) {
    final List<String> inBadFormat = new ArrayList<>();
    fieldPropertiesMap.forEach((field, property) -> {
      if (property.regex().length == 0)
        return;
      final String value = System.getProperty(property.name());
      if (value == null)
        return;
      // Verify all the regex.
      for (String regex : property.regex()) {
        if (!value.matches(regex)) {
          inBadFormat.add(property.name());
          break;
        }
      }
    });
    if (!inBadFormat.isEmpty())
      throw (new PropertiesBadFormat(inBadFormat));
  }

  /**
   * Return the map of all fields annotated with {@link Property}.
   *
   * @return The map of all fields annotated with {@link Property}.
   */
  private @NotNull Map<@NotNull Field, Property> getPropertiesElements(
      @NotNull final Object been
  ) {
    final Map<Field, Property> propertiesElements = new HashMap<>();
    for (Field field : been.getClass().getDeclaredFields()) {
      for (Annotation annotation : field.getAnnotations()) {
        if (!(annotation instanceof Property))
          continue;
        propertiesElements.put(field, (Property) annotation);
      }
    }
    return (propertiesElements);
  }

  // Bindings

  /**
   * Return the value of the property.
   *
   * @param property The property to get.
   * @return The value of the property.
   */
  public @Nullable String getProperty(@NotNull final String property) {
    return (System.getProperty(property));
  }

  /**
   * Return the value of the property or the default value if the property is not set.
   *
   * @param property     The property to get.
   * @param defaultValue The default value to return if the property is not set.
   * @return The value of the property or the default value if the property is not set.
   */
  public @Nullable String getProperty(
      @NotNull final String property,
      @Nullable final String defaultValue
  ) {
    return (System.getProperty(property, defaultValue));
  }

  /**
   * Set the value of the property.
   *
   * @param property The property to set.
   * @param value    The value to set.
   * @return {@code true} if the property was changed, {@code false} otherwise.
   */
  public boolean setProperty(
      @NotNull final String property,
      @NotNull final String value
  ) {
    String oldValue = System.getProperty(property);
    if (oldValue == null || oldValue.equals(value))
      return (false);
    System.setProperty(property, value);
    return (true);
  }

}
