package org.dot.properties.exceptions;

import org.dot.properties.DotProperties;

public class NoJavaEnvFoundException extends Exception {

    public NoJavaEnvFoundException() {
        super("No Java environment found, you need to configure JAVA_PROP environment variable");
    }

}
