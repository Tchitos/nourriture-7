nourritureApp.factory('loginService', ['$rootScope', '$http', 'httpService', '$location', function($rootScope, $http, httpService, $location) {

	var loginServiceInstance = {

		'login': loginFunction,
		'logout': logoutFunction,
		'getUser': getUserFunction,
		'stillLogged': stillLoggedFunction
	};

	function loginFunction(username, password) {

		var data = {
			'username': username,
			'password': password
		};
		var url = httpService.makeUrl('/login');

		$http.post(url, data).then(function(response) {

			$rootScope.user = response.data;
			$location.path('/');
		}, httpService.httpError);
	};

	function logoutFunction() {

		var url = httpService.makeUrl('/logout');

		console.log('logout');

		$http.get(url).then(function(response) {

			console.log('logout done');
			delete $rootScope.user;
			$location.path('/');
		}, httpService.httpError);
	}

	function getUserFunction() {

		return $rootScope.user;
	}

	function stillLoggedFunction() {

		var url = httpService.makeUrl('/stilllogged');

		$http.get(url).then(function(response) {

			if (response.data != 'no')
				$rootScope.user = response.data;
			else
				$rootScope.user = null;
		}, httpService.httpError);
	}

	return loginServiceInstance;
}]);