package net.botlify.dotproperties;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builder for {@link DotProperties}.
 */
@ToString
@EqualsAndHashCode
public final class DotPropertiesConfig {

  /**
   * The java environment to use for the file name
   * of the properties file.
   */
  @Getter(AccessLevel.PACKAGE)
  private String javaEnv = null;

  /**
   * The file name of the properties file.
   */
  @Nullable
  @Getter(AccessLevel.PACKAGE)
  private String fileName = ".properties";

  /**
   * Set if the resource folder should be used to
   * find the properties file.
   */
  @Getter(AccessLevel.PACKAGE)
  private boolean useResourceFolder = true;

  /**
   * Set if the file system folder should be used to
   * find the properties file.
   */
  @Getter(AccessLevel.PACKAGE)
  private boolean useFileSystemFolder = true;

  /**
   * Constructor of the configuration.
   */
  public DotPropertiesConfig() {
    // Nothing to do
  }

  /**
   * Set the java environment to use for the file name.
   *
   * @param javaEnv The java environment to use.
   * @return The configuration.
   */
  public @NotNull DotPropertiesConfig setJavaEnvName(@NotNull final String javaEnv) {
    this.javaEnv = javaEnv;
    return (this);
  }

  /**
   * This method will call the method {@link #setJavaEnvName(String)} with
   * the value "JAVA_PROPS".
   *
   * @return The configuration.
   */
  public @NotNull DotPropertiesConfig enableJavaEnv() {
    this.javaEnv = "JAVA_PROPS";
    return (this);
  }

  /**
   * Set the path of the properties file. The file can be in the ressource
   * folder or in the file system.
   *
   * @param path The path of the properties file.
   * @return The configuration.
   */
  public @NotNull DotPropertiesConfig setPath(@Nullable final String path) {
    if (path == null) {
      this.fileName = null;
      return (this);
    }
    this.fileName = path;
    return (this);
  }

  /**
   * Set if the resource folder should be used to find the properties file.
   *
   * @param useResourceFolder Set if the resource folder should be used.
   * @return The configuration.
   */
  public @NotNull DotPropertiesConfig setUseResourceFolder(final boolean useResourceFolder) {
    this.useResourceFolder = useResourceFolder;
    return (this);
  }

  /**
   * Set if the file system folder should be used to find the properties file.
   *
   * @param useFileSystemFolder Set if the file system folder should be used.
   * @return The configuration.
   */
  public @NotNull DotPropertiesConfig setUseFileSystemFolder(final boolean useFileSystemFolder) {
    this.useFileSystemFolder = useFileSystemFolder;
    return (this);
  }

}
