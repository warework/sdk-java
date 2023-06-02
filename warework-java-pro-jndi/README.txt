----------------------------------------------------------------------
Warework ${project.name} (v${project.version}, Java Ed.)
----------------------------------------------------------------------

1. WHAT IS IT?

${project.description}

2. LIBRARY IDENTIFICATION

You can identify this library at Warework servers with the following 
information:

 - <language>: ${project.language.acronym}
 - <component-type>: ${project.library.type.name}
 - <library-name>: ${project.artifactId}
 - <library-version>: ${project.version}
 	 
3. SYSTEM REQUIREMENTS

To run this library minimally you need to have Java version ${project.language.version} or later.
 
4. DOWNLOAD URL

You can get a copy of this software library at: 

 http://repository.warework.com/maven/2/${project.directory.group}/${project.artifactId}/${project.version}/${project.artifactId}-${project.version}-bundle.zip

Maven users can link to this software library with the following code:

 <dependency>
   <groupId>${project.groupId}</groupId>
   <artifactId>${project.artifactId}</artifactId>
   <version>${project.version}</version>
 </dependency>
 
You also have to indicate in the pom.xml or the global settings file where is located the Maven
2 repository. Use the following code in any of those files to achieve this:

 <repositories>
   <repository>
     <id>warework-repository</id>
     <name>Warework Repository</name>
     <url>http://repository.warework.com/maven/2</url>
     <layout>default</layout>
     <releases>
       <enabled>true</enabled>
       <updatePolicy>always</updatePolicy>
       <checksumPolicy>fail</checksumPolicy>
     </releases>   
     <snapshots>
       <enabled>false</enabled>
       <updatePolicy>never</updatePolicy>
       <checksumPolicy>fail</checksumPolicy>
     </snapshots>
   </repository>
 </repositories>

5. LIBRARY FILES

The software library is included as part of the downloaded zip with a file named:

 - "${project.artifactId}-${project.version}.jar" 

6. DOCUMENTATION

The documentation available for this release is included:

 a. As part of the downloaded zip file with:
 
 		a.1. A software developer guide called:
 		
             user-guide.pdf
 				
 		a.2. The API documentation in HTML format (Javadoc) bundled in a 
 		     jar file called: 
 		     
             ${project.artifactId}-${project.version}-javadoc.jar
 		     
 b. At Warework servers with:
 
 		b.1. A software developer guide that can be found at:
             
             http://tutorial.warework.com/java/${project.artifactId}/${project.version}/index.html
             
             and
             
             http://guide.warework.com/java/${project.artifactId}/${project.artifactId}-${project.version}.pdf

		b.2. The API documentation in HTML format (Javadoc) that can be 
		     found at:
		     
             http://api.warework.com/java/${project.artifactId}/${project.version}/index.html
		     
7. RELEASE NOTES

The full list of changes is included:

 a. As part of the downloaded zip with a file called:

    CHANGELOG.txt
	 
 b. At Warework servers in the following location:

    http://log.warework.com/java/${project.artifactId}.txt

8. LICENSING 

The licenses that apply to this software library are included as part of the downloaded zip, in a 
folder named "license". Please review NOTICE.txt to find further information about licensing.