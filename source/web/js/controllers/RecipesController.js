nourritureApp.controller('RecipesController', ['$scope', '$routeParams', 'viewName', 'loginService', 'recipeService', 'typeService', function ($scope, $routeParams, viewName, loginService, recipeService, typeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {
		recipes: [],
		types: [],
		perPage: 8,
		offset: 0
	};

	if (!$routeParams || !$routeParams.subtype) {
		recipeService.getRecipes(function(recipes) {
			$scope.viewScope.recipes = recipes;
		});
	} else {
		recipeService.getRecipesBySubtype($routeParams.subtype, function(recipes) {
			$scope.viewScope.recipes = recipes;
		});		
	}

	typeService.getTypes(function(types) {
		
		if (types == 'No types found.')
			return;
		$scope.viewScope.types = types;
	});
}]);
