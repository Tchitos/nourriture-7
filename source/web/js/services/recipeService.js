nourritureApp.factory('recipeService', ['$http', 'httpService', '$location', 'Upload', function($http, httpService, $location, Upload) {

	var recipeServiceInstance = {

		'addRecipe': addRecipeFunction,
		'getRecipes': getRecipesFunction,
		'getRecipeByName': getRecipeByNameFunction
	};

	function addRecipeFunction(recipeName, recipePhoto, recipeDesc, recipeTips, equipements, ingredients, steps, stepsPhoto, cb) {

		var data = {
			'recipeName': recipeName,
			'recipePhoto': recipePhoto,
			'recipeDesc': recipeDesc,
			'recipeTips': recipeTips,
			'equipements': equipements,
			'ingredients': ingredients,
			'stepsPhoto': stepsPhoto,
			'steps': steps,
		};
		var url = httpService.makeUrl('/recipe/add');

		var upload = Upload.upload({
		  url: url,
		  data: data,
		});

		upload.then(function(response) {

			cb(response);
		}, httpService.httpError);

		// $http.post(url, data).then(function(response) {

		// 	cb(response);
		// }, httpService.httpError);
	}

	function getRecipesFunction(cb) {

		var url = httpService.makeUrl('/getRecipes');

		$http.get(url).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function getRecipeByNameFunction(name, cb) {

		var data = {
			'name': name
		};
		var url = httpService.makeUrl('/getRecipeByName');

		$http.post(url, data).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	return recipeServiceInstance;
}]);