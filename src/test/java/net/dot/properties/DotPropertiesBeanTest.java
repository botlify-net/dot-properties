package net.dot.properties;

import net.dot.properties.enums.Property;
import net.dot.properties.exceptions.PropertiesAreMissingException;
import net.dot.properties.exceptions.NoJavaEnvFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DotPropertiesBeanTest {

    public enum MyTestEnum { VALUE_ONE, VALUE_TWO };

    @Property(name = "propertyOne", required = true)
    public String propertyOne = "default";

    @Property(name = "propertyTwo", required = true)
    public String propertyTwo = "default";

    @Property(name = "propertyInteger", required = true)
    public int propertyInteger = 0;

    @Property(name = "propertyEnumBean", required = true)
    public MyTestEnum propertyEnum = MyTestEnum.VALUE_ONE;

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
