var mongo = require('mongodb');
var commonService = require('../../routes/commonService');
var tokenModel = require('./token');
var db = commonService.db;
var recipeIngredientModel = require('./recipeIngredient');

module.exports.add = function(userId, recipeName, recipePhoto, recipeDesc, recipeTips, equipements, ingredients, steps, cb) {

	db.collection('recipes', function(err, collection) {

		var recipe = {
			'author': new mongo.ObjectID(userId),
			'name': recipeName,
			'image': recipePhoto,
			'description': recipeDesc,
			'tips': recipeTips,
			'steps': steps,
			'users': [],
			'equipments': equipements
		};

		collection.insert(recipe, {safe:true}, function(err, result) {
            if (err)
				return cb(err);

			var recipeId = result.ops[0]._id;


			recipeIngredientModel.add(recipeId, ingredients, function(recipeIngredientsIds) {

				var recipeIngredients = [];
				for (var i in recipeIngredientsIds) {
					recipeIngredients.push(new mongo.ObjectID(recipeIngredientsIds[i]));
				}
				collection.update({_id: recipeId}, {$set: {'recipeIngredients': recipeIngredients}}, {safe: true}, function(err, result) {
					if (err)
						return cb(err);

					return cb();
				});
			});
        });
	});
}

module.exports.delete = function(name, cb) {

	db.collection('recipes', function(err, collection) {
	
		collection.remove({'name': name}, {safe:true}, function(err, result) {
			if (err)
				return cb(err);
			return cb();
		})
	});
}

module.exports.fetchByAuthor = function(userId, cb) {

	db.collection('recipes', function(err, collection) {

		collection.find({'author': new mongo.ObjectID(userId)}).toArray(function(err, recipe) {

			if (err)
				return cb(err);
			return cb(null, recipe);
		});
	});
};

module.exports.fetchAll = function(skip, limit, cb) {

	db.collection('recipes', function(err, collection) {

		collection.find().skip(skip).limit(limit).toArray(function(err, recipes) {

			if (err)
				return cb(err);   

			return cb(null, recipes);
		});
	});
};

module.exports.fetchById = function(id, cb) {

	db.collection('recipes', function(err, collection) {

		collection.findOne({'_id': new mongo.ObjectId(id)}, function(err, recipe) {

			if (err)
				return cb(err);   

			return cb(null, recipe);
		});
	});
};

module.exports.fetchByName = function(name, cb) {

	db.collection('recipes', function(err, collection) {

		collection.findOne({'name':name}, function(err, recipe) {

			if (err)
				return cb(err);

			return cb(null, recipe);
		});
	});
};

module.exports.countAll = function(cb) {

	db.collection('recipes', function(err, collection) {

		collection.count(function(err, nbRecipes) {

			if (err)
				return cb(err);   

			return cb(null, nbRecipes);
		});
	});
};

module.exports.fetchByIds = function(ids, cb) {

	db.collection('recipes', function(err, collection) {

		collection.find({"_id": {$in: ids}}).toArray(function(err, recipes) {

			if (err)
				return cb();   

			return cb(null, recipes);
		});
	});
};

module.exports.fetchBySearch = function(search, cb) {

	db.collection('recipes', function(err, collection) {

		collection.find({'name': new RegExp(search, 'i')}).toArray(function(err, recipes) {

			if (err)
				return cb();

			return cb(null, recipes);
		});
	});
};

module.exports.fetchBySubtypes = function(subtypeIds, cb) {

	db.collection('recipes', function(err, collection) {

		collection.find({"subtypes": {$in: subtypeIds}}).toArray(function(err, recipes) {

			if (err)
				return cb();

			return cb(null, recipes);
		});
	});
};
