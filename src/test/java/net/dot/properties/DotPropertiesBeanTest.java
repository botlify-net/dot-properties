package net.dot.properties;

import net.dot.properties.enums.PropertiesElement;
import net.dot.properties.exceptions.PropertiesAreMissingException;
import net.dot.properties.exceptions.NoJavaEnvFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DotPropertiesBeanTest {

    enum MyTestEnum { VALUE_ONE, VALUE_TWO };

    @PropertiesElement(name = "propertyOne", required = true)
    private String propertyOne = "default";

    @PropertiesElement(name = "propertyTwo", required = true)
    private String propertyTwo = "default";

    @PropertiesElement(name = "propertyInteger", required = true)
    private int propertyInteger = 0;

    @PropertiesElement(name = "propertyEnumBean", required = true)
    private MyTestEnum propertyEnum = MyTestEnum.VALUE_ONE;

    @Test
    public void testBean() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        DotProperties dotProperties = new DotProperties.Builder()
                .bean(this)
                .setPath(".properties.test")
                .build();
        assertNotNull(dotProperties);
        assertEquals("foo", propertyOne);
        assertEquals("bar", propertyTwo);
        assertEquals(42, propertyInteger);
        assertEquals(MyTestEnum.VALUE_TWO, propertyEnum);
    }

}
