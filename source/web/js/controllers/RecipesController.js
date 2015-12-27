nourritureApp.controller('RecipesController', ['$scope', 'viewName', 'loginService', 'recipeService', 'typeService', function ($scope, viewName, loginService, recipeService, typeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {};

	recipeService.getRecipes(function(recipes) {
		$scope.viewScope.recipes = recipes;
	});

	typeService.getTypes(function(types) {
		$scope.viewScope.types = types;
	});
}]);
