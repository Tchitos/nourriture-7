nourritureApp.controller('AddRecipeController', ['$scope', 'viewName', 'loginService', 'recipeService', 'ingredientService', 'typeService', function ($scope, viewName, loginService, recipeService, ingredientService, typeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {
		ingredients: [],
	};

	ingredientService.getIngredients(function(ingredients) {
		
		$scope.viewScope.ingredients = [];
		for (var i in ingredients) {
			$scope.viewScope.ingredients.push = {
				value: ingredients[i].name.toLowerCase(),
          		display: ingredients[i].name
			};
		}
	});

	$scope.newIngredient = function(state) {
    	alert("Sorry! You'll need to create a Constituion for " + state + " first!");
    }

    $scope.querySearch = function(query) {

    	var results = query ? $scope.viewScope.ingredients.filter(createFilterFor(query)) : $scope.viewScope.ingredients, deferred;
    	if (self.simulateQuery) {
			deferred = $q.defer();
        	$timeout(function () { deferred.resolve( results ); }, Math.random() * 1000, false);
        	return deferred.promise;
      	} else {
        	return results;
      	}
    }

    function createFilterFor(query) {
    	var lowercaseQuery = angular.lowercase(query);
    	return function filterFn(state) {
        	return (state.value.indexOf(lowercaseQuery) === 0);
      	};
    }
}]);
