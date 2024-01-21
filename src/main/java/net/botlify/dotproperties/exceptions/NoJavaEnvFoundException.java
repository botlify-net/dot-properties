package net.botlify.dotproperties.exceptions;

import lombok.Getter;
import net.botlify.dotproperties.DotPropertiesConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when no Java environment is found but
 * a configuration requires it.
 */
@Getter
public class NoJavaEnvFoundException extends RuntimeException {


  /**
   * The configuration that is invalid.
   */
  @NotNull
  private final DotPropertiesConfig config;

  /**
   * Constructor of the exception with the java environment variable name.
   *
   * @param javaEnv The java environment variable name.
   * @param config  The configuration that is invalid.
   */
  public NoJavaEnvFoundException(@NotNull final String javaEnv,
                                 @NotNull final DotPropertiesConfig config) {
    super("No Java environment found, you need to configure " + javaEnv + " environment variable");
    this.config = config;
  }

}
