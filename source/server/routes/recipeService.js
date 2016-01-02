var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);

exports.addRecipe = function(req, res, next) {

	if (!req.session || !req.session.authorized)
        return res.status(403).send();

    if (!req.body.recipeName || !req.body.recipeDesc || !req.body.recipeTips ||!req.body.ingredients || !req.body.steps)
        return res.send('Missing parameters');

    var recipeName = req.body.recipeName;
    var recipeDesc = req.body.recipeDesc;
    var recipeTips = req.body.recipeTips;
    var ingredients = JSON.parse(req.body.ingredients);
    var steps = JSON.parse(req.body.steps);

    model.recipe.add(recipeName, recipeDesc, recipeTips, ingredients, steps, function(err, recipe) {

        if (err)
            return res.status(500).send('An error occured.');

        res.send('ok');
    });
};

function loadRecipeDetailsLoop(res, recipe, tablesToLoad, index) {

	if (tablesToLoad[index] === undefined) {

		res.send(recipe);
		return recipe;
	}

	if (recipe != null && recipe[tablesToLoad[index]+'s'] != null) {
		model[tablesToLoad[index]].fetchByIds(recipe[tablesToLoad[index]+'s'], function(err, result) {
			if (err != null)
				res.status(401).send('An error occured during the search.');
			else if (result == null)
				res.status(201).send('No '+tablesToLoad[index]+' found.');
			else {
				recipe[tablesToLoad[index]+'s'] = result;

				recipe = loadRecipeDetailsLoop(res, recipe, tablesToLoad, index + 1);

				return recipe;
			}
		});
	}
	else
		console.log('Recipe is null');
}

function loadRecipeDetails(res, recipe) {
	model.recipeIngredient.fetchByIds(recipe.recipeIngredients, function(err, recipeIngredients) {

		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (recipeIngredients == null)
			res.status(201).send('No ingredient found.');
		else
			recipe.recipeIngredients = recipeIngredients;
		model.ingredient.fetchByRecipeIngredients(recipe.recipeIngredients, function(err, recipeIngredients2) {

			if (err != null)
				res.status(401).send('An error occured during the search.');
			else if (recipeIngredients2 == null)
				res.status(201).send('No ingredient found.');
			else
				recipe.recipeIngredients = recipeIngredients2;

			tablesToLoad = ['step', 'equipment', 'user'];

			recipe = loadRecipeDetailsLoop(res, recipe, tablesToLoad, 0);
		});
	});
}

exports.findRecipeByName = function(req, res, next) {

	if (req.body.name === undefined) {
		res.status(401).send('No name given.');
		return;
	}

	var name = req.body.name;

	console.log('Get a recipe: ' + name);


	model.recipe.fetchByName(name, function(err, recipeRes) {
		var recipe = null;

		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (recipeRes == null)
			res.status(201).send('No recipe found.');
		else
			recipe = recipeRes;

		recipe = loadRecipeDetails(res, recipe);
	});
};

exports.findAllRecipes = function(req, res) {
	
	console.log('GetRecipes');
	model.recipe.fetchAll(0, 0, function(err, recipes) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (recipes.length == 0)
			res.status(201).send('No recipes found.');
		else
			res.send(recipes);
	});
};

exports.countAllRecipes = function(req, res) {
	
	console.log('Get Recipes Count');

	model.recipe.countAll(function(err, nbRecipes) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else
			res.status(200).send(nbRecipes+'');
	});
};

exports.findAllRecipesPaginate = function(req, res) {
	
	var limit = 10;
	var nbPage = 1;

	console.log(req.params);
	if (req.params.nbPage != undefined && req.params.nbPage > 0) {
		nbPage = req.params.nbPage;
	}

	model.recipe.fetchAll((nbPage - 1) * limit, limit, function(err, recipes) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (recipes.length == 0)
			res.status(201).send('No recipes found.');
		else {
			res.send(recipes);
			console.log(recipes);
		}
	});
};

// var findMatch = function(ingredients, id) {
// 	var res = [{}];
// 	var i = 0;
// 	var j = 0;
// 	var h = 0;

