package org.dot.properties.enums;

import lombok.Singular;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PropertiesElement {

    @NotNull String name();

    @NotNull String[] regex() default {};

    boolean required() default false;

}
