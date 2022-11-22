package org.dot.properties;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

public class PropertiesFormat {

    public enum Default {
        TIME("^(\\d{1,2}):(\\d{1,2})$"),
        DATE("^(\\d{1,2})/(\\d{1,2})/(\\d{4})$"),
        DATETIME("^(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{1,2}):(\\d{1,2})$"),
        BOOLEAN("^(true|false)$"),
        URL("^(http|https)://.*$"),
        INTEGER("^(\\d+)$"),
        DOUBLE("^(\\d+\\.\\d+)$"),
        FLOAT("^(\\d+\\.\\d+)$"),
        STRING("^(.+)$"),
        LOCALTIME("^(\\d{1,2}):(\\d{1,2})$"),
        MONGO_URL("^(mongodb://)(\\w+):(\\w+)@([\\w.]+):(\\d+)/(\\w+)$"),
        POSTGRESQL_URL("^(jdbc:postgresql://)([\\w.]+):(\\d+)/(\\w+)$"),
        MYSQL_URL("^(jdbc:mysql://)([\\w.]+):(\\d+)/(\\w+)$"),
        REDIS_URL("^(redis://)(\\w+):(\\w+)@([\\w.]+):(\\d+)$");

        @Getter
        private final String regex;

        Default(@NotNull final String regex) {
            this.regex = regex;
        }

    }

    @NotNull @Getter
    private final String name;

    @Nullable @Getter @Setter
    private Pattern pattern;

    public PropertiesFormat(@NotNull String name) {
        this.name = name;
        this.pattern = null;
    }

    public PropertiesFormat(@NotNull String name,
                            @Nullable String regex) {
        this.name = name;
        if (regex != null) {
            this.pattern = Pattern.compile(regex);
        } else {
            this.pattern = null;
        }
    }

    public PropertiesFormat(@NotNull final String name,
                            @NotNull final Pattern pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    public PropertiesFormat(@NotNull final String name,
                            @NotNull Default defaultFormat) {
        this.name = name;
        this.pattern = Pattern.compile(defaultFormat.getRegex());
    }

    public PropertiesFormat(@NotNull final String name,
                            @NotNull final Class<? extends Enum<?>> enumClass) {
        this.name = name;
        // create a pattern from the enum values
        StringBuilder sb = new StringBuilder();
        for (Enum<?> e : enumClass.getEnumConstants()) {
            if (sb.length() > 0) {
                sb.append("|");
            }
            sb.append(e.name());
        }
        this.pattern = Pattern.compile(sb.toString());
    }

    public boolean verifyFormat(@NotNull final String value) {
        if (pattern == null) return (true);
        return (pattern.matcher(value).matches());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertiesFormat that = (PropertiesFormat) o;
        return (name.equals(that.name));
    }

    @Override
    public int hashCode() {
        return (name.hashCode());
    }
}
