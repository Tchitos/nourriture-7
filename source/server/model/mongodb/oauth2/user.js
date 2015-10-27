var mongo = require('mongodb');
var commonService = require('../../../routes/commonService');
var db = commonService.db;

module.exports.getId = function(user) {
    return user.id;
};

module.exports.fetchById = function(id, cb) {
    for (var i in users) {
        if (id == users[i].id) return cb(null, users[i]);
    };
    cb();
};

module.exports.fetchByUsername = function(username, cb) {
    db.collection('users', function(err, collection) {
        collection.findOne({'username':username}, function(err, user) {
            cb(null, user);
        });
    });
};

module.exports.checkPassword = function(user, password, cb) {
    (user.password == password) ? cb(null, true) : cb(null, false);
};

module.exports.fetchFromRequest = function(req) {
    return req.session.user;
};