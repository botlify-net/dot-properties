package org.dot.properties;

import java.util.ArrayList;

class DotPropertiesEvent {

    private static final ArrayList<DotPropertiesListener> properties = new ArrayList<DotPropertiesListener>();

    /*
     $      Register a listener for the events.
     */

    public static void addListener(DotPropertiesListener listener) {
        properties.add(listener);
    }

    public static void removeListener(DotPropertiesListener listener) {
        properties.remove(listener);
    }

    /*
     $      Toggle event
     */

    public static void togglePropertyChanged(String name, String oldValue, String newValue) {
        for (DotPropertiesListener listener : properties)
            listener.onPropertyChanged(name, oldValue, newValue);
    }

}
