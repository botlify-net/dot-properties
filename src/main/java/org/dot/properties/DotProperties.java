package org.dot.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dot.properties.events.DotPropertiesEvent;
import org.dot.properties.events.DotPropertiesListener;
import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;
import org.dot.properties.exceptions.PropertiesBadFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.time.Duration;
import java.util.*;

public class DotProperties {

    private static final Logger logger = LogManager.getLogger(DotProperties.class);

    /*
     $      Constructors
     */

    private final Builder builder;

    private DotProperties(Builder builder) throws PropertiesAreMissingException, IOException, NoJavaEnvFoundException {
        this.builder = builder;
        refreshProperties();
        if (builder.refresh) startTaskRefresh();
    }



    /*
     $      Private methods
     */

    private void refreshProperties() throws IOException, NoJavaEnvFoundException {
        Properties properties = (builder.fileName != null) ? FileUtils.readProperties(builder.fileName, builder.inResource) : FileUtils.readProperties(builder.javaEnv);
        checkIfAllPropertiesExist(properties);
        checkAllFormats(properties);
        for (String property : properties.stringPropertyNames()) {
            String oldValue = System.getProperty(property);
            String value = properties.getProperty(property);
            System.setProperty(property, value);
            if (oldValue != null && !oldValue.equals(value))
                DotPropertiesEvent.togglePropertyChanged(property, oldValue, value);
        }
    }

    private void checkIfAllPropertiesExist(Properties properties) {
        if (builder.requires.isEmpty()) return;
        List<String> notSetProperties = new ArrayList<>();
        for (PropertiesFormat propertyFormat : builder.requires) {
            String value = properties.getProperty(propertyFormat.getName());
            if (value == null || value.isEmpty()) notSetProperties.add(propertyFormat.getName());
        }
        if (!notSetProperties.isEmpty())
            throw new RuntimeException(new PropertiesAreMissingException(notSetProperties));
    }

    private void checkAllFormats(Properties properties) {
        List<PropertiesFormat> propertiesFormats = new ArrayList<>();
        for (PropertiesFormat propertyFormat : builder.requires) {
            String value = properties.getProperty(propertyFormat.getName());
            if (value == null || value.isEmpty()) continue;
            if (!propertyFormat.verifyFormat(value))
                propertiesFormats.add(propertyFormat);
        }
        if (!propertiesFormats.isEmpty())
            throw (new RuntimeException(new PropertiesBadFormat(propertiesFormats)));
    }

    // create a repeat task
    private void startTaskRefresh() {
        if (!builder.refresh) return;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    refreshProperties();
                    logger.trace("Properties was refreshed, next in " + builder.duration.toSeconds() + " seconds");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        };
        builder.timer.scheduleAtFixedRate(timerTask, 0, builder.duration.toMillis());
    }

    /*
     $      Binding
     */

    public @Nullable String getProperty(@NotNull final String property) {
        return System.getProperty(property);
    }

    public @NotNull String getProperty(@NotNull final String property,
                                       @NotNull final String defaultValue) {
        return System.getProperty(property, defaultValue);
    }

    /*
     $      Update
     */

    public boolean setProperty(@NotNull final String property,
                               @NotNull final String value) throws IOException {
        String oldValue = System.getProperty(property);
        if (oldValue == null || oldValue.equals(value)) return false;
        System.setProperty(property, value);
        FileUtils.changePropertyInFile(builder.fileName, property, value);
        DotPropertiesEvent.togglePropertyChanged(property, oldValue, value);
        return true;
    }

    /*
     $      Events
     */

    public static void registerListener(@NotNull final DotPropertiesListener listener) {
        DotPropertiesEvent.addListener(listener);
    }

    public static void unregisterListener(@NotNull final DotPropertiesListener listener) {
        DotPropertiesEvent.removeListener(listener);
    }
    
    /*
     $      Getters and setters
     */


    public String getJavaEnv() {
        return (builder.javaEnv);
    }

    public Duration getDuration() {
        return (builder.duration);
    }

    public List<PropertiesFormat> getRequires() {
        return (builder.requires);
    }

    public Boolean isRefresh() {
        return (builder.refresh);
    }

    /*
     $      Builder
     */

    public static class Builder {

        private String javaEnv = "JAVA_PROPS";
        private String fileName = null;
        private boolean inResource = false;
        private Duration duration = Duration.ofSeconds(30);
        private boolean refresh = false;
        private final List<PropertiesFormat> requires = new ArrayList<>();
        private final Timer timer = new Timer();

        public Builder() {
            // Nothing to do
        }

        public Builder setJavaEnv(String javaEnv) {
            this.javaEnv = javaEnv;
            return (this);
        }

        public Builder requires(List<String> properties) {
            if (properties == null) return (this);
            return (requires(properties.toArray(new String[0])));
        }

        public Builder requires(String... properties) {
            if (properties == null) return (this);
            for (String str : properties) {
                if (!this.requires.contains(new PropertiesFormat(str)))
                    this.requires.add(new PropertiesFormat(str));
            }
            return (this);
        }

        public Builder requires(PropertiesFormat... properties) {
            if (properties == null) return (this);
            for (PropertiesFormat str : properties) {
                if (!this.requires.contains(str))
                    this.requires.add(str);
            }
            return (this);
        }

        public Builder refresh() {
            this.refresh = true;
            return (this);
        }

        public Builder refresh(int seconds) {
            this.refresh = true;
            this.duration = Duration.ofSeconds(seconds);
            return (this);
        }

        public Builder refresh(Duration duration) {
            this.refresh = true;
            this.duration = duration;
            return (this);
        }

        public Builder setPath(String path) {
            this.inResource = false;
            this.fileName = path;
            return (this);
        }

        public Builder setResourcePath(String path) {
            this.inResource = true;
            this.fileName = path;
            return (this);
        }

        public DotProperties build() throws IOException, PropertiesAreMissingException, NoJavaEnvFoundException {
            return (new DotProperties(this));
        }

    }

}
