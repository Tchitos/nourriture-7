nourritureApp.controller('AddRecipeController', ['$scope', '$filter', 'viewName', 'loginService', 'recipeService', 'ingredientService', 'typeService',
  function ($scope, $filter, viewName, loginService, recipeService, ingredientService, typeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {};

  function initViewScope() {

    $scope.viewScope = {
      ingredients: [],
      recipeName: '',
      recipeDesc: '',
      recipeTips: '',
      mainIngredients: [
        {'searchText': ''}
      ],
      subIngredients: [
        {'searchText': ''}
      ],
      steps: [
        {
          'title': '',
          'desc': ''
        }
      ]
    };
  }

  initViewScope();

	ingredientService.getIngredients(function(ingredients) {
		
    $scope.viewScope.ingredients = [];
    for (var i in ingredients) {
      $scope.viewScope.ingredients.push({
        value: ingredients[i].name.toLowerCase(),
              display: ingredients[i].name
      });
    }
	});

  $scope.addRecipe = function() {

    var recipeName = $scope.viewScope.recipeName;
    var recipeDesc = $scope.viewScope.recipeDesc;
    var recipeTips = $scope.viewScope.recipeTips;
    var ingredients = [];
    
    for (var i in $scope.viewScope.mainIngredients) {
      if ($scope.viewScope.mainIngredients[i].searchText) {
        ingredients.push({
          'name': $scope.viewScope.mainIngredients[i].searchText,
          'main': true
        });
      }
    }

    for (var i in $scope.viewScope.subIngredients) {
      if ($scope.viewScope.subIngredients[i].searchText) {        
        ingredients.push({
          'name': $scope.viewScope.subIngredients[i].searchText,
          'main': false
        });
      }
    }

    ingredients = $filter('json')(ingredients);
    
    var steps = $filter('json')($scope.viewScope.steps);

    recipeService.addRecipe(recipeName, recipeDesc, recipeTips, ingredients, steps, function() {

      initViewScope();
    });
  }

  $scope.newIngredient = function(state) {
    console.log("Sorry! You'll need to create a Constituion for " + state + " first!");
  }

  $scope.querySearch = function(query) {

  	var results = query ? $scope.viewScope.ingredients.filter(createFilterFor(query)) : $scope.viewScope.ingredients, deferred;
    return results;
  }

  function createFilterFor(query) {
  	var lowercaseQuery = angular.lowercase(query);
  	return function filterFn(state) {
      	return (state.value.indexOf(lowercaseQuery) === 0);
    	};
  }

  $scope.removeMainIngredient = function(id) {

    $scope.viewScope.mainIngredients.splice(id, 1);
  }

  $scope.addMainIngredient = function() {

    $scope.viewScope.mainIngredients.push({'searchText': ''});
  }

  $scope.removeSubIngredient = function(id) {

    $scope.viewScope.subIngredients.splice(id, 1);
  }

  $scope.addSubIngredient = function() {

    $scope.viewScope.subIngredients.push({'searchText': ''});
  }

  $scope.removeStep = function(id) {

    $scope.viewScope.steps.splice(id, 1);
  }

  $scope.addStep = function() {

    $scope.viewScope.steps.push({'searchText': ''});
  }
}]);
