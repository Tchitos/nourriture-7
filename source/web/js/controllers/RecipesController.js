nourritureApp.controller('RecipesController', ['$scope', 'viewName', 'recipeService', function ($scope, viewName, recipeService) {

	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {};

	recipeService.getRecipes(function(recipes) {
		console.log(recipes);
		$scope.viewScope.recipes = recipes;
	});
}]);
