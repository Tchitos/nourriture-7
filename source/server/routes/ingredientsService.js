var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var ObjectId = require('mongodb').ObjectID;

var db = commonService.db;

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

var findMatch = function(recipes, id) {
    var res = [{}];
    var i = 0;
    var j = 0;
    var h = 0;


    while (recipes[i]) {
        while (recipes[i].ingredients[j]) {
            if (recipes[i].ingredients[j] == id) {
                res[h] = recipes[i];
                h += 1;
            }
            j += 1;
        }
        j = 0;
        i += 1;
    }

    return (res);
}

exports.getIngredientRecipe = function(req, res) {
    var id = req.params.id;

    db.collection('recipes', function(err, collection) {
        collection.find().toArray(function(err, items) {
            res.send(findMatch(items, id));
        });
    });
}



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

    var ingredients = [
        {
            _id: "4e54ed9f48dc5922c0094a41",
            name: "milk",
            recipes: [
                ObjectId("4e54ed9f48dc5922c0094a40"),
            ],
            composition: null
        },
        {
            _id: "4e54ed9f48dc5922c0094a42",
            name: "chocolate powder",
            recipes: [
                ObjectId("4e54ed9f48dc5922c0094a40"),
            ],
            composition: null
        }
        ];

    db.collection('ingredients', function(err, collection) {
        collection.insert(ingredients, {safe:true}, function(err, result) {});
    });

};

commonService.prepareCheckCollection('ingredients', populateDB);