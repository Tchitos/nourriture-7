var express = require('express'),
    usersService = require('./routes/usersService'),
    ingredientsService = require('./routes/ingredientsService');

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

/***************************
*     START INGREDIENTS SERVICE
****************************/

//Get the list of all ingredients in the DB
app.get('/ingredients', ingredientsService.findAllIngredients);
//Get a ingredient by ID
app.get('/ingredients/:id', ingredientsService.findIngredientById);
//Add a ingredient
app.post('/ingredients', ingredientsService.addIngredient);
//Delete a ingredient
app.delete('/ingredients', ingredientsService.deleteIngredient);

/***************************
*     END INGREDIENTS SERVICE
****************************/

app.listen(3000);
console.log('Listening on port 3000...');