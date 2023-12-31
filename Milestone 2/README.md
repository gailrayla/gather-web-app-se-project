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
- Posts
- Searching 
- Commenting
- Registration (trivial feature)

## Posts
#### Open Endpoints
- Creating a new post: POST /posts/create
- Accessing all of the posts: GET /posts/access 
- Deleting an exsisting post: DELETE /posts/delete/{id}

#### Creating Posts
Use the following `curl` command to create an example new post:
```javascript
curl -X POST http://localhost:8080/posts/create -H "Content-Type: application/json" -d '{"user":{"name":"Molli"},"desc":"Planning to study algorithms at 8pm in the library. Who wants to join?", "date":"13th of May"}'
```
- Example success response:
```javascript
{"id":"6462264e18cd9129ef52ad4a","createdAt":"2023-05-15T21:32:14.615208","user":{"id":null,"name":"Molli","email":null,"password":null},"desc":"Planning to study algorithms at 8pm in the library. Who wants to join?","date":"13th of May","comments":[]}% 
```

#### Accessing All Posts
Use the following `curl` command to display all of the posts that has been created:
```javascript
curl -X GET http://localhost:8080/posts/access
```
- Example success response:
```javascript
[{"id":"6462264e18cd9129ef52ad4a","createdAt":"2023-05-15T21:32:14.615","user":{"id":null,"name":"Molli","email":null,"password":null},"desc":"Planning to study algorithms at 8pm in the library. Who wants to join?","date":"13th of May","comments":[]},{"id":"646226b718cd9129ef52ad4b","createdAt":"2023-05-15T21:33:59.55","user":{"id":null,"name":"Jane","email":null,"password":null},"desc":"Planning to study computer systems at 9pm in the library. Who wants to join?","date":"15th of May","comments":[]},{"id":"646226fa18cd9129ef52ad4c","createdAt":"2023-05-15T21:35:06.967","user":{"id":null,"name":"Tom","email":null,"password":null},"desc":"Planning to study for Theory of Computation at 5pm at the first floor in the library.","date":"21st of May","comments":[]},{"id":"6462286c18cd9129ef52ad4e","createdAt":"2023-05-15T21:41:16.552955","user":{"id":null,"name":"Hannah","email":null,"password":null},"desc":"Who wants to study discrete math? I am available whole day.","date":"21st of May","comments":[]}%
```

#### Deleting a Post
- Required URL Params:
id=[string]

Use the following `curl` command to delete a specific post using the uniquely generated id for that post:
```javascript
curl -X DELETE http://localhost:8080/posts/delete/{id}
```
Example with an already existing id:
```javascript
curl -X DELETE http://localhost:8080/posts/delete/6462286c18cd9129ef52ad4e
```

## Searching
#### Open Endpoints
- Searching through posts: GET /posts/{text}

#### Searching Through Posts
Use the following `curl` command to search through all of the posts using a keyword:
```javascript
curl -X GET http://localhost:8080/posts/{text}
```
*Replace {text} with the word you want to search*

Example:
```javascript
curl -X GET http://localhost:8080/posts/systems
```
- Example success response:
```javascript
[{"id":"646226b718cd9129ef52ad4b","createdAt":"2023-05-15T21:33:59.55","user":{"id":null,"name":"Jane","email":null,"password":null},"desc":"Planning to study computer systems at 9pm in the library. Who wants to join?","date":"15th of May","comments":[]}]% 
```

## Commenting
#### Open Endpoints
- Adding a new comment to a post: POST /posts/{postId}/comments
- Deleting a comment: DELETE /comments/{commentId}
- Accessing the comments of a post: GET /posts/{postId}/comments

#### Adding a New Comment
Use the following `curl` command to add a new comment to a specific post:
```javascript
curl -X POST -H "Content-Type: application/json" -d '{
  "user": {
    "id": "USER_ID"   // Replace USER_ID with the actual user ID obtained from the previous response
  },
  "content": "This is a comment on the post"
}' http://localhost:8080/posts/POST_ID/comments
```

Example command with already existing user and post id:
```javascript
curl -X POST -H "Content-Type: application/json" -d '{
  "user": {
    "id": "646211be25bbeb4964c3f136"
  },
  "content": "i am interested"
}' http://localhost:8080/posts/64622f5e4a4ed21fe7f50871/comments
```
#### Deleting a Comment
Use the following `curl` command to delete a specific comment:
```javascript
curl -X DELETE http://localhost:8080/comments/{commentId}
```

Example command with already existing comment id:
```javascript
curl -X DELETE http://localhost:8080/comments/6462370f825da32475e89798
```

#### Accessing the Comments 
Use the following `curl` command to access the comments under a specific post:
```javascript
curl -X GET http://localhost:8080/posts/{postId}/comments
```
Example command with a post that already has comments:
```javascript
curl -X GET http://localhost:8080/posts/64622f5e4a4ed21fe7f50871/comments
```

## Registration
#### Open Endpoints
- Creating a new user: POST /users/signup 
- Accessing a user: GET /users/access/{id}
- Updating an exsisting user: PUT /users/update/{id}
- Deleting an exsisting user: DELETE /users/delete/{id}

#### Creating a New User
Use the following `curl` command to create a new user:
```javascript
curl -X POST http://localhost:8080/users/signup -H "Content-Type: application/json" -d '{"name":"May","email":"mayii@example.com","password":"password23"}'
```
#### Accessing a user
Use the following `curl` command to access the data of a registered user:
```javascript
curl -X GET http://localhost:8080/users/access/{id}
```
Example command with a already exsisting user:
```javascript
curl -X GET http://localhost:8080/users/access/{id}
```
#### Updating a User
Use the following `curl` command to update the data of a registered user:
```javascript
curl -X PUT http://localhost:8080/users/update/{id} -H "Content-Type: application/json" -d '{"name":"Jen","email":"jennie@gmail.com","password":"password23"}'    
```
Example command with a already exsisting user:
```javascript
curl -X PUT http://localhost:8080/users/update/646211f2213b14481103acb8 -H "Content-Type: application/json" -d '{"name":"Jen","email":"jennie@gmail.com","password":"password23"}'    
```
#### Deleting a User
Use the following `curl` command to delete a user:
```javascript
curl -X DELETE http://localhost:8080/posts/delete/{id}
```
