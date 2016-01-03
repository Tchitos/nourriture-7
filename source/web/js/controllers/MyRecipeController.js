nourritureApp.controller('MyRecipeController', ['$scope', 'viewName', 'loginService', 'recipeService',
  function ($scope, viewName, loginService, recipeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {};

	recipeService.getMyRecipes(function(recipes) {
		$scope.viewScope.recipes = recipes;
	});

	$scope.delete = function(key) {

		var recipe = $scope.viewScope.recipes[key];

		recipeService.deleteRecipe(recipe.name);
		$scope.viewScope.recipes.splice(key, 1);
	}

}]);