var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var db = commonService.db;

module.exports.generateToken = function(user, cb) {
    
    var token = Math.random().toString(36).substr(2);
    var tokenObj = {'user_id': user.id, 'token': token};

    db.collection('tokens', function(err, collection) {

        collection.insert(tokenObj, {}, function(err, result) {
            
            if (err)
                return cb(err, null);

            console.log('Add token: ' + token + ' for user: ' + user.username);
            return cb(null, token);
        });
    });
};
