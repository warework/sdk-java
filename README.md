# Warework SDK for Java

The **Warework SDK for Java** allows Java developers to easily perform multiple backend operations
on different platforms, using simple, common and efficient interfaces. Get started with the ***Warework Framework*** in minutes using ***Maven***.

## Release Notes ##
Changes to the SDK beginning with version 3.0.0 are tracked in [CHANGELOG.md][changes-file].

## Getting Started

#### Minimum requirements ####

To run the SDK you will need **Java 1.5+**.

#### Install the SDK ####

The recommended way to use the Warework SDK for Java in your project is to download and execute it from Maven:

##### Importing SDK for desktop applications #####

```xml
<dependencyManagement>
   <dependencies>
      <dependency>
         <groupId>com.warework</groupId>
         <artifactId>warework-java-boot-loader-se</artifactId>
         <version>3.0.0</version>
      </dependency>
   </dependencies>
</dependencyManagement>
```

##### Importing SDK for JEE web applications #####

```xml
<dependencyManagement>
   <dependencies>
      <dependency>
         <groupId>com.warework</groupId>
         <artifactId>warework-java-boot-loader-servlet-6.0</artifactId>
         <version>3.0.0</version>
      </dependency>
   </dependencies>
</dependencyManagement>
```

## Features

* Modular and extremely portable: designed to run on desktop, server, mobile and really small devices.

* Performs operations with logs, databases, file systems, mail,...***Warework is a multi-purpose Java framework***.

* Common interfaces for third party libraries that perform the same tasks.

[changes-file]: ./CHANGELOG.md
