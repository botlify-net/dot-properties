package org.dot.properties;

public interface DotPropertiesListener {

    void onPropertyChanged(String propertiesName, String oldValue, String newValue);

}
