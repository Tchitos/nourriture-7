var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);
var	fs				= require('fs-extra');
var getPath			= require('path');

/*
** Add a recipe
** POST fields :
** recipeName: string
** recipeDesc: string
** recipeTips: string
** equipements: JSON Array : [{name: string}]
** ingredients: JSON Array : [{ingredient: id, name: string, mandatory: boolean quantity: string}]
** steps: JSON Array : [{level: id, text: string}]
*/
exports.addRecipe = function(req, res, next) {

	if (!req.session || !req.session.authorized)
		return res.status(403).send();

	if (!req.body.recipeName || !req.body.recipeDesc || !req.body.recipeTips || !req.body.equipements || !req.body.ingredients || !req.body.steps)
		return res.send('Missing parameters');

	var recipePhoto = null;
	var recipeName = req.body.recipeName;
	var recipeDesc = req.body.recipeDesc;
	var recipeTips = req.body.recipeTips;
	var equipements = JSON.parse(req.body.equipements);
	var ingredients = JSON.parse(req.body.ingredients);
	var steps = JSON.parse(req.body.steps);

	addImagesRecurs(req.files, {}, 0, function(err, imageIds) {

		console.log(imageIds);

		for (var key in imageIds) {
			if (key == 'recipePhoto')
				recipePhoto = imageIds[key];
			else {
				console.log('tmp');
				var tmp = key.split('][step');
				if (!tmp)
					continue;

				level = tmp[1].substr(0, 1);

				if (!steps[level-1])
					continue;

				steps[level-1]['image'] = imageIds[key];
			}
		}

		model.recipe.add(req.session.user._id, recipeName, recipePhoto, recipeDesc, recipeTips, equipements, ingredients, steps, function(err) {

			if (err)
				return res.status(500).send('An error occured.');
			res.send('ok');
		});
	});
};

function addImagesRecurs(files, filesId, index, cb) {

	var path = './uploads/'+files[index].filename;
	path = getPath.resolve(process.cwd(), path);

	fs.move(files[index].path, path, function (err) {

		if (err)
			cb(err);

		model.image.add(files[index].originalname,
						path,
						files[index].mimetype,
						files[index].size,
						function(err, image) {

			if (err)
				cb(err);

			filesId[files[index].fieldname] = image._id;

			if (files[index + 1] === undefined)
				cb(null, filesId);
			else
				addImagesRecurs(files, filesId, index + 1, cb);
		});
	});
}

exports.deleteRecipe = function(req, res, next) {

	if (!req.session || !req.session.authorized)
        return res.status(403).send();

    if (!req.body.name)
    	return res.send('Missing parameters');

    var name = req.body.name;

    model.recipe.delete(name, function(err) {

    	if (err)
            return res.status(500).send('An error occured.');
        res.send('ok');
    });
}

exports.findMyRecipes = function(req, res) {

	if (!req.session || !req.session.authorized)
        return res.status(403).send();

	model.recipe.fetchByAuthor(req.session.user._id, function(err, recipes) {

		if (err != null)
			return res.status(401).send('An error occured during the search.');
		else if (recipes == null)
			return res.status(201).send('No recipe found.');
		return res.send(recipes);
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
				return res.status(401).send('An error occured during the search.');
			else if (result == null)
				return res.status(201).send('No '+tablesToLoad[index]+' found.');
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
			return res.status(401).send('An error occured during the search.');
		else if (recipeIngredients == null)
			return res.status(201).send('No ingredient found.');
		else
			recipe.recipeIngredients = recipeIngredients;
		model.ingredient.fetchByRecipeIngredients(recipe.recipeIngredients, function(err, recipeIngredients2) {

			if (err != null)
				return res.status(401).send('An error occured during the search.');
			else if (recipeIngredients2 == null)
				return res.status(201).send('No ingredient found.');
			else
				recipe.recipeIngredients = recipeIngredients2;

			tablesToLoad = ['user'];

			recipe = loadRecipeDetailsLoop(res, recipe, tablesToLoad, 0);
		});
	});
}

exports.findRecipeByName = function(req, res) {

	if (req.body.name === undefined) {
		res.status(401).send('No name given.');
		return;
	}

	var name = req.body.name;

	console.log('Get a recipe: ' + name);

	model.recipe.fetchByName(name, function(err, recipeRes) {
		var recipe = null;

		if (err != null)
			return res.status(401).send('An error occured during the search.');
		else if (recipeRes == null)
			return res.status(201).send('No recipe found.');
		else
			recipe = recipeRes;

		recipe = loadRecipeDetails(res, recipe);
	});
};

exports.findRecipesBySearch = function(req, res) {

	if (req.body.search === undefined) {
		res.status(401).send('No search given.');
		return;
	}

	if (req.body.search.length < 3) {
		return res.status(201).send('Search start after 3 character.');
	}

	var search = req.body.search;

	console.log('Get a recipe by search: ' + search);

	model.recipe.fetchBySearch(search, function(err, recipe) {

		if (err != null)
			return res.status(401).send('An error occured during the search.');
		else if (!recipe)
			return res.status(201).send('No recipe found.');
		else
			return res.send(recipe);
	});
};

