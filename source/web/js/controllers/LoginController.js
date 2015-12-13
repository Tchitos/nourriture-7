nourritureApp.controller('LoginController', ['$scope', 'viewName', '$cookies', 'loginService', function($scope, viewName, $cookies, loginService) {

	$scope.view = viewName;	
	$scope.tab = 'ingredients';
	$scope.viewScope = {};
	$scope.user = loginService.getUser();

	$scope.makeLogin = function() {

		username = $scope.viewScope.username;
		password = $scope.viewScope.password;

		loginService.login(username, password);
	}

	$scope.logout = function() {

		loginService.logout();
	}

}]);