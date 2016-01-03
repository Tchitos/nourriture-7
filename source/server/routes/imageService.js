var mongo           = require('mongodb');
var commonService   = require('./commonService');
var BSON            = mongo.BSONPure;
var db              = commonService.db;
var ObjectId        = require('mongodb').ObjectID;
var TYPE            = 'mongodb';
var model           = require('../model/' + TYPE);
var fs              = require('fs-extra');
var getPath         = require('path');

exports.findImageById = function(req, res) {
	var imageId = req.params.imageId;

	console.log('Get an image: ' + imageId);

	if (imageId.length < 12) {
		sendDefaultImage(res);
		return;
	}

	model.image.fetchById(imageId, function(err, image) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (image == null) {
			sendDefaultImage(res);
		} else {
			sendImage(image.path, image.size, image.mimetype, res);
		}
	});
};

exports.findImageByName = function(req, res) {
	var imageName = req.params.imageName;
	console.log('Get an image: ' + imageName);

	model.image.fetchByName(imageName, function(err, image) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (image == null)
			sendDefaultImage(res);
		else {
			sendImage(image.path, image.size, image.mimetype, res);
		}
	});
};

function sendImage(path, size, type, res) {
	res.sendFile(path, {
		headers: {
			'Accept-Ranges':	'bytes',
			'Cache-Control':	'max-age=0',
			'Connection': 		'keep-alive',
			'Content-length':	size,
			'Content-type':		type,
			'x-timestamp':		Date.now(),
			'x-sent': true
		}
	});
}

function sendDefaultImage(res) {
	res.sendFile(getPath.resolve(process.cwd(), './uploads/default.png'), {
		headers: {
			'Accept-Ranges':	'bytes',
			'Cache-Control':	'max-age=0',
			'Connection': 		'keep-alive',
			'Content-length':	4924,
			'Content-type':		'image/png',
			'x-timestamp':		Date.now(),
			'x-sent':			true
		}
	});
}

exports.findAllImages = function(req, res) {
	model.image.fetchAll(function(err, images) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (images.length == 0)
			res.status(201).send('No types found.');
		else {
			res.send(images);
		}
	});
};

exports.deleteImage = function(req, res) {
	var id = req.params.id;
	console.log('Deleting image: ' + id);
	// db.collection('images', function(err, collection) {
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

	var images = [
		{
			"_id": new mongo.ObjectID("56878925b27aadea3497773a"),
			"name": "bread.jpg",
			"path": "/var/www/nourriture-7/source/server/uploads/7a509b4a05aef2cc55450cb35f5ff4fb",
			"mimetype": "image/jpeg",
			"size": 14934,
		},
	];

	db.collection('images', function(err, collection) {
		collection.insert(images, {safe:true}, function(err, result) {});
	});

};

commonService.prepareCheckCollection('images', populateDB);