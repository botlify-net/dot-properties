package org.dot.properties;

import org.dot.properties.enums.PropertiesElement;
import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DotPropertiesBeanTest {

    @PropertiesElement(name = "propertyOne", required = true)
    private String propertyOne = "default";

    @PropertiesElement(name = "propertyTwo", required = true)
    private String propertyTwo = "default";

    @Test
    public void testBean() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        DotProperties dotProperties = new DotProperties.Builder()
                .bean(this)
                .setPath(".properties.test")
                .build();
        assertNotNull(dotProperties);
        assertEquals("foo", propertyOne);
        assertEquals("bar", propertyTwo);
    }

}
