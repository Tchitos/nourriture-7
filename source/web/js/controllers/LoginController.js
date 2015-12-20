nourritureApp.controller('LoginController', ['$scope', 'viewName', 'loginService', function($scope, viewName, loginService) {

	$scope.view = viewName;	
	$scope.tab = 'login';
	loginService.init($scope);
	$scope.viewScope = {};

	$scope.makeLogin = function() {

		var username = $scope.viewScope.username;
		var password = $scope.viewScope.password;

		loginService.login(username, password);
	}

}]);