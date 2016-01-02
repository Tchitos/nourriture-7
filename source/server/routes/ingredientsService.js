var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);

exports.addIngredient = function(req, res, next) {

	console.log(req.file.path);
	return res.status(500).send('error');

	if (!req.session || !req.session.authorized)
		return res.status(403);

	if (!req.body.name)
		return res.send('Missing parameters');

	var ingredientName = req.body.name;

	model.ingredient.add(ingredientName, function(err, user) {

		if (err)
			return res.status(500).send('An error occured.');

		res.send('ok');
	});
};

exports.findIngredientById = function(req, res) {
	var id = req.params.id;
	console.log('Get an ingredient: ' + id);
	db.collection('ingredients', function(err, collection) {
		collection.findOne({'_id':new BSON.ObjectID(id)}, function(err, item) {
			res.send(item);
		});
	});
};

exports.findAllIngredients = function(req, res) {
	model.ingredient.fetchAll(function(err, ingredients) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (ingredients.length == 0)
			res.status(201).send('No types found.');
		else {
			res.send(ingredients);
		}
	});
};

var findMatch = function(recipes, id) {
	var res = [{}];
	var i = 0;
	var j = 0;
	var h = 0;

	while (recipes[i]) {
		while (recipes[i].ingredients[j]) {
			if (recipes[i].ingredients[j] == id) {
				res[h] = recipes[i];
				h += 1;
			}
			j += 1;
		}
		j = 0;
		i += 1;
	}

	return (res);
}

exports.getIngredientRecipe = function(req, res) {
	var id = req.params.id;

	db.collection('recipes', function(err, collection) {
		collection.find().toArray(function(err, items) {
			res.send(findMatch(items, id));
		});
	});
}

exports.deleteIngredient = function(req, res) {
	var id = req.params.id;
	console.log('Deleting ingredient: ' + id);
	// db.collection('ingredients', function(err, collection) {
	//     collection.remove({'_id':new BSON.ObjectID(id)}, {safe:true}, function(err, result) {
	//         if (err) {
	//             res.send({'error':'An error has occurred - ' + err});
	//         } else {
	//             console.log('' + result + ' document(s) deleted');
	//             res.send(req.body);
	//         }
	//     });
	// });
}

/*--------------------------------------------------------------------------------------------------------------------*/
// Populate database with sample data -- Only used once: the first time the application is started.
// You'd typically not find this code in a real-life app, since the database would already exist.
var populateDB = function() {

	var ingredients = [
		{
			"_id": new mongo.ObjectID("566d6f26e1b9caac0dac79f1"),
			"name": "Dry pepper"
		},
		{
			"_id": new mongo.ObjectID("566d6f51e1b9caac0dac79f2"),
			"name": "Cabbage"
		},
		{
			"_id": new mongo.ObjectID("566d6f60e1b9caac0dac79f3"),
			"name": "Black fungus"
		}
	];

	db.collection('ingredients', function(err, collection) {
		collection.insert(ingredients, {safe:true}, function(err, result) {});
	});

};

commonService.prepareCheckCollection('ingredients', populateDB);