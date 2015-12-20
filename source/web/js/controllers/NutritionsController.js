nourritureApp.controller('NutritionsController', ['$scope', 'viewName', 'loginService', function ($scope, viewName, loginService) {

	$scope.view = viewName;
	$scope.tab = 'nutrition';
	loginService.init($scope);

}]);