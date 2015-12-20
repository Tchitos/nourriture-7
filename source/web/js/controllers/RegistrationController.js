nourritureApp.controller('RegistrationController', ['$scope', 'viewName', 'loginService', function ($scope, viewName, loginService) {

	$scope.view = viewName;
	$scope.tab = 'registration';
	loginService.init($scope);
	$scope.viewScope = {};

	$scope.makeRegistration = function() {

		var username = $scope.viewScope.username;
		var email = $scope.viewScope.email;
		var password = $scope.viewScope.password;

		loginService.register(username, email, password);
	}

}]);