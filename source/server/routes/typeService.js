var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);

exports.findAllTypesDetails = function(req, res) {
	
	model.type.fetchAll(function(err, types) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (types.length == 0)
			res.status(201).send('No types found.');
		else {

			model.subtype.fetchAll(function(err, subtypes) {
				if (err != null)
					res.status(401).send('An error occured during the search.');
				else if (subtypes.length == 0)
					res.status(201).send('No types found.');
				else {
					for (index in subtypes) {
						for (index2 in types) {
							if (types[index2]['_id'] == subtypes[index]['type']) {
								if (types[index2]['subtypes'] === undefined)
									types[index2]['subtypes'] = [];
								types[index2]['subtypes'].push(subtypes[index]);
							}
						}
					}
					res.send(types);
				}
			});
		}
	});
};

var populateDB = function() {

	//Type collection (right 1: consumers 2: gastronomist 3: supplier)
	var types = [
		{
			_id: new mongo.ObjectID("567fb0a72c8099aa1008e0bd"),
			name: "Health",
		},
		{
			_id: new mongo.ObjectID("567fb0c72c8099aa1008e0be"),
			name: "Sickness",
		},
		{
			_id: new mongo.ObjectID("567fb0dc2c8099aa1008e0bf"),
			name: "Religion",
		},
		{
			_id: new mongo.ObjectID("567fb0fe2c8099aa1008e0c0"),
			name: "Diet",
			type: "567fb0fe2c8099aa1008e0c0"
		},
		{
			_id: new mongo.ObjectID("567fb11c2c8099aa1008e0c1"),
			name: "Crowd",
		},
	];
	db.collection('types', function(err, collection) {
		collection.insert(types, {safe:true}, function(err, result) {});
	});

};

commonService.prepareCheckCollection('types', populateDB);