package org.dot.properties.exceptions;

public class NoJavaEnvFoundException extends Exception {

    public NoJavaEnvFoundException(String javaEnv) {
        super("No Java environment found, you need to configure " + javaEnv + " environment variable");
    }

}
