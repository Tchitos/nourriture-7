nourritureApp.factory('recipeService', ['$http', 'httpService', '$location', function($http, httpService, $location) {

	var recipeServiceInstance = {

		'getRecipes': getRecipesFunction,
		'getRecipeByName': getRecipeByNameFunction
	};

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