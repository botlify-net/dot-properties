package net.botlify.dotproperties.annotations;

import net.botlify.dotproperties.DotProperties;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used before variable
 * to be updated by {@link DotProperties}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Property {

  /**
   * The name of the property to found in the file.
   *
   * @return The name of the property.
   */
  @NotNull String name();

  /**
   * The regex to use to check the format of the property.
   *
   * @return The regex.
   */
  @NotNull String[] regex() default {};

  /**
   * Set if the property is required of not.
   *
   * @return A boolean to known if the property is required.
   */
  boolean required() default false;

}
