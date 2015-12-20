nourritureApp.controller('AccountController', ['$scope', 'viewName', 'loginService', function($scope, viewName, loginService) {

	$scope.view = viewName;	
	$scope.tab = 'account';
	loginService.init($scope);
	$scope.viewScope = {};

}]);