nourritureApp.controller('NutritionDetailController', ['$scope', '$routeParams', 'viewName', 'loginService', 'nutritionService', function ($scope, $routeParams, viewName, loginService, nutritionService) {

	$scope.view = viewName;
	$scope.tab = 'nutritions';
	loginService.init($scope);
	$scope.viewScope = {};
	var nutritionName = $routeParams.nutritionName;

	nutritionService.getNutritionByName(nutritionName, function(nutrition) {
		$scope.viewScope.nutrition = nutrition;
	});

}]);