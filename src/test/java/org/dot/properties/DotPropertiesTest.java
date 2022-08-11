package org.dot.properties;

import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DotPropertiesTest {

    @Test
    public void testDotProperties() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        DotProperties dotProperties = new DotProperties.Builder()
                .requires("propertyOne", "propertyTwo")
                .setPath(".properties.test")
                .refresh()
                .build();

        for (String property : dotProperties.getRequires())
            assertNotNull(System.getProperty(property));
    }

    @Test
    public void testDotPropertiesNoFile() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        try {
            DotProperties dotProperties = new DotProperties.Builder()
                    .requires("propertyOne", "propertyTwo")
                    .setResourcePath(".properties.notexist")
                    .build();
            assertNotNull(null);
        } catch (IOException e) {
            assertNotNull(e);
        }
    }

}
