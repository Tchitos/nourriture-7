nourritureApp.factory('httpService', ['httpConfig', function(httpConfig) {

	var httpServiceInstance = {

		'makeUrl': makeUrlFunction,
		'httpError': httpErrorFunction
	};

	function makeUrlFunction(url) {

		return httpConfig.url + ':' + httpConfig.port + url;
	}

	function httpErrorFunction(response) {

		console.log("ERROR");
		console.log(response);
	};

	return httpServiceInstance;
}]);