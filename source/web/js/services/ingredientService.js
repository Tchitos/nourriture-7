nourritureApp.factory('ingredientService', ['$http', 'httpService', function($http, httpService) {

	var ingredientServiceInstance = {

		'getIngredients': getIngredientsFunction,
		'addIngredient': addIngredientFunction
	};

	function getIngredientsFunction(cb) {

		var url = httpService.makeUrl('/ingredients');

		$http.get(url).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function addIngredientFunction(ingredientName, cb) {

		var data = {
			'ingredientName': ingredientName
		}
		var url = httpService.makeUrl('/ingredient/add');

		$http.post(url, data).then(function(response) {

			cb(response);
		}, httpService.httpError);
	}

	return ingredientServiceInstance;
}]);