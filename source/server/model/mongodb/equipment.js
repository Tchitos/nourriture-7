var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.fetchAll = function(cb) {

	db.collection('equipments', function(err, collection) {

		collection.find().toArray(function(err, equipments) {

			if (err)
				return cb();   

			return cb(null, equipments);
		});
	});
};

module.exports.fetchByIds = function(ids, cb) {

	db.collection('equipments', function(err, collection) {

		collection.find({"_id": {$in: ids}}).toArray(function(err, equipments) {

			if (err)
				return cb();   

			return cb(null, equipments);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('equipments', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, equipment) {

			if (err)
				return cb();   

			return cb(null, equipment);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('equipments', function(err, collection) {

		collection.findOne({'name':name}, function(err, equipment) {

			if (err)
				return cb();

			return cb(null, equipment);
		});
	});
};
