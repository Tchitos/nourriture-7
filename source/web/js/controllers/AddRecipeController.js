nourritureApp.controller('AddRecipeController', ['$scope', 'viewName', 'loginService', 'recipeService', 'ingredientService', 'typeService', function ($scope, viewName, loginService, recipeService, ingredientService, typeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {
		ingredients: [],
	};

	ingredientService.getIngredients(function(ingredients) {
		$scope.viewScope.ingredients = ingredients;
	});
}]);
