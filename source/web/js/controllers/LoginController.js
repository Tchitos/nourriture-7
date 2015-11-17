nourritureApp.controller('LoginController', ['$scope', '$cookies', 'loginService', function($scope, $cookies, loginService) {
	
	$scope.makeLogin = function() {

		login = $scope.login;
		password = $scope.password;

		loginService.login(login, password);
	}

	//$cookies.put('token', '123456');

}]);