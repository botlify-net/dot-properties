package org.dot.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;

import java.io.IOException;
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

    private void refreshProperties() throws IOException, PropertiesAreMissingException, NoJavaEnvFoundException {
        Properties properties = (builder.fileName != null) ? FileUtils.readProperties(builder.fileName, builder.inResource) : FileUtils.readProperties(builder.javaEnv);
        checkIfAllPropertiesExist(properties);
        for (String property : properties.stringPropertyNames()) {
            String value = properties.getProperty(property);
            System.setProperty(property, value);
        }
    }

    private void checkIfAllPropertiesExist(Properties properties) throws PropertiesAreMissingException {
        if (builder.requires.isEmpty()) return;
        List<String> notSetProperties = new ArrayList<>();
        for (String property : builder.requires) {
            String value = properties.getProperty(property);
            if (value == null || value.isEmpty()) notSetProperties.add(property);
        }
        if (!notSetProperties.isEmpty())
            throw new PropertiesAreMissingException(notSetProperties);
    }

    // create a repeat task
    private void startTaskRefresh() {
        if (!builder.refresh) return;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    refreshProperties();
                    logger.trace("Properties was refreshed (file: " + builder.fileName + ")");
                } catch (IOException | PropertiesAreMissingException | NoJavaEnvFoundException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        };
        builder.timer.scheduleAtFixedRate(timerTask, 0, builder.duration.toMillis());
    }

    /*
     $      Binding
     */

    public String getProperty(String property) {
        return System.getProperty(property);
    }

    /*
     $      Events
     */

    public static void registerListener(DotPropertiesListener listener) {
        DotPropertiesEvent.addListener(listener);
    }

    public static void unregisterListener(DotPropertiesListener listener) {
        DotPropertiesEvent.removeListener(listener);
    }

    /*
     $      Getters and setters
     */


    public String getJavaEnv() {
        return (builder.javaEnv);
    }

    public String getFileName() {
        return (builder.fileName);
    }

    public Duration getDuration() {
        return (builder.duration);
    }

    public Timer getTimer() {
        return (builder.timer);
    }

    public List<String> getRequires() {
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
        private final ArrayList<String> requires = new ArrayList<>();
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
