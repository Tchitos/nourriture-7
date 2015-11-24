nourritureApp.factory('loginService', ['$http', 'httpService', function($http, httpService) {

	var loginServiceInstance = {

		'login': loginFunction,
		'logout': logoutFunction
	};

	function loginFunction(login, password) {

		var data = {
			'login': login,
			'password': password
		};
		var url = httpService.makeUrl('/login');

		$http.post(url, data).then(function(response) {

			console.log(response);
		}, httpService.httpError);
	};

	function logoutFunction() {

		var url = httpService.makeUrl('/api/logout');

		$http.get(url).then(function(response) {

			console.log(response);
		}, httpService.httpError);
	}

	return loginServiceInstance;
}]);