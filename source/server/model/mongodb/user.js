var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.fetchById = function(id, cb) {

	db.collection('users', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, user) {

			if (err)
				return cb();   
			return cb(null, user);
		});
	});
};

module.exports.fetchByUsername = function(username, cb) {

	db.collection('users', function(err, collection) {

		collection.findOne({'username':username}, function(err, user) {

			if (err)
					return cb();
			return cb(null, user);
		});
	});
};

module.exports.checkPassword = function(user, password, cb) {

	(user.password == password) ? cb(null, true) : cb(null, false);
};

module.exports.getLvlFromToken = function(token, cb) {

	tokenModel.fetchByToken(token, function(err, user_id) {

		if (err)
			cb(err);

		module.exports.fetchById(user_id, function(err, user) {

			console.log(user);
			if (err)
				cb(err);
			cb(null, user.right);
		});
	});
}