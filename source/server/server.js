var TYPE = 'mongodb';

var express = require('express'),
	query = require('querystring'),
    usersService = require('./routes/usersService'),
    ingredientsService = require('./routes/ingredientsService'),
	oauth20 = require('./oauth20.js')(TYPE),
	model = require('./model/' + TYPE)
	session = require('express-session');

var server = express();

server.set('oauth2', oauth20);

server.configure(function () {
    server.use(express.logger('dev'));
    server.use(express.bodyParser());
    server.use(session({ secret: 'nourriture', resave: false, saveUninitialized: false }));
});

/***************************
*     START OAUTH SERVICE
****************************/

//login
server.post('/login', function(req, res, next) {
    var backUrl = req.query.backUrl ? req.query.backUrl : '/';
    delete(req.query.backUrl);
    backUrl += backUrl.indexOf('?') > -1 ? '&' : '?';
    backUrl += query.stringify(req.query);

    // Already logged in
    if (req.session.authorized) res.redirect(backUrl);
    // Trying to log in
    else if (req.body.username && req.body.password) {
        model.oauth2.user.fetchByUsername(req.body.username, function(err, user) {
            if (err) next(err);
            else {
                model.oauth2.user.checkPassword(user, req.body.password, function(err, valid) {
                    if (err) next(err);
                    else if (!valid) res.redirect(req.url);
                    else {
                        req.session.user = user;
                        req.session.authorized = true;
                        res.redirect(backUrl);
                    }
                });
            }
        });
    }
    // Please login
    else res.redirect(req.url);
});

/***************************
*     END OAUTH SERVICE
****************************/

/***************************
*     START USERS SERVICE
****************************/

//Get the list of all users in the DB
server.get('/users', usersService.findAllUsers);
//Get a user by ID
server.get('/users/:id', usersService.findUserById);
//Add a user
server.post('/users', usersService.addUser);
//Delete a user
server.delete('/users', usersService.deleteUser);

/***************************
*     END USERS SERVICE
****************************/

/***************************
*     START INGREDIENTS SERVICE
****************************/

//Get the list of all ingredients in the DB
server.get('/ingredients', ingredientsService.findAllIngredients);
//Get a ingredient by ID
server.get('/ingredients/:id', ingredientsService.findIngredientById);
//Add a ingredient
server.post('/ingredients', ingredientsService.addIngredient);
//Delete a ingredient
server.delete('/ingredients', ingredientsService.deleteIngredient);

/***************************
*     END INGREDIENTS SERVICE
****************************/

server.listen(3000);
console.log('Listening on port 3000...');