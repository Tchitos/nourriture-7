var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var db = commonService.db;

module.exports.fetchById = function(id, cb) {
    
    db.collection('users', function(err, collection) {
        
        collection.findOne({'id':id}, function(err, user) {
        
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
