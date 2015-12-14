var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);

exports.findRecipeByName = function(req, res) {
	var name = req.body.name;

	console.log('Get a recipe: ' + name);

	model.recipe.fetchByName(name, function(err, recipe) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (recipe == null)
			res.status(201).send('No recipe found.');
		else if (recipe.steps != null) {
			model.step.fetchByIds(recipe.steps, function(err, steps) {
				if (err != null)
					res.status(401).send('An error occured during the search.');
				else if (steps == null)
					res.status(201).send('No steps found.');
				else {
					recipe.steps = steps;
					console.log(steps);
					res.send(recipe);
				}
			});
		}
	});
};

exports.findAllRecipes = function(req, res) {
	
	model.recipe.fetchAll(function(err, recipes) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (recipes.length == 0)
			res.status(201).send('No recipes found.');
		else
			res.send(recipes);
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
				"566d6fe9e1b9caac0dac79f4",
				"566d7022e1b9caac0dac79f5",
				"566d7048e1b9caac0dac79f6",
				"566d7091e1b9caac0dac79f7",
				"566d70b9e1b9caac0dac79f8",
				"566d70f9e1b9caac0dac79f9"
			],
			"users": [
				"4e54ed9f48dc5922c0094a32"
			],
			"recipeIngredients": [
				"566d7541e1b9caac0dac79fa",
				"566d7541e1b9caac0dac79fb",
				"566d7541e1b9caac0dac79fc"
			]
		};


	db.collection('recipes', function(err, collection) {
		collection.insert(defaultRecipe, {safe:true}, function(err, result) {});
	});

};

commonService.prepareCheckCollection('recipes', populateDB);