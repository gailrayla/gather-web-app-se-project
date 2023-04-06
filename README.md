## Milestone 1

### Table of Contents
* About the Project
* Getting Started
* Prerequisites
* Installation
* Usage

### About the milestone
In this milestone, we built the development environment for our web app using Docker, Rest API, Maven, and MongoDB. By utilizing Docker, we packaged our applications along with their dependencies into a single container. With Rest API, we created the web artchitecture of our web app. Moreover, we simplified our build process by using Maven. For the database management system, we selected MongoDB due to its great performance potential.

### Getting Started
To get started with the project, follow these steps.

### Prerequisites
* Your local host should have downloaded
* Docker 
* Any IDE including Visual Studio, Eclipse, or PyCharm ( preferably IntelliJ )
* Maven extension
* MongoDB

### Installation

**Step 1**: Clone the git repository 

```javascript
git clone https://github.com/gailrayla/CSE346-Group-20.git
```

**Step 2**: Build docker 

```javascript
docker build
```
```javascript
docker build -t image_name /path/to/Dockerfile
```

**Step 3**: Run docker image

```javascript
docker run -it image_name
```


### Usage
#### Milestone Part II
In order to run our application we can either choose `Run` option from the IDE we are using or we can just run the the below command;
```javascript
./mvnw clean spring-boot:run
```

After starting the application, we can use `curl` tool to test our application.
Below code shows what the server offers;
```javascript
curl http://localhost:8080
```
We can use the people link to see the `Person` records.
```javascript
curl http://localhost:8080/people
```
And then, use the below command to create a `Person`.
```javascript
curl -i -X POST -H "Content-Type:application/json" -d "{  \"firstName\" : \"Frodo\",  \"lastName\" : \"Baggins\" }" http://localhost:8080/people
```

#### Milestone Part III
To begin with Part III, MongoDB is added as a dependency in the pom.xml file first. Then, a MongoDB Database is linked and configured to the Spring Boot application through adding the hostame, port, and database name to the application.properties under the folder in the resources folder in the main folder in src directory. After this, a MongoDB configuratio class that extends AbstractMongoConfigurationSupport and overrides the mongoClient() is created. From this, we create a spring data repository interface for User data called ‘UserRepository’ that extends MongoRepository and defines methods for accessing the MongoDB collection.

After Pulling the **MongoDB** image on **Docker**, we start with running the **MongoDB container** with the below commands;

```javascript
docker pull mongo
```
```javascript
docker run --name our-first-mongo -d mongo
```

