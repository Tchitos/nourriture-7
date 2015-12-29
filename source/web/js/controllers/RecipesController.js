nourritureApp.controller('RecipesController', ['$scope', 'viewName', 'loginService', 'recipeService', 'typeService', function ($scope, viewName, loginService, recipeService, typeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {
		recipes: [],
		types: [],
		perPage: 8,
		offset: 0
	};

	recipeService.getRecipes(function(recipes) {
		$scope.viewScope.recipes = recipes;
	});

	typeService.getTypes(function(types) {
		$scope.viewScope.types = types;
	});
}]);
