## Milestone 2
Our application "Gather" is a platform for arranging group study gatherings. Users can create posts and indicate the subject they want to study as well as the time and the place to study. Other users can search through these posts and find a study gathering that suits them. Users can also make comments on these post to discuss details about the gathering.

### Starting the application
in order to build the application with docker, run the following commands;
```javascript
docker build -t image_name .
```
```javascript
docker run -it image_name
```
```javascript
tr -d "\r" <run.sh> newrun.sh
```
```javascript
sh newrun.sh
```
## Features
- Creating posts
- Searching through posts
- Making comments on posts
- Registration (trivial feature)

### Posts
#### Open Endpoints
- Creating a new post: POST /posts/create
- Accessing all of the posts: GET /posts/access 
- Deleting an exsisting post: DELETE /posts/delete/{id}

#### Creating Posts
Use the following curl` command to create a new post:
```javascript
curl -X POST http://localhost:8080/posts/create -H "Content-Type: application/json" -d '{"user":{"name":"Molli"},"desc":"Planning to study algorithms at 8pm in the library. Who wants to join?", "date":"13th of May"}'
```
- Example success response:
`{"id":"64622451706993490d711ae1","createdAt":null,"user":{"id":null,"name":"Molli","email":null,"password":null},"desc":"Planning to study algorithms at 8pm in the library. Who wants to join?","date":"13th of May","comments":[]}% `
