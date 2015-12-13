var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.fetchAll = function(cb) {

	db.collection('recipes', function(err, collection) {

		collection.find().toArray(function(err, recipes) {

			if (err)
				return cb();   

			return cb(null, recipes);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('recipes', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, recipe) {

			if (err)
				return cb();   

			return cb(null, recipe);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('recipes', function(err, collection) {

		collection.findOne({'name':name}, function(err, recipe) {

			if (err)
				return cb();

			return cb(null, recipe);
		});
	});
};
