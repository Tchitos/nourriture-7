var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;
var ingredientModel = require('./ingredient');

function subAddRecibeIngredientRec(i, recipeId, ingredients, recipeIngredientsIds, cb) {

	var recipeIngredients = {
		'quantity': ingredients[i].quantity,
		'mandatory': ingredients[i].mandatory,
		'ingredient': new mongo.ObjectID(ingredients[i].ingredient),
		'recipe': new mongo.ObjectID(recipeId),
	};

	db.collection('recipeIngredients', function(err, collection) {

		collection.insert(recipeIngredients, {safe:true}, function(err, result) {

			recipeIngredientsIds.push(result.ops[0]._id);
			addRecipeIngredientRec(i + 1, recipeId, ingredients, recipeIngredientsIds, cb);
		});
	});
}

function addRecipeIngredientRec(i, recipeId, ingredients, recipeIngredientsIds, cb) {

	if (i >= ingredients.length)
		return (cb(recipeIngredientsIds));

	if (!ingredients[i].ingredient) {
		ingredientModel.fetchByName(ingredients[i].name, function(err, ingredient) {

			if (ingredient) {

				ingredients[i].ingredient = ingredient._id;
				subAddRecibeIngredientRec(i, recipeId, ingredients, recipeIngredientsIds, cb);
			} else {

				ingredientModel.addWithoutImage(ingredients[i].name, function(err, ingredient) {

					ingredients[i].ingredient = ingredient._id;
					subAddRecibeIngredientRec(i, recipeId, ingredients, recipeIngredientsIds, cb);
				});
			}
		});
	} else
		subAddRecibeIngredientRec(i, recipeId, ingredients, recipeIngredientsIds, cb);
}

module.exports.add = function(recipeId, ingredients, cb) {

	var recipeIngredientsIds = [];
	var i = 0;
	addRecipeIngredientRec(i, recipeId, ingredients, recipeIngredientsIds, cb);
}

module.exports.fetchAll = function(cb) {

	db.collection('recipeIngredients', function(err, collection) {

		collection.find().toArray(function(err, recipeIngredients) {

			if (err)
				return cb();   

			return cb(null, recipeIngredients);
		});
	});
};

module.exports.fetchByIds = function(ids, cb) {

	db.collection('recipeIngredients', function(err, collection) {

		collection.find({"_id": {$in: ids}}).toArray(function(err, recipeIngredients) {

			if (err)
				return cb();   

			return cb(null, recipeIngredients);
		});
	});
};

module.exports.fetchByIngredients = function(ingredients, cb) {

	db.collection('recipeIngredients', function(err, collection) {

		collection.find({"ingredient": {$in: ingredients}}).toArray(function(err, recipeIngredients) {

			if (err)
				return cb();   

			return cb(null, recipeIngredients);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('recipeIngredients', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, recipeIngredient) {

			if (err)
				return cb();   

			return cb(null, recipeIngredient);
		});
	});
};
