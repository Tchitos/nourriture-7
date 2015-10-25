var mongo = require('mongodb');

var Server = mongo.Server,
    Db = mongo.Db;

var connected = false;
var connectionToCheck = [];

var server = new Server('localhost', 27017, {auto_reconnect: true});
exports.db = new Db('usersDB', server);

exports.db.open(function(err, db) {

    if (!err) {
     
        console.log("Connected to 'usersDB' database");
        
        for (var i in connectionToCheck)
        	checkCollection(connectionToCheck[i].collection, connectionToCheck[i].callback);
        
        connected = true;
    }
    else {

    	console.log("Connection to 'userDB' database failed");
    	throw "Error with db"; 
    }

});

function checkCollection(collectionName, callback) {

	exports.db.collection(collectionName, {strict:true}, function(err, collection) {
	    
	    if (err) {
	        console.log("The '" + collectionName + "' collection doesn't exist. Creating it with sample data...");
	        callback();
	    }
	});
}

exports.prepareCheckCollection = function(collection, populateCallback) {

	if (connected == false)
		connectionToCheck.push({collection: collection, callback: populateCallback});
	else
		checkCollection(collection, populateCallback);
}