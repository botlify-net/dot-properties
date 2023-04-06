package net.botlify.dotproperties.fields.implementations;

import net.botlify.dotproperties.fields.PropertyField;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePropertyField extends PropertyField {

    public DatePropertyField() {
        super(Date.class);
    }

    @Override
    public @NotNull Object parseString(@NotNull final String value) {
        final SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        try {
            return (format.parse(value));
        } catch (ParseException e) {
            throw (new RuntimeException(e));
        }
    }

}
