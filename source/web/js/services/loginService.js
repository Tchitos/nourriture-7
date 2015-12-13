nourritureApp.factory('loginService', ['$http', 'httpService', '$cookies', function($http, httpService, $cookies) {

	var loginServiceInstance = {

		'login': loginFunction,
		'logout': logoutFunction
	};

	function loginFunction(username, password) {

		$cookies.put('lol', 'lol');

		var data = {
			'username': username,
			'password': password
		};
		var url = httpService.makeUrl('/login');

		$http.post(url, data).then(function(response) {

			console.log(user)
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