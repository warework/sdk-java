# Warework SDK for Java

The **Warework SDK for Java** allows Java developers to easily perform multiple backend operations
on different platforms, using simple, common and efficient interfaces. Get started with the ***Warework Framework*** in minutes using ***Maven***.

## Release Notes ##
Changes to the SDK beginning with version 3.0.0 are tracked in [CHANGELOG.md][changes-file].

Prior releases to version 3.0.0 are not available due to license restrictions.

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

* Modular and highly portable: designed to run on desktop, server, mobile and really small devices.

* Performs operations with logs, databases, file systems, mail,...***Warework is a multi-purpose Java framework***.

* Common interfaces for third party libraries that perform the same tasks.


## Desktop application example

Setup the framework to perform log operations with Log4j and execute queries in a database:

##### Maven #####

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

##### Main config file #####

Warework boots up by loading an XML file where you can specify which componentes of the framework (services and providers) will be available during the life span of the application:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<scope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="xsd/scope.xsd">	
	
	<providers>	
		<provider name="ddbb-connection-provider" class="com.warework.provider.PooledObjectProvider">
			<parameter name="service-name" value="pool-service" />
		</provider>
	</providers>	
	
	<services>
		<service name="log-service" class="com.warework.service.log.LogServiceImpl" >
			<parameter name="config-class" value="com.warework.loader.ProxyServiceXmlLoader"/>			
			<parameter name="config-target" value="/META-INF/my-app-name/service/log-service.xml"/>
		</service>	
		<service name="datastore-service" class="com.warework.service.datastore.DatastoreServiceImpl">
			<parameter name="config-class" value="com.warework.loader.DatastoreXmlLoader"/>	
			<parameter name="config-target" value="/META-INF/my-app-name/service/datastore-service.xml"/>
		</service>	
		<service name="pool-service" class="com.warework.service.pool.PoolServiceImpl">
			<parameter name="config-class" value="com.warework.loader.ProxyServiceXmlLoader"/>
			<parameter name="config-target" value="/META-INF/my-app-name/service/pool-service.xml" />
		</service>		
	</services>
	
</scope>
```

##### Log config files #####

Warework XML configuration file to setup Log4j:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy-service xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="../../xsd/proxy-service.xsd">

	<clients>	
		<client name="default-client" connector="com.warework.service.log.client.connector.Log4jPropertiesConnector">
			<parameter name="config-target" value="/META-INF//my-app-name/log/log4j.properties"/>
			<parameter name="connect-on-create" value="true"/>
		</client>
	</clients>

</proxy-service>
```

Log4j classic properties configuration file:

```properties
# Root logger option
log4j.rootLogger=DEBUG, stdout

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy/MM/dd HH:mm:ss.SSS} %-5p - [%t] - %m%n
```

[changes-file]: ./CHANGELOG.md
