var mongo = require('mongodb');

var Server = mongo.Server,
    Db = mongo.Db,
    BSON = mongo.BSONPure;

var server = new Server('localhost', 27017, {auto_reconnect: true});
db = new Db('usersDB', server);

db.open(function(err, db) {
    if(!err) {
        console.log("Connected to 'usersDB' database");
        db.collection('users', {strict:true}, function(err, collection) {
            if (err) {
                console.log("The 'users' collection doesn't exist. Creating it with sample data...");
                populateDB();
            }
        });
    }
});

exports.findUserById = function(req, res) {
    var id = req.params.id;
    console.log('Get a user: ' + id);
    db.collection('users', function(err, collection) {
        collection.findOne({'_id':new BSON.ObjectID(id)}, function(err, item) {
            res.send(item);
        });
    });
};

exports.findAllUsers = function(req, res) {
    db.collection('Get all users', function(err, collection) {
        collection.find().toArray(function(err, items) {
            res.send(items);
        });
    });
};

exports.addUser = function(req, res) {
    var user = req.body;
    console.log('Add user: ' + JSON.stringify(user));
    db.collection('users', function(err, collection) {
        collection.insert(user, {safe:true}, function(err, result) {
            if (err) {
                res.send({'error':'An error has occurred'});
            } else {
                console.log('Success: ' + JSON.stringify(result[0]));
                res.send(result[0]);
            }
        });
    });
}

exports.deleteUser = function(req, res) {
    var id = req.params.id;
    console.log('Deleting user: ' + id);
    // db.collection('users', function(err, collection) {
    //     collection.remove({'_id':new BSON.ObjectID(id)}, {safe:true}, function(err, result) {
    //         if (err) {
    //             res.send({'error':'An error has occurred - ' + err});
    //         } else {
    //             console.log('' + result + ' document(s) deleted');
    //             res.send(req.body);
    //         }
    //     });
    // });
}

/*--------------------------------------------------------------------------------------------------------------------*/
// Populate database with sample data -- Only used once: the first time the application is started.
// You'd typically not find this code in a real-life app, since the database would already exist.
var populateDB = function() {

    var users = [
    {
        username: "Tchitos",
        password: "1234",
        right: "3",
    },
    {
        username: "Toto",
        password: "azerty",
        right: "1",
    },
    {
        username: "Titi",
        password: "123",
        right: "1",
        
    }
    ];
    db.collection('users', function(err, collection) {
        collection.insert(users, {safe:true}, function(err, result) {});
    });

};