exports.findAllRecipes = function(req, res) {
	
	console.log('GetRecipes');
	model.recipe.fetchAll(0, 0, function(err, recipes) {
		if (err != null)
			return res.status(401).send('An error occured during the search.');
		else if (recipes.length == 0)
			return res.status(201).send('No recipes found.');
		else
			return res.send(recipes);
	});
};

exports.countAllRecipes = function(req, res) {
	
	console.log('Get Recipes Count');

	model.recipe.countAll(function(err, nbRecipes) {
		if (err != null)
			return res.status(401).send('An error occured during the search.');
		else
			return res.status(200).send(nbRecipes+'');
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
			return res.status(401).send('An error occured during the search.');
		else if (recipes.length == 0)
			return res.status(201).send('No recipes found.');
		else
			return res.send(recipes);
	});
};

exports.findRecipesByIngredient = function(req, res) {
	
	console.log('GetRecipesByIngredient');

	if (req.body.search === undefined) {
		return res.status(401).send('No search given.');
	}

	if (req.body.search.length < 3) {
		return res.status(201).send('Search start after 3 character.');
	}

	model.ingredient.fetchBySearch(req.body.search, function(err, ingredients) {

		if (err != null)
			return res.status(401).send('An error occured during the search.');
		else if (!ingredients)
			return res.status(201).send('No ingredient found.');

		var ingredientIds = [];

		for (var index in ingredients) {
			ingredientIds.push(ingredients[index]['_id']);
		}

		model.recipeIngredient.fetchByIngredients(ingredientIds, function(err, recipeIngredients) {

			if (err != null)
				return res.status(401).send('An error occured during the search.');
			else if (recipeIngredients == null)
				return res.status(201).send('No ingredient found.');
			else {

				var recipeIds = [];

				for (var index in recipeIngredients) {
					recipeIds.push(recipeIngredients[index]['recipe']);
				}

				model.recipe.fetchByIds(recipeIds, function(err, recipes) {

					if (err != null)
						return res.status(401).send('An error occured during the search.');
					else if (recipeIngredients == null)
						return res.status(201).send('No ingredient found.');
					else
						return res.send(recipes);

				});
			}

		});
	});
};

exports.findRecipesBySubtype = function(req, res) {
	
	console.log('GetRecipesBySubtype');

	if (req.body.search === undefined) {
		return res.status(401).send('No search given.');
	}

	if (req.body.search.length < 3) {
		return res.status(201).send('Search start after 3 character.');
	}

	model.subtype.fetchBySearch(req.body.search, function(err, subtypes) {

		if (err != null)
			return res.status(401).send('An error occured during the search.');
		else if (!subtypes)
			return res.status(201).send('No ingredient found.');

		var subtypeIds = [];

		for (var index in subtypes) {
			subtypeIds.push(subtypes[index]['_id']);
		}

		model.recipe.fetchBySubtypes(subtypeIds, function(err, recipes) {

			if (err != null)
				return res.status(401).send('An error occured during the search.');
			else if (!recipes)
				return res.status(201).send('No recipes found.');
			else {
				res.send(recipes);
			}

		});
	});
};

var populateDB = function() {

		var defaultRecipe = 
		{
			"_id": new mongo.ObjectID("566d6ea4e1b9caac0dac79ee"),
			"name": "Pumpkin cheese bread",
			"description": "Pomelo spicy chicken wings can pour wine as a kind of snacks, can also be a kind of office work leisure snacks.Which is characterized by special pomelo spiced salt, grapefruit aromas and spicy chicken wings pepper aroma.This kind of hot even children can accept.",
			"image": "baicai.jpg",
			"tips": "Pomelo spiced salt in my recipes have separate method.This is the main condiment is Thai sweet chili sauce and pomelo spiced salt, the other can be adjusted according to personal taste.",
			"steps": [
				{
					"level":1,
					"text":"To prepare sauce.",
					"image":"step1.jpg",
				},
				{
					"level":2,
					"text":"Wash chicken wings and ice water for 30 minutes, draw two blade so that a good flavor, add soya milk, chicken, sweet chili sauce, shaddock spiced salt, chili powder, sugar, oyster sauce mix together, pickled flavor.About 1 hour.",
					"image":"step2.jpg"
				},
				{
					"level":3,
					"text":"Good pickled chicken wings on the grill and sprinkle with sesame seeds ground into a powder.In a little bit of pomelo spiced salt.",
					"image":"step3.jpg"
				},
				{
					"level":4,
					"text":"Slag baking oven bottom put, rack up and down into the middle roast fire 180 degrees bake for 15 minutes.",
					"image":"step4.jpg"
				},
				{
					"level":5,
					"text":"Remove the chicken wings over here, also sprinkle some sesame powder and pomelo spiced salt, into the oven to bake for 10 minutes.Circumstances can flip again into the upper bake for 5 minutes, will be more burn.",
					"image":"step5.jpg"
				},
				{
					"level":6,
					"text":"Meimei's wings to ok pomelo.",
					"image":"step6.jpg"
				}
			],
			"users": [
				new mongo.ObjectID("4e54ed9f48dc5922c0094a32")
			],
			"recipeIngredients": [
				new mongo.ObjectID("566d7541e1b9caac0dac79fa"),
				new mongo.ObjectID("566d7568e1b9caac0dac79fb"),
				new mongo.ObjectID("566d75a3e1b9caac0dac79fc")
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