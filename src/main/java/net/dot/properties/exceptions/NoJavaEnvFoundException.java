package net.dot.properties.exceptions;

import org.jetbrains.annotations.NotNull;

public class NoJavaEnvFoundException extends Exception {

    public NoJavaEnvFoundException(@NotNull final String javaEnv) {
        super("No Java environment found, you need to configure " + javaEnv + " environment variable");
    }

}
