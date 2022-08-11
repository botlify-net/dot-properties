package org.dot.properties;

import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DotPropertiesTest {

    @Test
    public void testDotProperties() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        DotProperties dotProperties = new DotProperties.Builder()
                .requires("propertyOne", "propertyTwo")
                .setFile(".properties.test")
                .build();

        for (String property : dotProperties.getRequires())
            assertNotNull(System.getProperty(property));
    }

    @Test
    public void testDotPropertiesNoFile() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        DotProperties dotProperties = new DotProperties.Builder()
                .requires("propertyOne", "propertyTwo")
                .setResourceFile(".properties.notexist")
                .build();
    }

}
