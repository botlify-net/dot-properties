package org.dot.properties.events;

public interface DotPropertiesListener {

    void onPropertyChanged(String propertiesName, String oldValue, String newValue);

}
