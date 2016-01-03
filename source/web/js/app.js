var nourritureApp = angular.module('nourritureApp', ['ngRoute', 'ngMaterial', 'ngCookies', 'ngFileUpload']);

nourritureApp.run(['$rootScope', 'loginService', function($rootScope, loginService) {

	$rootScope.user = loginService.stillLogged();
}]);

nourritureApp.config(function ($routeProvider) {

	$routeProvider
		.when('/',
		{
			controller: 'WelcomeController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/welcome.html'	
				}
			}
		})
		.when('/login',
		{
			controller: 'LoginController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/user/login.html'	
				}
			}
		})
		.when('/register',
		{
			controller: 'RegistrationController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/user/register.html'	
				}
			}
		})
		.when('/account',
		{
			controller: 'AccountController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/user/account.html';
				}
			}
		})
		.when('/recipes',
		{
			controller: 'RecipesController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/recipes/recipes.html'	
				}
			}
		})
		.when('/recipes/add',
		{
			controller: 'AddRecipeController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/recipes/add.html'	
				}
			}
		})
		.when('/recipes/mine',
		{
			controller: 'MyRecipeController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/recipes/mine.html'	
				}
			}
		})
		.when('/recipes/edit/:recipeName',
		{
			controller: 'EditRecipeController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/recipes/edit.html'	
				}
			}
		})
		.when('/ingredients',
		{
			controller: 'IngredientsController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/ingredients/ingredients.html'	
				}
			}
		})
		.when('/ingredients/add',
		{
			controller: 'IngredientsController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/ingredients/add.html'	
				}
			}
		})
		.when('/recipes_detail/:recipeName',
		{
			controller: 'RecipeDetailController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/recipes/recipe_detail.html'	
				}
			}
		})
		.when('/ingredient_detail',
		{
			controller: 'IngredientDetailController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/ingredients/ingredient_detail.html'	
				}
			}
		})
		.when('/nutritions',
		{
			controller: 'NutritionsController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/nutrition/nutritions.html'	
				}
			}
		})
		.when('/nutrition_detail/:nutritionName',
		{
			controller: 'NutritionDetailController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/nutrition/nutrition_detail.html'	
				}
			}
		})
		.otherwise({redirectTo: '/'});
});
