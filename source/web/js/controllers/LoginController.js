nourritureApp.controller('LoginController', ['$scope', 'viewName', '$cookies', 'loginService', function($scope, viewName, $cookies, loginService) {

	$scope.view = viewName;	
	$scope.tab = 'ingredients';


	$scope.makeLogin = function() {

		username = $scope.username;
		password = $scope.password;

		loginService.login(username, password);
	}

}]);