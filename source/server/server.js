var TYPE = 'mongodb';

var
	express						= require('express'),
	query						= require('querystring'),
	usersService				= require('./routes/usersService'),
	ingredientsService			= require('./routes/ingredientsService'),
	recipeIngredientsService	= require('./routes/recipeIngredientsService'),
	recipeService				= require('./routes/recipeService'),
	typeService					= require('./routes/typeService'),
	subtypeService				= require('./routes/subtypeService'),
	imageService				= require('./routes/imageService'),
	nutritionService			= require('./routes/nutritionService'),
	oauth20						= require('./oauth20.js')(TYPE),
	model						= require('./model/' + TYPE),
	config						= require('./config'),
	session						= require('express-session'),
	bodyParser					= require('body-parser'),
	multer						= require('multer'),
	upload						= multer({dest: '/tmp/nourriture'}),
	server						= express();

server.set('oauth2', oauth20);

//Middleware
server.use(session({ secret: 'nourriture', resave: false, saveUninitialized: false }));
server.use(bodyParser.urlencoded({extended: false}));
server.use(bodyParser.json());
server.use(oauth20.inject());
server.use(express.static('../web'));

server.use(function (req, res, next) {

	// res.setHeader('Access-Control-Allow-Origin', config.server.origin);
	// res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
	// res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
	// res.setHeader('Access-Control-Allow-Credentials', true);
	next();
});

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
//plop
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
server.get('/logintest', usersService.logintest);
server.post('/login', usersService.login);
server.post('/register', usersService.register);
server.get('/stilllogged', usersService.stilllogged);
server.get('/logout', usersService.logout);

server.post('/ingredient/add', upload.single('photo'), ingredientsService.addIngredient)
server.post('/getIngredientsBySearch', ingredientsService.findIngredientsBySearch)
server.get('/getIngredients', ingredientsService.findAllIngredients)

server.get('/getTypesDetails', typeService.findAllTypesDetails);

server.post('/recipe/add', upload.any(), recipeService.addRecipe);
server.post('/recipe/delete', recipeService.deleteRecipe);
server.get('/getRecipes', recipeService.findAllRecipes);
server.get('/getRecipesCount', recipeService.countAllRecipes);
server.get('/getRecipesByPage/:nbPage?', recipeService.findAllRecipesPaginate);
server.get('/getMyRecipes', recipeService.findMyRecipes);
server.post('/getRecipeByName', recipeService.findRecipeByName);
server.post('/getRecipesByIngredient', recipeService.findRecipesByIngredient);
server.post('/getRecipesBySearch', recipeService.findRecipesBySearch);
server.post('/getRecipesBySubtype', recipeService.findRecipesBySubtype);

server.get('/getNutritions', nutritionService.findAllNutritions);
server.post('/getNutritionByName', nutritionService.findNutritionByName);

server.get('/image/:imageId', imageService.findImageById);

// /***************************
// *     END OAUTH SERVICE
// ****************************/

// /***************************
// *     START API USERS SERVICE
// ****************************/

// //Get the list of all users in the DB
// routerAPI.get('/users', usersService.findAllUsers);
// //Get a user by ID
// routerAPI.get('/users/:id', usersService.findUserById);
// //Add a user
// routerAPI.post('/users', usersService.addUser);
// //Delete a user
// routerAPI.delete('/users', usersService.deleteUser);


// /***************************
// *     END USERS SERVICE
// ****************************/

// /***************************
// *     START INGREDIENTS SERVICE
// ****************************/

// //Get the list of all ingredients in the DB
// routerAPI.get('/ingredients', ingredientsService.findAllIngredients);
// //Get a ingredient by ID
// routerAPI.get('/ingredients/:id', ingredientsService.findIngredientById);
// //Add a ingredient
// routerAPI.post('/ingredients', ingredientsService.addIngredient);
// //Delete a ingredient
// //routerAPI.delete('/ingredients', ingredientsService.deleteIngredient);

// //Return all recipe wich contain the ingredient (id)
// routerAPI.get('/ingredientrecipes/:id', ingredientsService.getIngredientRecipe);

// /***************************
// *     END INGREDIENTS SERVICE
// ****************************/


// /***************************
// *     START RECIPES SERVICE
// ****************************/

// //Get the list of all recipes in the DB
// routerAPI.get('/recipes', recipeService.findAllRecipes);
// //Get a recipe by ID
// routerAPI.get('/recipes/:id', recipeService.findRecipeById);
// //Add a recipe
// routerAPI.post('/recipes', recipeService.addRecipe);
// //Return all ingredients for the recipe (id)
// routerAPI.get('/recipeingredient/:id', recipeService.getRecipeIngredient);
// //Get all the recipes of the user (id)
// routerAPI.get('/getownerrecipes/:id', recipeService.getOwnerRecipes);

// /***************************
// *     END RECIPES SERVICE
// ****************************/


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