var express = require('express'),
    usersService = require('./routes/usersService');

var app = express();

app.configure(function () {
    app.use(express.logger('dev'));
    app.use(express.bodyParser());
});

/***************************
*     START USERS SERVICE
****************************/

//Get the list of all users in the DB
app.get('/users', usersService.findAllUsers);
//Get a user by ID
app.get('/users/:id', usersService.findUserById);
//Add a user
app.post('/users', usersService.addUser);
//Delete a user
app.delete('/users', usersService.deleteUser);

/***************************
*     END USERS SERVICE
****************************/

app.listen(3000);
console.log('Listening on port 3000...');