// 	while (ingredients[i]) {
// 		while (ingredients[i].recipes[j]) {
// 			if (ingredients[i].recipes[j] == id) {
// 				res[h] = ingredients[i];
// 				h += 1;
// 			}
// 			j += 1;
// 		}
// 		j = 0;
// 		i += 1;
// 	}
// 	return (res);
// }

// exports.getRecipeIngredient = function(req, res) {
// 	var id = req.params.id;

// 	db.collection('ingredients', function(err, collection) {
// 		collection.find().toArray(function(err, items) {
// 			res.send(findMatch(items, id));
// 		});
// 	});
// }

// var userRecipes = function(recipes, id) {
// 	var res = [{}];
// 	var i = 0;
// 	var h = 0;

// 	while (recipes[i]) {
// 		if (recipes[i].user == id){
// 			res[h] = recipes[i];
// 			h += 1;
// 		}
// 		i += 1;
// 	}
// 	return (res);
// }

// exports.getOwnerRecipes = function(req, res) {
// 	var id = req.params.id;
	
// 	db.collection('recipes', function(err, collection) {
// 		collection.find().toArray(function(err, items){
// 			res.send(userRecipes(items, id));
// 		});
// 	});
// }

// exports.addRecipe = function(req, res) {
// 	var recipe = req.body;
// 	console.log('Add recipe: ' + JSON.stringify(recipe));
// 	db.collection('recipes', function(err, collection) {
// 		collection.insert(recipe, {safe:true}, function(err, result) {
// 			if (err) {
// 				res.send({'error':'An error has occurred'});
// 			} else {
// 				console.log('Success: ' + JSON.stringify(result[0]));
// 				res.send(result[0]);
// 			}
// 		});
// 	});
// }



// exports.deleteRecipe = function(req, res) {
// 	var id = req.params.id;
// 	console.log('Deleting recipe: ' + id);
// }

var populateDB = function() {

		var defaultRecipe = 
		{
			"_id": new mongo.ObjectID("566d6ea4e1b9caac0dac79ee"),
			"name": "Pumpkin cheese bread",
			"description": "Pomelo spicy chicken wings can pour wine as a kind of snacks, can also be a kind of office work leisure snacks.Which is characterized by special pomelo spiced salt, grapefruit aromas and spicy chicken wings pepper aroma.This kind of hot even children can accept.",
			"image": "baicai.jpg",
			"tips": "Pomelo spiced salt in my recipes have separate method.This is the main condiment is Thai sweet chili sauce and pomelo spiced salt, the other can be adjusted according to personal taste.",
			"steps": [
				new mongo.ObjectID("566d6fe9e1b9caac0dac79f4"),
				new mongo.ObjectID("566d7022e1b9caac0dac79f5"),
				new mongo.ObjectID("566d7048e1b9caac0dac79f6"),
				new mongo.ObjectID("566d7091e1b9caac0dac79f7"),
				new mongo.ObjectID("566d70b9e1b9caac0dac79f8"),
				new mongo.ObjectID("566d70f9e1b9caac0dac79f9")
			],
			"users": [
				new mongo.ObjectID("4e54ed9f48dc5922c0094a32")
			],
			"recipeIngredients": [
				new mongo.ObjectID("566d7541e1b9caac0dac79fa"),
				new mongo.ObjectID("566d7568e1b9caac0dac79fb"),
				new mongo.ObjectID("566d75a3e1b9caac0dac79fc"),
				new mongo.ObjectID("56768efc4058c19e7a7d170c"),
				new mongo.ObjectID("56768f594058c19e7a7d170d"),
				new mongo.ObjectID("56768fe44058c19e7a7d170f"),
				new mongo.ObjectID("5676903d4058c19e7a7d1710"),
				new mongo.ObjectID("5676904c4058c19e7a7d1711"),
				new mongo.ObjectID("5676943c4058c19e7a7d1712") 
			],
		    "equipments": [
		        new mongo.ObjectID("5676583a92a6087f7461b010")
		    ]
		};


	db.collection('recipes', function(err, collection) {
		collection.insert(defaultRecipe, {safe:true}, function(err, result) {});
	});

};

commonService.prepareCheckCollection('recipes', populateDB);