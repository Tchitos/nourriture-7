var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;


exports.findSubtypeById = function(req, res) {
	var id = req.params.id;
	console.log('Get a subtype: ' + id);
	db.collection('subtypes', function(err, collection) {
		collection.findOne({'_id':new BSON.ObjectID(id)}, function(err, item) {
			res.send(item);
		});
	});
};

exports.findAllSubtypes = function(req, res) {
	
	db.collection('subtypes', function(err, collection) {
		collection.find().toArray(function(err, items) {
			res.send(items);
		});
	});
};

exports.addSubtype = function(req, res) {
	var subtype = req.body;
	console.log('Add subtype: ' + JSON.stringify(subtype));
	db.collection('subtypes', function(err, collection) {
		collection.insert(subtype, {safe:true}, function(err, result) {
			if (err) {
				res.send({'error':'An error has occurred'});
			} else {
				console.log('Success: ' + JSON.stringify(result[0]));
				res.send(result[0]);
			}
		});
	});
}

exports.deleteSubtype = function(req, res) {
	var id = req.params.id;
	console.log('Deleting subtype: ' + id);
	// db.collection('subtypes', function(err, collection) {
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

	//Subtype collection (right 1: consumers 2: gastronomist 3: supplier)
	var subtypes = [
		{
			_id: new mongo.ObjectID("567fb65f310e116611080940"),
			name: "Clearning Heat",
			type: "567fb0a72c8099aa1008e0bd"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080941"),
			name: "Eliminating phlegm",
			type: "567fb0a72c8099aa1008e0bd"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080942"),
			name: "Tonifying kidney",
			type: "567fb0a72c8099aa1008e0bd"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080943"),
			name: "Tonifying Yang",
			type: "567fb0a72c8099aa1008e0bd"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080944"),
			name: "Invigorating the stomach",
			type: "567fb0a72c8099aa1008e0bd"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080945"),
			name: "Promoting appetite",
			type: "567fb0a72c8099aa1008e0bd"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080946"),
			name: "Moistening lung",
			type: "567fb0a72c8099aa1008e0bd"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080947"),
			name: "Antitussive",
			type: "567fb0a72c8099aa1008e0bd"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080948"),
			name: "Improving eyesight",
			type: "567fb0a72c8099aa1008e0bd"
		},

		{
			_id: new mongo.ObjectID("567fb65f310e116611080949"),
			name: "Diabetes",
			type: "567fb0c72c8099aa1008e0be"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e11661108094a"),
			name: "Hypertension",
			type: "567fb0c72c8099aa1008e0be"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e11661108094b"),
			name: "Gout",
			type: "567fb0c72c8099aa1008e0be"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e11661108094c"),
			name: "Gastritis",
			type: "567fb0c72c8099aa1008e0be"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e11661108094d"),
			name: "Hemorrhoids",
			type: "567fb0c72c8099aa1008e0be"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e11661108094e"),
			name: "Climacteric",
			type: "567fb0c72c8099aa1008e0be"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e11661108094f"),
			name: "Thyroid enlargement",
			type: "567fb0c72c8099aa1008e0be"
		},

		{
			_id: new mongo.ObjectID("567fb65f310e116611080950"),
			name: "Buddhism",
			type: "567fb0dc2c8099aa1008e0bf"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080951"),
			name: "Islam",
			type: "567fb0dc2c8099aa1008e0bf"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080952"),
			name: "Christianity",
			type: "567fb0dc2c8099aa1008e0bf"
		},

		{
			_id: new mongo.ObjectID("567fb65f310e116611080953"),
			name: "Slimming",
			type: "567fb0fe2c8099aa1008e0c0"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080954"),
			name: "Slimming Face",
			type: "567fb0fe2c8099aa1008e0c0"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080955"),
			name: "Slimming Legs",
			type: "567fb0fe2c8099aa1008e0c0"
		},

		{
			_id: new mongo.ObjectID("567fb65f310e116611080956"),
			name: "Baby",
			type: "567fb11c2c8099aa1008e0c1"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080957"),
			name: "Pregnant woman",
			type: "567fb11c2c8099aa1008e0c1"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080958"),
			name: "Lying-in woman",
			type: "567fb11c2c8099aa1008e0c1"
		},
		{
			_id: new mongo.ObjectID("567fb65f310e116611080959"),
			name: "Elder people",
			type: "567fb11c2c8099aa1008e0c1"
		},
	];
	db.collection('subtypes', function(err, collection) {
		collection.insert(subtypes, {safe:true}, function(err, result) {});
	});

};

commonService.prepareCheckCollection('subtypes', populateDB);