var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

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

module.exports.fetchById = function(id, cb) {

	db.collection('recipeIngredients', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, recipeIngredient) {

			if (err)
				return cb();   

			return cb(null, recipeIngredient);
		});
	});
};
