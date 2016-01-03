var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);

var populateDB = function() {

		var defaultRecipeIngredients = [
			{
				'_id': new mongo.ObjectID("566d7541e1b9caac0dac79fa"),
				'recipe': new mongo.ObjectID("566d6ea4e1b9caac0dac79ee"),
				'ingredient': new mongo.ObjectID("566d6f26e1b9caac0dac79f1")
			},
			{
				'_id': new mongo.ObjectID("566d7568e1b9caac0dac79fb"),
				'recipe': new mongo.ObjectID("566d6ea4e1b9caac0dac79ee"),
				'ingredient': new mongo.ObjectID("566d6f51e1b9caac0dac79f2")
			},
			{
				'_id': new mongo.ObjectID("566d75a3e1b9caac0dac79fc"),
				'recipe': new mongo.ObjectID("566d6ea4e1b9caac0dac79ee"),
				'ingredient': new mongo.ObjectID("566d6f60e1b9caac0dac79f3")
			},
		];


	db.collection('recipeIngredients', function(err, collection) {
		collection.insert(defaultRecipeIngredients, {safe:true}, function(err, result) {});
	});

};

commonService.prepareCheckCollection('recipeIngredients', populateDB);