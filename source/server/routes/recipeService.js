var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;


exports.findRecipeById = function(req, res) {
    var id = req.params.id;
    console.log('Get a recipe: ' + id);
    db.collection('recipes', function(err, collection) {
        collection.findOne({'_id':new BSON.ObjectID(id)}, function(err, item) {
            res.send(item);
        });
    });
};

exports.findAllRecipes = function(req, res) {
    
    db.collection('recipes', function(err, collection) {
        collection.find().toArray(function(err, items) {
            res.send(items);
        });
    });
};

var findMatch = function(ingredients, id) {
    var res = [{}];
    var i = 0;
    var j = 0;
    var h = 0;

    while (ingredients[i]) {
        while (ingredients[i].recipes[j]) {
            if (ingredients[i].recipes[j] == id) {
                res[h] = ingredients[i];
                h += 1;
            }
            j += 1;
        }
        j = 0;
        i += 1;
    }
    return (res);
}

exports.getRecipeIngredient = function(req, res) {
    var id = req.params.id;

    db.collection('ingredients', function(err, collection) {
        collection.find().toArray(function(err, items) {
            res.send(findMatch(items, id));
        });
    });
}

var userRecipes = function(recipes, id) {
    var res = [{}];
    var i = 0;
    var h = 0;

    while (recipes[i]) {
        if (recipes[i].user == id){
            res[h] = recipes[i];
            h += 1;
        }
        i += 1;
    }
    return (res);
}

exports.getOwnerRecipes = function(req, res) {
    var id = req.params.id;
    
    db.collection('recipes', function(err, collection) {
        collection.find().toArray(function(err, items){
            res.send(userRecipes(items, id));
        });
    });
}

exports.addRecipe = function(req, res) {
    var recipe = req.body;
    console.log('Add recipe: ' + JSON.stringify(recipe));
    db.collection('recipes', function(err, collection) {
        collection.insert(recipe, {safe:true}, function(err, result) {
            if (err) {
                res.send({'error':'An error has occurred'});
            } else {
                console.log('Success: ' + JSON.stringify(result[0]));
                res.send(result[0]);
            }
        });
    });
}



exports.deleteRecipe = function(req, res) {
    var id = req.params.id;
    console.log('Deleting recipe: ' + id);
}

var populateDB = function() {
    

        var defaultRecipe = {
            _id: "4e54ed9f48dc5922c0094a40",
            name: "Hot chocolate",
            description: "blablabla",
            steps: ["1: do something", "2: do something else etc..."],
            ingredients: [
                ObjectId("4e54ed9f48dc5922c0094a41"),
                ObjectId("4e54ed9f48dc5922c0094a42"),
            ],
            user: ObjectId("4e54ed9f48dc5922c0094a30")
        };


    db.collection('recipes', function(err, collection) {
        collection.insert(defaultRecipe, {safe:true}, function(err, result) {});
    });

};

commonService.prepareCheckCollection('recipes', populateDB);