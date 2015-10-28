var TYPE = 'mongodb';

var
    express				= require('express'),
    config				= require('./config.js'),
	query				= require('querystring'),
    usersService		= require('./routes/usersService'),
    ingredientsService	= require('./routes/ingredientsService'),
	oauth20 			= require('./oauth20.js')(TYPE),
	model				= require('./model/' + TYPE)
	session				= require('express-session')
	bodyParser			= require('body-parser');
	server				= express();

server.set('oauth2', oauth20);

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

/***************************
*     END INGREDIENTS SERVICE
****************************/

server.use('/api', routerAPI);

server.listen(config.server.port, config.server.host, function(err) {
    if (err)
        console.error(err);
    else
        console.log('Server started at ' + config.server.host + ':' + config.server.port);
});