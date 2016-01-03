var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.fetchAll = function(skip, limit, cb) {

	db.collection('nutritions', function(err, collection) {

		collection.find().skip(skip).limit(limit).toArray(function(err, nutritions) {

			if (err)
				return cb(err);   

			return cb(null, nutritions);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('nutritions', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, nutrition) {

			if (err)
				return cb(err);   

			return cb(null, nutrition);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('nutritions', function(err, collection) {

		collection.findOne({'name':name}, function(err, nutrition) {

			if (err)
				return cb(err);

			return cb(null, nutrition);
		});
	});
};

