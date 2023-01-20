package net.dot.properties;

import net.dot.properties.enums.Property;
import net.dot.properties.exceptions.PropertiesAreMissingException;
import net.dot.properties.exceptions.NoJavaEnvFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DotPropertiesBeanTest {

    public enum MyTestEnum { VALUE_ONE, VALUE_TWO };

    @Property(name = "propertyOne", required = true)
    private String propertyOne = "default";

    @Property(name = "propertyTwo", required = true)
    private String propertyTwo = "default";

    @Property(name = "propertyInteger", required = true)
    private int propertyInteger = 0;

    @Property(name = "propertyEnumBean", required = true)
    private MyTestEnum propertyEnum = MyTestEnum.VALUE_ONE;

    @Property(name = "propertyLocalTime", required = true)
    private LocalTime propertyLocalTime = LocalTime.of(11, 0, 0);

    @Property(name = "propertyZoneId", required = true)
    private ZoneId propertyZoneId;

    @Property(name = "propertyLong", required = true)
    private Long propertyLong;

    @Test
    public void testBean() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException, IllegalAccessException {
        DotProperties dotProperties = new DotProperties.Builder()
                .bean(this)
                .setPath(".properties.test")
                .build();
        assertNotNull(dotProperties);
        assertEquals("foo", propertyOne);
        assertEquals("bar", propertyTwo);
        assertEquals(42, propertyInteger);
        assertEquals(MyTestEnum.VALUE_TWO, propertyEnum);
        assertEquals(LocalTime.of(12, 0, 0), propertyLocalTime);
        assertNotNull(propertyZoneId);
        assertEquals(ZoneId.of("Europe/Paris"), propertyZoneId);
        assertNotNull(propertyLong);
        assertEquals(42L, propertyLong);
    }

}
