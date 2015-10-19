var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;

var db = commonService.db;

db.open(function(err, db) {
    if(!err) {
        console.log("Connected to 'usersDB' database");
        db.collection('ingredients', {strict:true}, function(err, collection) {
            if (err) {
                console.log("The 'ingredients' collection doesn't exist. Creating it with sample data...");
                populateDB();
            }
        });
    }
});

exports.findIngredientById = function(req, res) {
    var id = req.params.id;
    console.log('Get an ingredient: ' + id);
    db.collection('ingredients', function(err, collection) {
        collection.findOne({'_id':new BSON.ObjectID(id)}, function(err, item) {
            res.send(item);
        });
    });
};

exports.findAllIngredients = function(req, res) {
    db.collection('ingredients', function(err, collection) {
        collection.find().toArray(function(err, items) {
            res.send(items);
        });
    });
};

exports.addIngredient = function(req, res) {
    var ingredient = req.body;
    console.log('Add ingredient: ' + JSON.stringify(ingredient));
    db.collection('ingredients', function(err, collection) {
        collection.insert(ingredient, {safe:true}, function(err, result) {
            if (err) {
                res.send({'error':'An error has occurred'});
            } else {
                console.log('Success: ' + JSON.stringify(result[0]));
                res.send(result[0]);
            }
        });
    });
}

exports.deleteIngredient = function(req, res) {
    var id = req.params.id;
    console.log('Deleting ingredient: ' + id);
    // db.collection('ingredients', function(err, collection) {
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
            name: "pepper",
        },
        {
            username: "rice",
        }
    ];
    db.collection('users', function(err, collection) {
        collection.insert(users, {safe:true}, function(err, result) {});
    });

};