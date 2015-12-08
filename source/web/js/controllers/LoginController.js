nourritureApp.controller('LoginController', ['$scope', '$cookies', 'loginService', function($scope, $cookies, loginService) {
	
	$scope.makeLogin = function() {

		username = $scope.username;
		password = $scope.password;

		loginService.login(username, password);
	}

}]);