nourritureApp.controller('IngredientsController', ['$scope', 'viewName', 'loginService', function ($scope, viewName, loginService) {

	$scope.view = viewName;
	$scope.tab = 'ingredients';
	loginService.init($scope);

}]);