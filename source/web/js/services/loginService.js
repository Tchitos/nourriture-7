nourritureApp.factory('loginService', ['$rootScope', '$http', 'httpService', '$location', function($rootScope, $http, httpService, $location) {

	var loginServiceInstance = {

		'login': loginFunction,
		'register': registerFunction,
		'logout': logoutFunction,
		'getUser': getUserFunction,
		'stillLogged': stillLoggedFunction,
		'init': initFunction
	};

	function loginFunction(username, password) {

		var data = {
			'username': username,
			'password': password
		};
		var url = httpService.makeUrl('/login');

		$http.post(url, data).then(function(response) {

			if (response.data.username || $rootScope.user) {

				if (!$rootScope.user)
					$rootScope.user = response.data;
				$location.path('/');
			}

		}, httpService.httpError);
	};

	function registerFunction(username, email, password) {

		var data = {
			'username': username,
			'email': email,
			'password': password
		};
		var url = httpService.makeUrl('/register');

		$http.post(url, data).then(function(response) {

			if (response.data.username) {	
				$rootScope.user = response.data;
				$location.path('/');
			}
		}, httpService.httpError);
	}

	function logoutFunction() {

		var url = httpService.makeUrl('/logout');

		$http.get(url).then(function(response) {

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

	function initFunction($scope) {

		$scope.user = getUserFunction();
		$scope.logout = function() {
			$scope.user = null;
			logoutFunction();
		}

	}

	return loginServiceInstance;
}]);