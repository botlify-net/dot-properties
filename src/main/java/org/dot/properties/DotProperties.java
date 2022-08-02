package org.dot.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dot.properties.exceptions.NoJavaEnvFoundException;
import org.dot.properties.exceptions.PropertiesAreMissingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class DotProperties {

    private static final Logger logger = LogManager.getLogger(DotProperties.class);

    private Duration duration = Duration.ofSeconds(30);
    private Boolean refresh = false;
    private final ArrayList<String> requires = new ArrayList<>();
    private String fileName = null;
    private final Timer timer = new Timer();

    /*
     $      Constructors
     */

    public DotProperties() {

    }

    /*
     $      Public methods
     */

    public DotProperties require(String property) {
        return (requires(Collections.singletonList(property)));
    }

    public DotProperties requires(List<String> properties) {
        if (properties == null) return (this);
        return (requires(properties.toArray(new String[0])));
    }

    public DotProperties requires(String... properties) {
        if (properties == null) return (this);
        for (String str : properties) {
            if (!this.requires.contains(str))
                this.requires.add(str);
        }
        return (this);
    }

    public DotProperties refresh(int seconds) {
        this.refresh = true;
        this.duration = Duration.ofSeconds(seconds);
        return (this);
    }

    public DotProperties refresh(Duration duration) {
        this.refresh = true;
        this.duration = duration;
        return (this);
    }

    public DotProperties load(String fileName) throws NoJavaEnvFoundException, IOException, PropertiesAreMissingException {
        this.fileName = fileName;
        refreshProperties();
        return (this);
    }

    public DotProperties load() throws NoJavaEnvFoundException, IOException, PropertiesAreMissingException {
        String javaEnv = System.getenv("JAVA_PROP");
        if (javaEnv == null || javaEnv.isEmpty())
            throw new NoJavaEnvFoundException();
        String fileName = ".properties." + javaEnv;
        startTaskRefresh();
        return (load(fileName));
    }

    /*
     $      Private methods
     */

    private void refreshProperties() throws IOException, PropertiesAreMissingException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(fileName));
        checkIfAllPropertiesExist(properties);
        for (String property : properties.stringPropertyNames()) {
            String value = properties.getProperty(property);
            System.setProperty(property, value);
        }
    }

    private void checkIfAllPropertiesExist(Properties properties) throws PropertiesAreMissingException {
        if (requires.isEmpty()) return;
        List<String> notSetProperties = new ArrayList<>();
        for (String property : requires) {
            String value = properties.getProperty(property);
            if (value == null || value.isEmpty()) notSetProperties.add(property);
        }
        if (!notSetProperties.isEmpty())
            throw new PropertiesAreMissingException(notSetProperties);
    }

    // create a repeat task
    private void startTaskRefresh() {
        if (!refresh) return;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    refreshProperties();
                    logger.trace("Properties was refreshed (file: " + fileName + ")");
                } catch (IOException | PropertiesAreMissingException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, duration.toMillis());
    }

    public void setRefreshDuration(Duration duration) {
        cancelRefresh();
        this.duration = duration;
        startTaskRefresh();
    }

    public void cancelRefresh() {
        this.timer.cancel();
    }

    /*
     $      Getters and setters
     */

    public Duration getDuration() {
        return (duration);
    }

    public Boolean getRefresh() {
        return (refresh);
    }

    public ArrayList<String> getRequires() {
        return (requires);
    }

    public String getFileName() {
        return (fileName);
    }
}
