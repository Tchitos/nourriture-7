var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.fetchAll = function(cb) {

	db.collection('types', function(err, collection) {

		collection.find().toArray(function(err, types) {

			if (err)
				return cb();   

			return cb(null, types);
		});
	});
};

module.exports.fetchByIds = function(ids, cb) {

	db.collection('types', function(err, collection) {

		collection.find({"_id": {$in: ids}}).toArray(function(err, types) {

			if (err)
				return cb();   

			return cb(null, types);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('types', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, type) {

			if (err)
				return cb();   

			return cb(null, type);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('types', function(err, collection) {

		collection.findOne({'name':name}, function(err, type) {

			if (err)
				return cb();

			return cb(null, type);
		});
	});
};
