var mongo = require('mongodb');

var Server = mongo.Server,
    Db = mongo.Db;

var server = new Server('localhost', 27017, {auto_reconnect: true});
exports.db = new Db('usersDB', server);

/*exports.db.open(function(err, db) {

    if (!err) {
     
        console.log("Connected to 'usersDB' database");
    }
    else {

    	console.log("Connection to 'userDB' database failed");
    	throw "Error with db"; 
    }

});*/