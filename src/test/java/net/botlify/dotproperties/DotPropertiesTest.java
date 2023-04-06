package net.botlify.dotproperties;

import net.botlify.dotproperties.annotations.Property;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DotPropertiesTest {

    public enum MyTestEnum {
        VALUE_ONE, VALUE_TWO
    };

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

    /**
     * This test will load the properties file and check if the values are correct.
     */
    @Test
    public void load() {
        DotProperties dotProperties = new DotProperties();
        dotProperties.load(this);

        // Print all file in ressource folder
        System.out.println("Files in ressource folder:");

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
