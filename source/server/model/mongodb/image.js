var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.add = function(name, path, mimetype, size, cb) {

	db.collection('images', function(err, collection) {

		image = {
			'name': name,
			'path': path,
			'mimetype': mimetype,
			'size': size
		};

		collection.insert(image, {safe:true}, function(err, result) {
            if (err)
				return cb(); 
			return cb(null, result.ops[0]);
        });
	});
}

module.exports.fetchAll = function(cb) {

	db.collection('images', function(err, collection) {

		collection.find().skip(skip).limit(limit).toArray(function(err, images) {

			if (err)
				return cb();   

			return cb(null, images);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('images', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, image) {

			if (err)
				return cb();   

			return cb(null, image);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('images', function(err, collection) {

		collection.findOne({'name':name}, function(err, image) {

			if (err)
				return cb();

			return cb(null, image);
		});
	});
};
