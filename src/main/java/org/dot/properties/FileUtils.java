package org.dot.properties;

import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Properties;

class FileUtils {

    public static Properties readProperties(@NotNull String javaEnv) throws NoJavaEnvFoundException, IOException {
        String javaProps = System.getenv(javaEnv);
        if (javaProps == null) throw (new NoJavaEnvFoundException(javaEnv));
        String name1 = javaProps + ".properties", name2 =  ".properties." + javaProps;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    String path = i == 0 ? name1 : name2;
                    return (readProperties(path, j == 0));
                } catch (IOException e) {
                    // Nothing to do
                }
            }
        }
        throw new IOException(name1 + " or " + name2 + " are missing in root directory or resource directory or is in bad format.");
    }

    public static Properties readProperties(@NotNull String path, boolean resource) throws IOException {
        Properties result = (resource) ? readPropertiesFromResource(path) : readPropertiesFromPath(path);
        if (result == null)
            throw new IOException(path + " is missing in " + ((resource) ? "resource" : "root") + " directory or is in bad format.");
        return (result);
    }

    public static @Nullable Properties readPropertiesFromPath(@NotNull String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (Exception e) {
            return (null);
        }
        return (properties);
    }

    public static @Nullable Properties readPropertiesFromResource(@NotNull String path) {
        try (InputStream inputStream = FileUtils.class.getResourceAsStream(path)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return (properties);
        } catch (Exception e) {
            return (null);
        }
    }

    public static boolean changePropertyInFile(@NotNull final String filename,
                                               @NotNull final String key,
                                               @NotNull final String value) throws IOException {
        final File tmpFile = new File(filename + ".tmp");
        final File file = new File(filename);
        PrintWriter pw = new PrintWriter(tmpFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        boolean found = false;
        final String toAdd = key + '=' + value;
        for (String line; (line = br.readLine()) != null; ) {
            if (line.startsWith(key + '=')) {
                line = toAdd;
                found = true;
            }
            pw.println(line);
        }
        if (!found)
            pw.println(toAdd);
        br.close();
        pw.close();
        return (tmpFile.renameTo(file));
    }

}
