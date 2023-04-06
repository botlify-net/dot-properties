package net.botlify.dotproperties;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.botlify.dotproperties.exceptions.InvalidConfigException;
import net.botlify.dotproperties.exceptions.NoJavaEnvFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Utility class for file operations.
 */
@Log4j2
abstract class FileUtils {

    /**
     * Get the properties from the configuration.
     * @param been The been to get the properties for.
     * @param config The configuration.
     * @return The properties.
     */
    @SneakyThrows
    public static @NotNull Properties getProperties(@NotNull final Object been,
                                                    @NotNull final DotPropertiesConfig config) {
        if (!config.isUseFileSystemFolder() && !config.isUseResourceFolder())
            throw (new InvalidConfigException("Both use file system folder and use resource folder are not set.",
                    config));
        final List<String> fileNames = getFileName(config);
        // Search in file system.
        if (config.isUseFileSystemFolder()) {
            for (String name : fileNames) {
                log.trace("Searching properties file in file system: {}", name);
                Properties tmp = readPropertiesFromFile(name);
                if (tmp != null)
                    return (tmp);
            }
        }
        // Search in resource folder.
        if (config.isUseResourceFolder()) {
            for (String name : fileNames) {
                log.trace("Searching properties file in resource folder: {}", name);
                Properties tmp = readPropertiesFromResource(been, name);
                if (tmp != null)
                    return (tmp);
            }
        }
        throw (new IOException("No properties file found: " + fileNames));
    }

    /**
     * Return the {@link List} of file names to search for the properties file.
     * @param config The configuration.
     * @return The {@link List} of file names.
     */
    private static @NotNull List<String> getFileName(@NotNull final DotPropertiesConfig config) {
        if (config.getFileName() != null && config.getJavaEnv() != null)
            throw new InvalidConfigException("Both file name and java environment are set.", config);
        if (config.getFileName() == null && config.getJavaEnv() == null)
            throw new InvalidConfigException("Both file name and java environment are not set.", config);
        if (config.getFileName() != null)
            return List.of(config.getFileName());
        final String javaEnv = System.getenv(config.getJavaEnv());
        if (javaEnv == null)
            throw new NoJavaEnvFoundException(config.getJavaEnv(), config);
        return List.of(javaEnv + ".properties", ".properties." + javaEnv);
    }

    /**
     * Read the properties from a file in the file system.
     * @param path The path to the file.
     * @return The properties.
     */
    private static @Nullable Properties readPropertiesFromFile(@NotNull final String path) {
        try (FileInputStream resource = new FileInputStream(path)) {
            final Properties properties = new Properties();
            properties.load(resource);
            return (properties);
        } catch (Exception e) {
            return (null);
        }
    }

    /**
     * Read the properties from a file in the resource folder.
     * @param been The been to get the properties for.
     * @param path The path to the file.
     * @return The properties.
     */
    private static @Nullable Properties readPropertiesFromResource(@NotNull final Object been,
                                                                   @NotNull final String path) {
        try (InputStream inputStream = been.getClass().getClassLoader().getResourceAsStream(path)) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            return (properties);
        } catch (Exception e) {
            return (null);
        }
    }

}
