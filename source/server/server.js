var TYPE = 'mongodb';

var
    express             = require('express'),
    query               = require('querystring'),
    usersService        = require('./routes/usersService'),
    ingredientsService  = require('./routes/ingredientsService'),
    recipeService       = require('./routes/recipeService'),
    oauth20             = require('./oauth20.js')(TYPE),
    model               = require('./model/' + TYPE),
    config				= require('./config'),
	session				= require('express-session')
	bodyParser			= require('body-parser');
	server				= express();
	
server.set('oauth2', oauth20);
server.set('views', path.join(__dirname, '../web/views'));
server.set('view engine', 'html');

//Middleware
server.use(session({ secret: 'nourriture', resave: false, saveUninitialized: false }));
server.use(bodyParser.urlencoded({extended: false}));
server.use(bodyParser.json());
server.use(oauth20.inject());

// Middleware. User authorization
function isUserAuthorized(req, res, next) {
    if (req.session.authorized) next();
    else {
        var params = req.query;
        params.backUrl = req.path;
        res.redirect('/login?' + query.stringify(params));
    }
}

// Routers
var routerAPI = express.Router();

function getRequirement(url, method) {

    if (config.acl[url] === undefined)
        return false;
    if (config.acl[url][method] === undefined)
        return false;
    return config.acl[url][method];
}

function checkAcl(req, res, next) {

    var url = req.path;
    var method = req.method;
    var requirement = getRequirement(url, method);
    console.log(req.connection.remoteAddress + ' access to ' + method + ' ' + url + ' (lvl' + requirement + ')');

    if (requirement === false)
        return res.status(404).send();
    if (requirement <= 0)
        return next();

    var token = req.headers.token;
    if (token === undefined)
        return res.status(401).send();

    model.user.getLvlFromToken(token, function(err, user_lvl) {

        if (user_lvl < requirement)
            return res.status(403).send();
        next();
    });
}

routerAPI.use(checkAcl);

// Define OAuth2 Authorization Endpoint
routerAPI.get('/authorization', isUserAuthorized, oauth20.controller.authorization, function(req, res) {
    res.render('authorization', { layout: false });
});
routerAPI.post('/authorization', isUserAuthorized, oauth20.controller.authorization);

// Define OAuth2 Token Endpoint
routerAPI.post('/token', oauth20.controller.token);

/***************************
*     START OAUTH SERVICE
****************************/

/*
** Get a login and a password, return a token
*/
routerAPI.post('/gettoken', function(req, res, next) {

    if (!req.body.username || !req.body.password)
        return res.send('Missing parameters');

    model.user.fetchByUsername(req.body.username, function(err, user) {

        if (err || !user)
            return res.status(401).send('User does not exits');

        model.user.checkPassword(user, req.body.password, function(err, valid) {

            if (err)
                return res.status(500).send('An error occured.');
            else if (!valid)
            	return res.status(401).send('Login failure.');

        	model.token.generateToken(user, function(err, token) {
        		
        		if (err)
        			return res.status(500).send('An error occured.');
       			res.send(token);
        	});
        });
    });
});

server.get('/index', function (req, res) {
   res.render('login');
});

//login
server.post('/login', function(req, res, next) {

    var backUrl = req.query.backUrl ? req.query.backUrl : '/';
    delete(req.query.backUrl);
    backUrl += backUrl.indexOf('?') > -1 ? '&' : '?';
    backUrl += query.stringify(req.query);

    // Already logged in
    if (req.session.authorized)
    	res.redirect(backUrl);
    // Trying to log in
    else if (req.body.username && req.body.password) {
        model.oauth2.user.fetchByUsername(req.body.username, function(err, user) {
            if (err)
            	next(err);
            else {
                model.oauth2.user.checkPassword(user, req.body.password, function(err, valid) {
                    if (err)
                    	next(err);
                    else if (!valid)
                    	res.redirect(req.url);
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
    else
    	res.redirect(req.url);
});

// Some secure method
routerAPI.get('/secure', oauth20.middleware.bearer, function(req, res) {
    if (!req.oauth2.accessToken) return res.status(403).send('Forbidden');
    if (!req.oauth2.accessToken.userId) return res.status(403).send('Forbidden');
    res.send('Hi! Dear user ' + req.oauth2.accessToken.userId + '!');
});

// Some secure client method
routerAPI.get('/client', oauth20.middleware.bearer, function(req, res) {
    if (!req.oauth2.accessToken) return res.status(403).send('Forbidden');
    res.send('Hi! Dear client ' + req.oauth2.accessToken.clientId + '!');
});

/***************************
*     END OAUTH SERVICE
****************************/

/***************************
*     START API USERS SERVICE
****************************/

//Get the list of all users in the DB
routerAPI.get('/users', usersService.findAllUsers);
//Get a user by ID
routerAPI.get('/users/:id', usersService.findUserById);
//Add a user
routerAPI.post('/users', usersService.addUser);
//Delete a user
routerAPI.delete('/users', usersService.deleteUser);


/***************************
*     END USERS SERVICE
****************************/

/***************************
*     START INGREDIENTS SERVICE
****************************/

//Get the list of all ingredients in the DB
routerAPI.get('/ingredients', ingredientsService.findAllIngredients);
//Get a ingredient by ID
routerAPI.get('/ingredients/:id', ingredientsService.findIngredientById);
//Add a ingredient
routerAPI.post('/ingredients', ingredientsService.addIngredient);
//Delete a ingredient
routerAPI.delete('/ingredients', ingredientsService.deleteIngredient);

routerAPI.get('/ingredientrecipes/:id', ingredientsService.getIngredientRecipe);

/***************************
*     END INGREDIENTS SERVICE
****************************/


/***************************
*     START RECIPES SERVICE
****************************/

routerAPI.get('/recipes', recipeService.findAllRecipes);
routerAPI.get('/recipeingredient/:id', recipeService.getRecipeIngredient);
//routerAPI.get('/getrecipeowner/:id', recipeService.getRecipeOwner);

/***************************
*     END RECIPES SERVICE
****************************/


server.use('/api', routerAPI);

var start = module.exports.start = function() {
    
    server.listen(config.server.port, config.server.host, function(err) {
        if (err)
            console.error(err);
        else
            console.log('Server started at ' + config.server.host + ':' + config.server.port);
    });
};

module.exports = server;

if (require.main == module) {
    start();
}