package org.dot.properties.exceptions;

public class NoJavaEnvFoundException extends Exception {

    public NoJavaEnvFoundException() {
        super("No Java environment found, you need to configure JAVA_ENV environment variable");
    }

}
