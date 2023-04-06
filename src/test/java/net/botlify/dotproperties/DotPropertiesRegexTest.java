package net.botlify.dotproperties;

import net.botlify.dotproperties.annotations.Property;
import net.botlify.dotproperties.exceptions.PropertiesBadFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DotPropertiesRegexTest {

    @Property(name = "propertyLong", regex = "^[a-z]+$", required = true)
    public String test;

    @Test
    public void load() {
        final DotPropertiesConfig config = new DotPropertiesConfig();
        final DotProperties dotProperties = new DotProperties(config);
        assertThrows(PropertiesBadFormat.class, () -> dotProperties.load(this));

    }

}
