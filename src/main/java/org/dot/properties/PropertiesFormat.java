package org.dot.properties;

import lombok.Getter;
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

    @Nullable @Getter
    private final Pattern pattern;

    public PropertiesFormat(@NotNull String name) {
        this.name = name;
        this.pattern = null;
    }

    public PropertiesFormat(@NotNull String name, @NotNull String regex) {
        this.name = name;
        this.pattern = Pattern.compile(regex);
    }

    public PropertiesFormat(@NotNull final String name, @NotNull final Pattern pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    public PropertiesFormat(@NotNull final String name, @NotNull Default defaultFormat) {
        this.name = name;
        this.pattern = Pattern.compile(defaultFormat.getRegex());
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

        if (!name.equals(that.name)) return false;
        return Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
        return result;
    }
}
