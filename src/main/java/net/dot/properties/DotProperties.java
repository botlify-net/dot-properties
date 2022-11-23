package net.dot.properties;

import net.dot.properties.fields.PropertyField;
import net.dot.properties.fields.PropertyFieldManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.dot.properties.enums.Property;
import net.dot.properties.events.DotPropertiesEvent;
import net.dot.properties.events.DotPropertiesListener;
import net.dot.properties.exceptions.NoJavaEnvFoundException;
import net.dot.properties.exceptions.PropertiesAreMissingException;
import net.dot.properties.exceptions.PropertiesBadFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Pattern;

public class DotProperties {

    public static final Logger logger = LogManager.getLogger(DotProperties.class);

    /*
     $      Constructors
     */

    private final Builder builder;

    private DotProperties(@NotNull Builder builder) throws IOException, NoJavaEnvFoundException {
        logger.trace("Initializing new DotProperties");
        this.builder = builder;
        getRequiredFromBeen();
        refreshProperties();
        if (builder.refresh)
            startTaskRefresh();
    }



    /*
     $      Private methods
     */

    private void getRequiredFromBeen() {
        if (builder.bean == null) {
            logger.trace("No bean found, skipping bean parsing");
            return;
        }
        // get PropertiesElement annotation from bean class
        logger.trace("Getting PropertiesElement annotation from bean class: {}", builder.bean.getClass().getName());
        Field[] fields = builder.bean.getClass().getDeclaredFields();
        logger.trace("Found {} fields in {}", fields.length, builder.bean.getClass().getName());
        List<Property> propertiesElements = new ArrayList<>();
        for (Field field : fields) {
            for (Annotation annotation : field.getAnnotations()) {
                if (!(annotation instanceof Property))
                    continue;
                propertiesElements.add((Property) annotation);
            }
        }
        if (propertiesElements.size() == 0) {
            logger.trace("No properties element found in bean, skipping bean parsing");
            return;
        }
        logger.trace("Found {} properties element in bean", propertiesElements.size());
        for (Property propElem : propertiesElements) {
            if (!propElem.required())
                continue;
            PropertiesFormat propertiesFormat = new PropertiesFormat(propElem.name());
            if (propElem.regex().length != 0)
                propertiesFormat.setPattern(Pattern.compile(propElem.regex()[0]));
            if (propElem.format().length != 0)
                propertiesFormat.setPattern(Pattern.compile(propElem.format()[0].getRegex()));
            if (getRequires().contains(propertiesFormat)) {
                logger.warn("Property {} already exists in requires list", propertiesFormat.getName());
                continue;
            }
            getRequires().add(propertiesFormat);
            logger.trace("Property {} added to requires list", propertiesFormat.getName());
        }
    }

    private void updateAnnotationInBeen(@NotNull String key,
                                        @NotNull String value) {
        if (builder.bean == null)
            return;
        final Map<Field, Property> fieldPropertiesElementMap = getPropertiesElements();
        for (Field field : fieldPropertiesElementMap.keySet()) {
            Property propertiesElement = fieldPropertiesElementMap.get(field);
            if (!propertiesElement.name().equals(key))
                continue;
            try {
                logger.trace("Updating field {} in bean...", field.getName());
                boolean result = PropertyFieldManager.parseField(builder.bean, field, value);
                if (!result)
                    logger.warn("Unable to parse field {} in bean {}", field.getName(), builder.bean.getClass().getName());
                else
                    logger.trace("Field {} in bean {} updated with value {}", field.getName(), builder.bean.getClass().getName(), value);
            } catch (Exception e) {
                logger.error("Error while updating bean field: {}", e.getMessage());
            }
        }
    }

    private void refreshProperties() throws IOException, NoJavaEnvFoundException {
        Properties properties = (builder.fileName != null) ? FileUtils.readProperties(builder.fileName, builder.inResource) : FileUtils.readProperties(builder.javaEnv);
        checkIfAllPropertiesExist(properties);
        checkAllFormats(properties);
        for (String property : properties.stringPropertyNames()) {
            String oldValue = System.getProperty(property);
            String value = properties.getProperty(property);
            System.setProperty(property, value);
            updateAnnotationInBeen(property, value);
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

    /**
     * Return the map of all fields annotated with {@link Property}.
     * @return The map of all fields annotated with {@link Property}.
     */
    private @NotNull Map<Field, Property> getPropertiesElements() {
        Map<Field, Property> propertiesElements = new HashMap<>();
        if (builder.bean == null) return (propertiesElements);
        for (Field field : builder.bean.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (!(annotation instanceof Property))
                    continue;
                propertiesElements.put(field, (Property) annotation);
            }
        }
        return (propertiesElements);
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
        updateAnnotationInBeen(property, value);
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
        private Object bean;

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

        public Builder bean(@Nullable final Object bean) {
            this.bean = bean;
            return (this);
        }

        public DotProperties build() throws IOException, PropertiesAreMissingException, NoJavaEnvFoundException {
            return (new DotProperties(this));
        }

    }

}
