nourritureApp.controller('RecipeDetailController', ['$scope', '$routeParams', 'viewName', 'loginService', 'recipeService', function ($scope, $routeParams, viewName, loginService, recipeService) {

	$scope.view = viewName;
	$scope.tab = 'recipes';
	loginService.init($scope);
	$scope.viewScope = {};
	var recipeName = $routeParams.recipeName;

	recipeService.getRecipeByName(recipeName, function(recipe) {
		console.log(recipe);
		$scope.viewScope.recipe = recipe;
	});

}]);