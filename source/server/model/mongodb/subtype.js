var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.fetchAll = function(cb) {

	db.collection('subtypes', function(err, collection) {

		collection.find().toArray(function(err, subtypes) {

			if (err)
				return cb();   

			return cb(null, subtypes);
		});
	});
};

module.exports.fetchByIds = function(ids, cb) {

	db.collection('subtypes', function(err, collection) {

		collection.find({"_id": {$in: ids}}).toArray(function(err, subtypes) {

			if (err)
				return cb();   

			return cb(null, subtypes);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('subtypes', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, subtype) {

			if (err)
				return cb();   

			return cb(null, subtype);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('subtypes', function(err, collection) {

		collection.findOne({'name':name}, function(err, subtype) {

			if (err)
				return cb();

			return cb(null, subtype);
		});
	});
};

module.exports.fetchBySearch = function(search, cb) {

	db.collection('subtypes', function(err, collection) {

		collection.find({'name': new RegExp(search, 'i')}).toArray(function(err, subtype) {

			if (err)
				return cb();

			return cb(null, subtype);
		});
	});
};
