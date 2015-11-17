var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var ObjectId = require('mongodb').ObjectID;

var db = commonService.db;

exports.findStatById = function(req, res) {
    var id = req.params.id;
    console.log('Get a stat: ' + id);
    db.collection('stats', function(err, collection) {
        collection.findOne({'_id':new BSON.ObjectID(id)}, function(err, item) {
            res.send(item);
        });
    });
};

exports.addStat = function(req, res) {
    var stat = req.body;
    console.log('Add stat: ' + JSON.stringify(stat));
    db.collection('stats', function(err, collection) {
        collection.insert(stat, {safe:true}, function(err, result) {
            if (err) {
                res.send({'error':'An error has occurred'});
            } else {
                console.log('Success: ' + JSON.stringify(result[0]));
                res.send(result[0]);
            }
        });
    });
}

exports.deleteStat = function(req, res) {
    var id = req.params.id;
    console.log('Deleting stat: ' + id);
    // db.collection('ingredient', function(err, collection) {
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

    var stat = [

        ];

    db.collection('stats', function(err, collection) {
        collection.insert(stat, {safe:true}, function(err, result) {});
    });

};

//commonService.prepareCheckCollection('stats', populateDB);