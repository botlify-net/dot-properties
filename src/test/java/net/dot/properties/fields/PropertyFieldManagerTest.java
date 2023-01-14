package net.dot.properties.fields;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PropertyFieldManagerTest {

    String stringField = "stringField";

    int primitiveIntField = 1;

    Integer objectIntField = 1;

    @Test
    public void testParseField() throws IllegalAccessException {
        Field field = Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.getName().equals("stringField"))
                .findFirst().orElse(null);
        assertNotNull(field);
        PropertyFieldManager.parseField(this, field,"test");
        assertEquals("test", stringField);

        field = Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.getName().equals("primitiveIntField"))
                .findFirst().orElse(null);
        assertNotNull(field);
        PropertyFieldManager.parseField(this, field,"2");
        assertEquals(2, primitiveIntField);

        field = Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> f.getName().equals("objectIntField"))
                .findFirst().orElse(null);
        assertNotNull(field);
        PropertyFieldManager.parseField(this, field, "3");
        assertEquals(3, objectIntField);
    }

}