#Spring Cloud Example

Full example with Spring Cloud:

* Eureka Discovery Server
* Spring Config Server
* Filesystem Explorer Service
* MongoDb Service


***

See [Spring Cloud](http://projects.spring.io/spring-cloud/) for oficial recefrence

for the example we use this Spring Cloud version:
```xml
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>Brixton.SR5</version>
```

In this example we build a cloud system with Spring cloud tools, we need 2 dedicated servers, ***discovery server (Eureka)*** and ***Config server*** .We build a 2 services too, ***Filesystem explorer service*** and ***MongoDb service***.We need all this pieces for register services and serve external configurations.
We need to have been installed [MongoDb](https://www.mongodb.com/download-center#community) for run this example complete,but if you are not interested in MongoDb skills,simply, dont run it.

## Discovery Server (Eureka)

Spring uses Netflix tecnology for service discovery, called Eureka Server.
This server run under Spring boot, and its include in Spring Cloud Packages.

pom.xml:

``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.sh</groupId>
  <artifactId>EurekaDiscoverServer</artifactId>
  <version>0.0.1</version>
  
    
   <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.1.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
		</dependency>
		
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Brixton.SR5</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```
We have to include ***spring-cloud-starter-eureka-server*** package and ***spring-boot-starter-parent*** for use Eureka server.

***

In this simple example only include the annotations 
```java
@EnableEurekaServer
@SpringBootApplication
```
And only with that we are created Eureka server:

```java
package sh.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoverServer {
	
	public static void main(String[] args)
	{
		SpringApplication.run(DiscoverServer.class, args);
	}
	
}
```

We have to configure it in src/main/resources/application.properties file, by default eureka try to autoregister himself,and we dont want to do that.We configure the port too:
```properties
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

logging.level.com.netflix.eureka=OFF
logging.level.com.netflix.discovery=OFF
```
With all configured, we can build the project and execute the jar (java -jar EurekaServer.jar).
Eureka server is listening on port 8761.We can access to info in [localhost:8761](http://localhost:8761)

## Spring Config Server

Spring Cloud is centralized way to serve properties to services.We have to work with repository, in this case I used git for store properties.This server is a Spring boot application too.

pom.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>CloudConfigServer</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.4.1.RELEASE</version>
        <relativePath /> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Brixton.SR5</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>


</project>

``` 

We include ***spring-cloud-config-server*** for use it as cloud config server.

In this project we start for the configuration file, src/main/resources/application.properties

```properties
server.port=8888
spring.cloud.config.server.git.uri=/path/to/your/git/repository
```
In properties file we check 2 entries, we listen on port 8888 and our properties repository will be located in uri entry.
This is a local repository, we have to create in this path a git repository with `git init`.
Will leave the repository empty for now,we will back later.

We have configured the Config server, now we have to write the Spring boot application:

```java
package com.sh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ConfigApp.class, args);
    }
}

```

Very simple,only adding `@EnableConfigServer` we have created our config server,listening on port 8888 and with properties repository to store services configurations.
Now we can build the project and execute the jar.

## FileSystem Explorer Service

This service is a filesystem explorer, as FTP, by properties u have to pass the root folder.
Its a Spring Boot application and will register in the Eureka server and properties file will be server by the Spring Config server.
For this service we have to prepare our server properties.I included this properties in one folder called properties in project root and check the file `files-explorer.properties`:

```properties
server.port=8040
rootPath=/your/favourite/root
```
Modify this file with ur root folder and save it in the git repository previously created and commit,very important, commit, not only copy it.
Now we have stored one config in Cloud Config server, but we need to identify our application.
We called to properties ***files-explorer.properties*** then, we go to call our microservice ***files-explorer*** and we have to do it before try to read the properties in runtime, for this purpose we have to create `src/main/resources/bootstrap.properties`. This is called bootstrap and no application because have to get it before:

```properties
spring.application.name=files-explorer
spring.cloud.config.uri=http://localhost:8888
```


We can see why this file have to read before application, this properties are mandatory for:

1. Identify and register application in Eureka with given name
2. Provide the Config Cloud server url.Configuration properties files have to be called with same name of application name

This service have a bit more of code and packages but we want to see 2 behaviours, how tell to microservice that have to be discovered for Eureka server:

```java
package com.sh.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.sh.ctrl")
@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class ConfigApp {
		
}

```
We tell to Spring that this microservice is a Eureka client,its observable and can be discovered.

Second behaviour is get configuration file from Config Cloud server, for that only show the Spring configuration file, that was made by bootstrap.properties and the filename matching with our application name, but if we want to propagate and refresh the configuration with any change of the server properties we have to annotate our methos controllers
```java
@RefreshScope
@Controller
public class FilesController 
{
	@Value("${rootPath}")
	private String rootPath;

[...]
```
With @RefreshScope, the @Value variable refresh the value sent by config properties,but we have to call to POST method /refresh (See the reference for complete description [Spring Cloud Reference](http://cloud.spring.io/spring-cloud-static/docs/1.0.x/spring-cloud.html))

we can build and run our application but we can be sure that Eureka server and Config Server are previously up.

## MongoDbService

For make the example a bit more complete, we go to create a service that connects with MongoDb through Spring-data and be explained with more details,we go to see the project structure:

![Tree](http://shoecillo.drivehq.com/images/MongoService-Tree.png)

pom.xml:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.sh</groupId>
  <artifactId>MongoService</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  
   <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.4.1.RELEASE</version>
   
</parent>
<dependencies>
    <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
			<scope>test</scope>
		</dependency>
    <dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-io</artifactId>
			<version>1.3.2</version>
		</dependency>
</dependencies>

<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Brixton.SR5</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>


<build>
	<finalName>MongoService</finalName>
	<plugins>
		<plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
	</plugins>
</build>  
  
</project>

```

Here we include `spring-boot-starter-data-mongodb` and eureka,spring boot, actuator is recommended for operations like health, start and stop etc.

We go to create 2 services,one that obtain the first name from mongoDb from username and other service that returns the count of collection.
For that we go to do step by step, first of all, the configuration.We want to make this application visible in our cloud, then we have to create a src/main/resources/bootstrap.properties:


```properties
spring.application.name=mongo-service
spring.cloud.config.uri=http://localhost:8888
```

Here we give a application name and from what config server we want to get the configuration.
Now we want to centralize the MongoDb connection in external properties, for that, go to the root project,and in properties folder we find the file mongo-service.properties, we have to create and commit it in the git repository creted for store properties,as same way that we did it in the other service, and the content is:

```properties

server.port=8050
mongo.db=mydb
mongo.host=localhost
mongo.port=27017

```

As always, the server.port, that is a spring property and automatically set the port, and later all connection properties for MongoDb,Db name,host and port.
Once commited in the repository, we can start to configure Spring:

```java
package com.sh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@ComponentScan("com.sh")
@EnableMongoRepositories("com.sh.repository")
@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class Config 
{
	@Value("${mongo.db}")
	private String db;
	
	@Value("${mongo.host}")
	private String host;
	
	@Value("${mongo.port}")
	private String port;
	
	
	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		
		return new SimpleMongoDbFactory(new MongoClient(host, Integer.parseInt(port)), db);
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

	}	
}
```

Here we annotate the configuration class with @EnableEurekaClient for make it visible for Eureka.
Other times the config file is empty because are basic examples, but here we have to configure 2 aspects for MongoDb:

1. `@EnableMongoRepositories("com.sh.repository")` Here tell that Mongo repositories can be found in this package
2. Configure 2 beans, `MongoDbFactory` and `MongoTemplate`,here setting host port and Db for make a connection.

At this point we have the application configured,but not implemented yet.


***
 
I include a Mongo dump for this example, is in src/main/resources/mydb.zip, extract in one folder and the execute 
`mongorestore -d mydb <directory_backup>` where <directory_backup> is where we unzipped mydb.zip for import collections.

The collection is very simple,have this structure:

```json
{
    "username" : "",
    "firstName" : "",
    "lastName" : ""
}
```

***

Here we have to build the Entity in first time:

```java
package com.sh.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class UserEntity 
{
   @Id
   public String id;
	
   public String username;
	
   public String firstName;
	
   public String lastName;

	public UserEntity() {
		
	}
	
	public UserEntity(String userName,String firstName,String lastName) 
	{
		this.username = userName;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	 @Override
	    public String toString() {
	        return String.format(
	                "Customer[id=%s, username='%s',firstName='%s', lastName='%s']",
	                id,username, firstName, lastName);
	    }

}

```

Here not getters and setters, public fields and override toString() function for pretty format.
`@Document(collection="users")` link this object to mongoDb collection,in this case `users`
id field is mandatory,but is filled by mongo with a UUID, anyway we have to create it and annotate it wit `@Id` 
Important, the name of fields are case sensitive and have to match with the fields names of the collection, if not, spring data throws an exception.

Now we have to build the MongoRepository, and interface extended to MongoRepository with 2 types, the entity and String.
We have to do one repository for each collection where we want to operate.

```java
package com.sh.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sh.entity.UserEntity;

public interface UsersRepository extends MongoRepository<UserEntity, String>  
{
	public UserEntity findByUsername(String username);
	
}

```

Only we had created one method, find a user by username.Spring data with mongo repositories are not required to implements this interface,Spring do it for you, now we will see how to use this interface.

´´´java
package com.sh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.entity.UserEntity;
import com.sh.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository repo;
	
	public UserEntity getUser(String user)
	{
		return repo.findByUsername(user);
	}
	
	public List<UserEntity> getUserList()
	{
		return repo.findAll();
	}
}
´´´

We can see 2 methods, in one we use findbyUsername method that we declared in repository, and other method that returns all results of the collection.

The controller is a usual RestController where i use the service class to call Mongo and return results.

## Conclusion
With all this we are created a discovery server, a configuration server and 2 services discovered by Eureka and with centralized configuration.
Well Done!!
