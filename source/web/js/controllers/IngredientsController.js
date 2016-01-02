nourritureApp.controller('IngredientsController', ['$scope', 'viewName', 'loginService', 'ingredientService', function ($scope, viewName, loginService, ingredientService) {

	$scope.view = viewName;
	$scope.tab = 'ingredients';
	loginService.init($scope);
	$scope.viewScope = {};

	$scope.addIngredient = function(file) {

		var ingredient = [];

		ingredientService.addIngredient($scope.viewScope.ingredient, file, function(response) {
			$scope.viewScope.ingredient['Name'] = '';
			$scope.viewScope.ingredient['Photo'] = '';
			$scope.viewScope.message = 'Ingredient added';
		});

	}

}]);