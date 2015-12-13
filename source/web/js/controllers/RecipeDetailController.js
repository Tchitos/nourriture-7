nourritureApp.controller('RecipeDetailController', ['$scope', '$routeParams', 'viewName', 'recipeService', function ($scope, $routeParams, viewName, recipeService) {

	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {};
	var recipeName = $routeParams.recipeName;

	console.log($routeParams.recipeName);

	recipeService.getRecipeByName(recipeName, function(recipe) {
		console.log(recipe);
		$scope.viewScope.recipe = recipe;
	});

}]);