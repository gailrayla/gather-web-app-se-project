## Milestone 1

### Table of Contents
* About the Project
* Getting Started
* Prerequisites
* Installation
* Usage
* Contributing
* Contact

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
docker build -t image_name /path/to/Dockerfile
```

**Step 3**: Run docker image

```javascript
docker run -it image_name
```


### Usage
In order to run our application we can either choose `Run` option from the IDE we are using or we can just run the the below command;
```javascript
./mvnw clean spring-boot:run
```

After starting the application, running the below code 
```javascript
curl -v localhost:8080/employees
```
