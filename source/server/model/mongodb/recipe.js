var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);

module.exports.add = function(recipeName, recipeDesc, recipeTips, ingredients, steps, cb) {

	db.collection('recipes', function(err, collection) {

		recipe = {
			'name': recipeName,
			'description': recipeDesc,
			'tips': recipeTips,
		};

		collection.insert(recipe, {safe:true}, function(err, result) {
            if (err)
				return cb();
			//model.recipeIngredient.add(result.ops[0]._id, ingredients);
			//model.step.add(result.ops[0]._id, steps);
			return cb(null, result.ops[0]);
        });
	});
}

module.exports.fetchAll = function(skip, limit, cb) {

	db.collection('recipes', function(err, collection) {

		collection.find().skip(skip).limit(limit).toArray(function(err, recipes) {

			if (err)
				return cb(err);   

			return cb(null, recipes);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('recipes', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, recipe) {

			if (err)
				return cb(err);   

			return cb(null, recipe);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('recipes', function(err, collection) {

		collection.findOne({'name':name}, function(err, recipe) {

			if (err)
				return cb(err);

			return cb(null, recipe);
		});
	});
};

module.exports.countAll = function(cb) {

	db.collection('recipes', function(err, collection) {

		collection.count(function(err, nbRecipes) {

			if (err)
				return cb(err);   

			return cb(null, nbRecipes);
		});
	});
};
