package net.botlify.dotproperties.exceptions;

import lombok.Getter;
import net.botlify.dotproperties.DotPropertiesConfig;
import org.jetbrains.annotations.NotNull;

/**
 * This exception is thrown when the configuration is invalid.
 */
public class InvalidConfigException extends RuntimeException {

  /**
   * The configuration that is invalid.
   */
  @NotNull
  @Getter
  private final DotPropertiesConfig config;

  /**
   * Constructor of the exception with the message and the configuration.
   *
   * @param message The message.
   * @param config  The configuration that is invalid.
   */
  public InvalidConfigException(@NotNull final String message,
                                @NotNull final DotPropertiesConfig config) {
    super(message);
    this.config = config;
  }

}
