nourritureApp.controller('WelcomeController', ['$scope', 'viewName', 'loginService', function ($scope, viewName, loginService) {

	$scope.view = viewName;
	$scope.tab = 'home';
	loginService.init($scope);
	$scope.viewScope = {};

}]);