nourritureApp.factory('loginService', ['$http', function($http) {

	var loginServiceInstance = {

		'login': loginFunction,
		'logout': logoutFunction
	};

	function loginFunction(login, password) {

		var data = {
			'login': login,
			'password': password
		};

		$http.post('/api/login', data).then(function(response) {


			console.log(response);
		}, function(response) {

			console.log("ERROR");
			console.log(response);
		});
	};

	return loginServiceInstance;
}]);