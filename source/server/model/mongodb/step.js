var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.fetchAll = function(cb) {

	db.collection('steps', function(err, collection) {

		collection.find().toArray(function(err, steps) {

			if (err)
				return cb();   

			return cb(null, steps);
		});
	});
};

module.exports.fetchByIds = function(ids, cb) {

	db.collection('steps', function(err, collection) {

		collection.find({"_id": {$in: ids}}).toArray(function(err, steps) {

			if (err)
				return cb();   

			return cb(null, steps);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('steps', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, step) {

			if (err)
				return cb();   

			return cb(null, step);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('steps', function(err, collection) {

		collection.findOne({'name':name}, function(err, step) {

			if (err)
				return cb();

			return cb(null, step);
		});
	});
};
