nourritureApp.factory('typeService', ['$http', 'httpService', '$location', function($http, httpService, $location) {

	var typeServiceInstance = {

		'getTypes': getTypesFunction,
		'getTypeByName': getTypeByNameFunction
	};

	function getTypesFunction(cb) {

		var url = httpService.makeUrl('/getTypesDetails');

		$http.get(url).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function getTypeByNameFunction(name, cb) {

		var data = {
			'name': name
		};
		var url = httpService.makeUrl('/getTypeByName');

		$http.post(url, data).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	return typeServiceInstance;
}]);