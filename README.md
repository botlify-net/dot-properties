# Dot Properties

[![Latest release](https://img.shields.io/github/release/botlify-net/dot-properties.svg)](https://github.com/botlify-net/dot-properties/releases/latest)
[![Build Status](https://github.com/botlify-net/dot-properties/workflows/Java%20CI/badge.svg?branch=master)](https://github.com/botlify-net/dot-properties/actions)

Du to the impossibility to modify the environment variables via Java.
We need to use the properties file.

This module help you to manage your properties in your project.

## How to install ?

Add the dependency in your pom.xml:
```xml
<dependency>
    <groupId>net.botlify.dot-properties</groupId>
    <artifactId>dot-properties</artifactId>
    <version>LATEST</version>
</dependency>
```

You should have Maven installed. If not you can have the instructions 
to install it here: https://maven.apache.org/install.html

## Basic usage

## Without JAVA_PROPS (Recommended)

In case you don't use the **JAVA_PROPS** environment variables, the DotProperties will try to load properties file in the following order:

````bash
.properties # in the file system
.properties # in the resource directory
````

Example:
````java
DotProperties dotProperties = new DotProperties();
dotProperties.load(this);
````

_When a file is found, the properties are load and the others files are ignored._

## With JAVA_PROPS (Not recommended)

Before everything you should configure the **JAVA_PROPS** environment variables.

In the following case the DotProperties will try to load properties file in the following order:

````bash
.${JAVA_PROPS}.properties # in the file system
.properties.${JAVA_PROPS} # in the file system
.${JAVA_PROPS}.properties # in the resource directory
.properties.${JAVA_PROPS} # in the resource directory
````

Example:
````java
DotPropertiesConfig config = new DotPropertiesConfig();
config.enableJavaEnv();

DotProperties dotProperties = new DotProperties(config);
dotProperties.load(this);
````

### Build with annotations

You can use the annotation **@PropertiesElement** to build your DotProperties.

````java
enum MyTestEnum { VALUE_ONE, VALUE_TWO };

@Property(name = "propertyOne", required = true)
private String propertyOne = "default";

@Property(name = "propertyTwo", required = true)
private String propertyTwo = "default";

@Property(name = "propertyInteger", required = true)
private int propertyInteger = 0;

@Property(name = "propertyEnumBean", required = true)
private MyTestEnum propertyEnum = MyTestEnum.VALUE_ONE;

public static void main(String[] args) throws NoJavaEnvFoundException, PropertiesAreMissingException, IOException{
    DotProperties dotProperties = new DotProperties();
    dotProperties.load(this);
}
````

Supported types:
- Long
- Integer
- Short
- Byte
- Double
- Float
- Boolean
- Character
- Date
- Duration
- Instant
- String
- File
- Pattern
- ZonedDateTime
- TimeZone
- Enum
- ZoneId

## Need more option ?

If you need more options not developed in this module you can open a issue.
Don't forget to leave a star on the repository.