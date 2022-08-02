package org.dot.properties;

import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DotPropertiesTest {

    @Test
    public void testDotProperties() throws InterruptedException, NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        DotProperties dotProperties = new DotProperties.Builder()
                .requires("propertyOne", "propertyTwo")
                .refresh(1)
                .load(".properties.test");
        for (String property : dotProperties.getRequires())
            assertNotNull(System.getProperty(property));
        Thread.sleep(30000);
    }



}
