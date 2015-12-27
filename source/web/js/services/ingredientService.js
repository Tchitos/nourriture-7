nourritureApp.factory('ingredientService', ['$http', 'httpService', function($http, httpService) {

	var ingredientServiceInstance = {

		'addIngredient': addIngredientFunction
	};

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