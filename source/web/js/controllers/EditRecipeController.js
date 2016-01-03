nourritureApp.controller('EditRecipeController', ['$scope', '$filter', '$routeParams','viewName', 'loginService', 'recipeService', 'ingredientService', 'typeService',
	function ($scope, $filter, $routeParams, viewName, loginService, recipeService, ingredientService, typeService) {

	loginService.init($scope);
	$scope.view = viewName;
	$scope.tab = 'recipes';
	$scope.viewScope = {};
	var recipeName = $routeParams.recipeName;

	recipeService.getRecipeByName(recipeName, function(recipe) {

		initViewScope(recipe);
	});

	function initViewScope(recipe) {

		console.log(recipe);

		$scope.viewScope = {
			ingredients: [],
			recipeName: recipe.name,
			recipeDesc: recipe.description,
			recipeTips: recipe.tips,
			equipements: recipe.equipments,
			steps: recipe.steps,
			mainIngredients: [
				{
					'searchText': '',
					'quantity': ''
				}
			],
			subIngredients: [
				{
					'searchText': '',
					'quantity': ''
				}
			]
		};

		getAllIngredients();
	}

	function getAllIngredients() {
		ingredientService.getIngredients(function(ingredients) {
		
			$scope.viewScope.ingredients = [];
			for (var i in ingredients) {
				$scope.viewScope.ingredients.push({
					id: ingredients[i]._id,
					value: ingredients[i].name.toLowerCase(),
					display: ingredients[i].name
				});
			}
		});
	}

	$scope.addRecipe = function() {

		var recipeName = $scope.viewScope.recipeName;
		var recipeDesc = $scope.viewScope.recipeDesc;
		var recipeTips = $scope.viewScope.recipeTips;
		var equipements = $filter('json')($scope.viewScope.equipements);

		var ingredients = [];

		for (var i in $scope.viewScope.mainIngredients) {
			if ($scope.viewScope.mainIngredients[i].id) {
				ingredients.push({
					'ingredient': $scope.viewScope.mainIngredients[i].id,
					'mandatory': true,
					'quantity': $scope.viewScope.mainIngredients[i].quantity
				});
			}
		}

		for (var i in $scope.viewScope.subIngredients) {
			if ($scope.viewScope.subIngredients[i].id) {        
				ingredients.push({
					'ingredient': $scope.viewScope.subIngredients[i].id,
					'mandatory': false,
					'quantity': $scope.viewScope.subIngredients[i].quantity
				});
			}
		}

		ingredients = $filter('json')(ingredients);
		
		var steps = [];

		for (var i in $scope.viewScope.steps) {

			steps.push({
				'level': parseInt(i) + 1,
				'text': $scope.viewScope.steps[i].text
			});
		};

		steps = $filter('json')(steps);

		recipeService.addRecipe(recipeName, recipeDesc, recipeTips, equipements, ingredients, steps, function(response) {

			if (response.data == "Missing parameters")
				return;
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

	$scope.removeEquipement = function(id) {

		$scope.viewScope.equipements.splice(id, 1);
	}

	$scope.addEquipement = function() {

		$scope.viewScope.equipements.push({'name': ''});
	}

	$scope.removeMainIngredient = function(id) {

		$scope.viewScope.mainIngredients.splice(id, 1);
	}

	$scope.addMainIngredient = function() {

		$scope.viewScope.mainIngredients.push({'searchText': ''});
	}

	$scope.selectedMainIngredientChange = function(item, key) {

		if (item && item.id)
			$scope.viewScope.mainIngredients[key].id = item.id;
	}

	$scope.removeSubIngredient = function(id) {

		$scope.viewScope.subIngredients.splice(id, 1);
	}

	$scope.addSubIngredient = function() {

		$scope.viewScope.subIngredients.push({'searchText': ''});
	}

	$scope.selectedSubIngredientChange = function(item, key) {

		if (item && item.id)
			$scope.viewScope.subIngredients[key].id = item.id;
	}

	$scope.removeStep = function(id) {

		$scope.viewScope.steps.splice(id, 1);
	}

	$scope.addStep = function() {

		$scope.viewScope.steps.push({'text': ''});
	}
}]);