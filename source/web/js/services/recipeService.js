nourritureApp.factory('recipeService', ['$http', 'httpService', '$location', function($http, httpService, $location) {

	var recipeServiceInstance = {

		'addRecipe': addRecipeFunction,
		'getRecipes': getRecipesFunction,
		'getMyRecipes': getMyRecipesFunction,
		'getRecipeByName': getRecipeByNameFunction
	};

	function addRecipeFunction(recipeName, recipeDesc, recipeTips, equipements, ingredients, steps, cb) {

		var data = {
			'recipeName': recipeName,
			'recipeDesc': recipeDesc,
			'recipeTips': recipeTips,
			'equipements': equipements,
			'ingredients': ingredients,
			'steps': steps,
		};
		var url = httpService.makeUrl('/recipe/add');

		$http.post(url, data).then(function(response) {

			cb(response);
		}, httpService.httpError);
	}

	function getRecipesFunction(cb) {

		var url = httpService.makeUrl('/getRecipes');

		$http.get(url).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function getMyRecipesFunction(cb) {

		var url = httpService.makeUrl('/getMyRecipes');

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