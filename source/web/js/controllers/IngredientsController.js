nourritureApp.controller('IngredientsController', ['$scope', 'viewName', 'loginService', 'ingredientService', function ($scope, viewName, loginService, ingredientService) {

	$scope.view = viewName;
	$scope.tab = 'ingredients';
	loginService.init($scope);
	$scope.viewScope = {};

	$scope.addIngredient = function() {

		var ingredientName = $scope.viewScope.ingredientName;

		ingredientService.addIngredient(ingredientName, function(response) {
			$scope.viewScope.ingredientName = '';
			$scope.viewScope.message = 'Ingredient added';
		});
	}

}]);