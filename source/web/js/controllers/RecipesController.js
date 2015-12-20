nourritureApp.controller('RecipesController', ['$scope', 'viewName', 'loginService', 'recipeService', function ($scope, viewName, loginService, recipeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {};

	recipeService.getRecipes(function(recipes) {
		$scope.viewScope.recipes = recipes;
	});
}]);
