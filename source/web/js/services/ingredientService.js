nourritureApp.factory('ingredientService', ['$http', 'httpService', 'Upload', function($http, httpService, Upload) {

	var ingredientServiceInstance = {

		'getIngredients': getIngredientsFunction,
		'addIngredient': addIngredientFunction
	};

	function getIngredientsFunction(cb) {

		var url = httpService.makeUrl('/getIngredients');

		$http.get(url).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function addIngredientFunction(ingredient, cb) {

		var url = httpService.makeUrl('/ingredient/add');

		var upload = Upload.upload({
		  url: url,
		  data: {photo: ingredient['Photo'], name: ingredient['Name']},
		});

		upload.then(function(response) {

			cb(response);
		}, httpService.httpError);
	}

	return ingredientServiceInstance;
}]);
