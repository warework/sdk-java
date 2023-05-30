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
   <dependency>
      <groupId>com.warework</groupId>
      <artifactId>warework-java-mod-datastore-ext</artifactId>
      <version>3.0.0</version>
   </dependency>	
   <dependency>
      <groupId>com.warework</groupId>
      <artifactId>warework-java-ser-log-log4j</artifactId>
      <version>3.0.0</version>
   </dependency>
   <dependency>
      <groupId>com.warework</groupId>
      <artifactId>warework-java-ser-pool-c3p0</artifactId>
      <version>3.0.0</version>
   </dependency>		
</dependencyManagement>
```

##### Main config file (/META-INF/my-app-config.xml) #####

Warework boots up by loading an XML file where you can specify which componentes of the framework (services and providers) will be available during the life span of the application:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<scope>	
	
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
		<service name="pool-service" class="com.warework.service.pool.PoolServiceImpl">
			<parameter name="config-class" value="com.warework.loader.ProxyServiceXmlLoader"/>
			<parameter name="config-target" value="/META-INF/my-app-name/service/pool-service.xml" />
		</service>	
		<service name="datastore-service" class="com.warework.service.datastore.DatastoreServiceImpl">
			<parameter name="config-class" value="com.warework.loader.DatastoreXmlLoader"/>	
			<parameter name="config-target" value="/META-INF/my-app-name/service/datastore-service.xml"/>
		</service>	
	</services>
	
</scope>
```

##### Log config files (/META-INF/my-app-name/service/log-service.xml) #####

Warework XML configuration file to setup Log4j:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy-service>
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

##### Connection pool config file (/META-INF/my-app-name/service/pool-service.xml) #####

Warework XML configuration file to setup C3P0:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy-service>
	<client name="my-jdbc-connection-pool" connector="com.warework.service.pool.client.connector.C3P0Connector">			
		<parameter name="jdbc-url" value="jdbc:postgresql://127.0.0.1:5432/my-app-database?user=user-name&amp;password=the-password"/>						
		<parameter name="driver-class" value="org.postgresql.Driver"/>
		<parameter name="connect-on-create" value="true"/>			
	</client>	
</proxy-service>
```

##### Datastore config file (/META-INF/my-app-name/service/datastore-service.xml) #####

Warework XML configuration file to setup and interface to perform operations with JDBC:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<datastore-service>	
	<datastores>
		<datastore name="my-jdbc-datastore" connector="com.warework.service.datastore.client.connector.JdbcConnector">
			<parameters>
				<parameter name="client-connection-provider-name" value="ddbb-connection-provider"/>
				<parameter name="client-connection-provider-object" value="my-jdbc-connection-pool"/>
			</parameters>
			<views>
				<view name="default" class="com.warework.service.datastore.client.JdbcViewImpl" />
			</views>
		</datastore>				
	</datastores>
</datastore-service>
```

##### Java examples #####

Boot up the framework by providing a context ***MyApp.class*** (any class from your project) to load the previously created config files:

```java
ScopeFacade scope = ScopeContext.create(MyApp.class, "my-app-config", "my-app-name", null);
```

Log a message with Log4j:

```java
scope.debug("Hello!");
```

Get an interface to perform operations with the relational database:

```java
// Get an instance of the Data Store Service. 
DatastoreServiceFacade datastoreService = (DatastoreServiceFacade) scope.getService("datastore-service");

// Get an instance of a RDBMS View interface.
RDBMSView view = (RDBMSView) datastoreService.getView("my-jdbc-datastore");

// Connect the Data Store.
view.connect();

// Begin a transaction in the database management system.
view.beginTransaction();

// Create the SQL statement to execute.
String sql = "INSERT INTO HOME_USER (ID, NAME) VALUES (1, 'John Wood')";

// Run the SQL update statement.
view.executeUpdate(sql, null, null);

```


[changes-file]: ./CHANGELOG.md
