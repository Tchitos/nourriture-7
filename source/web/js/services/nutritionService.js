nourritureApp.factory('nutritionService', ['$http', 'httpService', '$location', function($http, httpService, $location) {

	var nutritionServiceInstance = {

		'getNutritions': getNutritionsFunction,
		'getNutritionByName': getNutritionByNameFunction
	};

	function getNutritionsFunction(cb) {

		var url = httpService.makeUrl('/getNutritions');

		$http.get(url).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function getNutritionByNameFunction(name, cb) {

		var data = {
			'name': name
		};
		var url = httpService.makeUrl('/getNutritionByName');

		$http.post(url, data).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	return nutritionServiceInstance;
}]);