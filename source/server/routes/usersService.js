var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;


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
    
    db.collection('users', function(err, collection) {
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

var populateDB = function() {

    //User collection (right 1: consumers 2: gastronomist 3: supplier)
    var users = [
        {
            _id: new.mongo.ObjectID("4e54ed9f48dc5922c0094a30"),
            username: "Foo",
            password: "bar",
            right: 1,
            recipes: [
                ObjectId("4e54ed9f48dc5922c0094a40"),
            ]
        },
        {
            _id: new.mongo.ObjectID("4e54ed9f48dc5922c0094a31"),
            username: "Titi",
            password: "titipwd",
            right: 2,
            recipes: [
            ]
        },
        {
            _id: new.mongo.ObjectID("4e54ed9f48dc5922c0094a32"),
            username: "toto",
            password: "totopwd",
            right: 3,
            recipes: [
            ]
        }
    ];
    db.collection('users', function(err, collection) {
        collection.insert(users, {safe:true}, function(err, result) {});
    });

};

commonService.prepareCheckCollection('users', populateDB);