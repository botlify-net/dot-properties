package org.dot.properties;

import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesFormatTest {

    enum TestEnum {
        FOO,
        BAR
    }

    @Test
    public void testFromEnum() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        PropertiesFormat propertiesFormat = new PropertiesFormat("propertyEnum", TestEnum.class);
        DotProperties dotProperties = new DotProperties.Builder()
                .requires(propertiesFormat)
                .setPath(".properties.test")
                .build();

    }

}