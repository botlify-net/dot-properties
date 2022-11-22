package net.dot.properties;

import net.dot.properties.exceptions.PropertiesAreMissingException;
import net.dot.properties.exceptions.NoJavaEnvFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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