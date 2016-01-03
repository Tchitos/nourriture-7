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

	function addIngredientFunction(ingredient, file, cb) {

		var url = httpService.makeUrl('/ingredient/add');

		file.upload = Upload.upload({
		  url: url,
		  data: {photo: file, name: ingredient['Name']},
		});

		file.upload.then(function(response) {

			cb(response);
		}, httpService.httpError);
	}

	return ingredientServiceInstance;
}]);
