var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;

module.exports.add = function(ingredientName, cb) {

	db.collection('ingredients', function(err, collection) {

		ingredient = {
			'ingredientName': ingredientName
		};

		collection.insert(ingredient, {safe:true}, function(err, result) {
            if (err)
				return cb(); 
			return cb(null, result.ops[0]);
        });
	});
}

module.exports.fetchAll = function(cb) {

	db.collection('ingredients', function(err, collection) {

		collection.find().toArray(function(err, ingredients) {

			if (err)
				return cb();   

			return cb(null, ingredients);
		});
	});
};

module.exports.fetchByIds = function(ids, cb) {

	db.collection('ingredients', function(err, collection) {

		collection.find({"_id": {$in: ids}}).toArray(function(err, ingredients) {

			if (err)
				return cb();   

			return cb(null, ingredients);
		});
	});
};

module.exports.fetchByRecipeIngredients = function(recipeIngredients, cb) {

	var ids = [];

	for (index in recipeIngredients) {
		ids.push(recipeIngredients[index].ingredient);
	}

	db.collection('ingredients', function(err, collection) {

		collection.find({"_id": {$in: ids}}).toArray(function(err, ingredients) {

			if (err)
				return cb();   

			for (index1 in ingredients) {
				for (index2 in recipeIngredients) {
					if (ingredients[index1]['_id'].toString() == recipeIngredients[index2].ingredient.toString()) {
						recipeIngredients[index2].ingredient = ingredients[index1];
						break;
					}
				}
			}

			return cb(null, recipeIngredients);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('ingredients', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, ingredient) {

			if (err)
				return cb();   

			return cb(null, ingredient);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('ingredients', function(err, collection) {

		collection.findOne({'name':name}, function(err, ingredient) {

			if (err)
				return cb();

			return cb(null, ingredient);
		});
	});
};
