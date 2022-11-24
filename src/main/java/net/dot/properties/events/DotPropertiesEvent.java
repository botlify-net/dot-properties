package net.dot.properties.events;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DotPropertiesEvent {

    private static final List<DotPropertiesListener> properties = new ArrayList<DotPropertiesListener>();

    /*
     $      Register a listener for the events.
     */

    public static void addListener(@NotNull DotPropertiesListener listener) {
        properties.add(listener);
    }

    public static void removeListener(@NotNull DotPropertiesListener listener) {
        properties.remove(listener);
    }

    /*
     $      Toggle event
     */

    /**
     * Call the toggle event for all the listeners.
     * @param name The name of the property.
     * @param oldValue The old value of the property.
     * @param newValue The new value of the property.
     */
    public static void togglePropertyChanged(@NotNull String name,
                                             @NotNull String oldValue,
                                             @NotNull String newValue) {
        for (DotPropertiesListener listener : properties)
            listener.onPropertyChanged(name, oldValue, newValue);
    }

}
