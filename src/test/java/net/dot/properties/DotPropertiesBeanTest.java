package net.dot.properties;

import net.dot.properties.enums.PropertiesElement;
import net.dot.properties.exceptions.PropertiesAreMissingException;
import net.dot.properties.exceptions.NoJavaEnvFoundException;
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
