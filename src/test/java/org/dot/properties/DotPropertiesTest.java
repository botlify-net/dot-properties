package org.dot.properties;

import org.dot.properties.events.DotPropertiesEvent;
import org.dot.properties.events.DotPropertiesListener;
import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DotPropertiesTest implements DotPropertiesListener {

    private static boolean eventCalled = false;

    @Test
    public void testDotProperties() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        DotProperties dotProperties = new DotProperties.Builder()
                .requires("propertyOne", "propertyTwo")
                .setPath(".properties.test")
                .refresh()
                .build();

        for (PropertiesFormat property : dotProperties.getRequires())
            assertNotNull(System.getProperty(property.getName()));
    }

    @Test
    public void testDotPropertiesNoFile() throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException {
        Assertions.fail();
        try {
            DotProperties dotProperties = new DotProperties.Builder()
                    .requires("propertyOne", "propertyTwo")
                    .setResourcePath(".properties.notexist")
                    .build();
            assertNotNull(null);
        } catch (IOException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testPropertiesEvents() {
        DotProperties.registerListener(this);
        DotPropertiesEvent.togglePropertyChanged("propertiesName", "oldValue", "newValue");
        assertTrue(eventCalled);
    }

    @Override
    public void onPropertyChanged(String propertiesName, String oldValue, String newValue) {
        assertEquals("propertiesName", propertiesName);
        assertEquals("oldValue", oldValue);
        assertEquals("newValue", newValue);
        eventCalled = true;
    }

}
