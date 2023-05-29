# Warework SDK for Java

The **Warework SDK for Java** allows Java developers to easily perform multiple backend operations
on different platforms, using simple, common and elegant interfaces. Get started with the ***Warework Framework*** in minutes using ***Maven***.

## Features

* Modular and extremely portable: designed to run on desktop, server, mobile and really small devices.

* Performs operations with logs, databases, file systems, mail,...***Warework is a multi-purpose Java framework***.

* Common interfaces for third party libraries that perform the same tasks.

## Release Notes ##
Changes to the SDK beginning with version 3.0.0 are tracked in [CHANGELOG.md][changes-file].

## Getting Started

#### Minimum requirements ####

To run the SDK you will need **Java 1.5+**.

#### Install the SDK ####

The recommended way to use the Warework SDK for Java in your project is to consume it from Maven:

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

## Building From Source

Once you check out the code from GitHub, you can build it using Maven. To disable the GPG-signing
in the build, use:

```sh
mvn clean install -Dgpg.skip=true
```

## Getting Help
GitHub [issues][sdk-issues] is the preferred channel to interact with our team. Also check these community resources for getting help:

* Ask a question on [StackOverflow][stack-overflow] and tag it with `aws-java-sdk`
* Come join the AWS Java community chat on [Gitter][gitter]
* Articulate your feature request or upvote existing ones on our [Issues][features] page
* Take a look at the [blog] for plenty of helpful walkthroughs and tips
* Open a case via the [AWS Support Center][support-center] in the [AWS console][console]
* If it turns out that you may have found a bug, please open an [issue][sdk-issues]

## Maintenance and Support for SDK Major Versions
For information about maintenance and support for SDK major versions and their underlying dependencies, see the following in the AWS SDKs and Tools Reference Guide:

* [AWS SDKs and Tools Maintenance Policy][maintenance-policy]
* [AWS SDKs and Tools Version Support Matrix][version-matrix]

## Supported Minor Versions

* **1.12.x** - Recommended.

* **1.11.x** - No longer supported, but migration to 1.12.x should require no code changes.

## AWS SDK for Java 2.x
A version 2.x of the SDK is generally available. It is a major rewrite of the 1.x code base, built on top of Java 8+ and adds several frequently requested features. These include support for non-blocking I/O, improved start-up performance, automatic iteration over paginated responses and the ability to plug in a different HTTP implementation at run time.

For more information see the [AWS SDK for Java 2.x Developer Guide][sdk-v2-dev-guide] or check the project repository in https://github.com/aws/aws-sdk-java-v2.

## Maintenance and Support for Java Versions

The AWS Java SDK version 1 (v1) supports Java versions from 7 to 16. The Java 17 version introduces strong encapsulation of internal Java elements, which is not backwards-compatible with the Java SDK v1. 
This may cause issues for certain use-cases of the SDK. If you plan to use Java 17+, we recommend that you migrate to
[AWS SDK for Java 2.x][aws-sdk-for-java-2x] that fully supports Java 8, Java 11, and Java 17 Long-Term Support(LTS) releases.

If you are experiencing issues with Java 17+ and unable to migrate to AWS SDK for Java v2 at this time, below are the workarounds that you might find helpful.
Please keep in mind that these workarounds may not work in the future 
versions of Java. See [JEP 403: Strongly Encapsulate JDK Internals][jep-403]
and [Breaking Encapsulation][jep-break-encapsulation]
for more details.

**Error: com.amazonaws.AmazonServiceException: Unable to unmarshall
exception response with the unmarshallers provided caused by java.lang.
reflect.InaccessibleObjectException**

- use JVM option `--add-opens java.base/java.lang=ALL-UNNAMED` at JVM startup

**WARNING: Illegal reflective access by com.amazonaws.util.XpathUtils**

- use JVM option `--add-opens=java.xml/com.sun.org.apache.xpath.internal=ALL-UNNAMED` at JVM startup

[aws-iam-credentials]: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-roles.html
[aws]: https://aws.amazon.com/
[blog]: https://aws.amazon.com/blogs/developer/category/java/
[docs-api]: https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/index.html
[docs-guide]: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/welcome.html
[docs-guide-source]: https://github.com/awsdocs/aws-java-developer-guide
[docs-java-env]: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-install.html#installing-a-java-development-environment
[docs-signup]: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/signup-create-iam-user.html
[docs-setup]: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-install.html
[install-jar]: https://sdk-for-java.amazonwebservices.com/latest/aws-java-sdk.zip
[sdk-issues]: https://github.com/aws/aws-sdk-java/issues
[sdk-license]: https://aws.amazon.com/apache2.0/
[sdk-website]: https://aws.amazon.com/sdkforjava
[aws-java-sdk-bom]: https://github.com/aws/aws-sdk-java/tree/master/aws-java-sdk-bom
[release-notes-catalog]: https://aws.amazon.com/releasenotes/Java?browse=1
[changes-file]: ./CHANGELOG.md
[stack-overflow]: https://stackoverflow.com/questions/tagged/aws-java-sdk
[gitter]: https://gitter.im/aws/aws-sdk-java
[features]: https://github.com/aws/aws-sdk-java/issues?q=is%3Aopen+is%3Aissue+label%3A%22feature-request%22
[support-center]: https://console.aws.amazon.com/support/
[console]: https://console.aws.amazon.com
[jackson-deserialization-gadget]: https://medium.com/@cowtowncoder/on-jackson-cves-dont-panic-here-is-what-you-need-to-know-54cd0d6e8062
[sdk-v2-dev-guide]: https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/welcome.html
[maintenance-policy]: https://docs.aws.amazon.com/credref/latest/refdocs/maint-policy.html
[version-matrix]: https://docs.aws.amazon.com/credref/latest/refdocs/version-support-matrix.html
[jep-break-encapsulation]: https://openjdk.org/jeps/261#Breaking-encapsulation
[jep-403]: https://openjdk.org/jeps/403
[aws-sdk-for-java-2x]: https://github.com/aws/aws-sdk-java-v2
