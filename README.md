# JWhisper Common Library

JWhisper Common Library is a part of JWhisper platform, providing
common tools used by other parts of the platform such as relay server or client apps.

## Prerequisites

To run the library you need to have `JDK 25+` installed.

To build the library from sources you will also need some modern version of `Maven 3.X` installed.

## Build

To build the library (and install it) use the next command:
```bash
mvn clean install
```

It will add the library to installed ones, so you can use it
to build, for example, relay server or CLI client app.

## Developer docs

To build the `javadoc` you can use the next command:
```bash
mvn clean javadoc:aggregate
```
