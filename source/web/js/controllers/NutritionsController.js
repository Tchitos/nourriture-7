nourritureApp.controller('NutritionsController', ['$scope', 'viewName', 'loginService', 'nutritionService', function ($scope, viewName, loginService, nutritionService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'nutritions';
	$scope.viewScope = {
		nutritions: [],
		types: [],
		perPage: 8,
		offset: 0
	};

	nutritionService.getNutritions(function(nutritions) {
		$scope.viewScope.nutritions = nutritions;
	});
}]);
