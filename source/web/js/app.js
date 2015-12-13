var nourritureApp = angular.module('nourritureApp', ['ngRoute', 'ngCookies']);

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
					return '/views/login.html'	
				}
			}
		})
		.when('/register',
		{
			controller: 'RegistrationController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/register.html'	
				}
			}
		})
		.when('/recipes',
		{
			controller: 'RecipesController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/recipes.html'	
				}
			}
		})
		.when('/ingredients',
		{
			controller: 'IngredientsController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/ingredients.html'	
				}
			}
		})
		.when('/recipes_detail',
		{
			controller: 'RecipeDetailController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/recipe_detail.html'	
				}
			}
		})
		.when('/ingredient_detail',
		{
			controller: 'IngredientDetailController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/ingredient_detail.html'	
				}
			}
		})
		.when('/nutritions',
		{
			controller: 'NutritionsController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/nutritions.html'	
				}
			}
		})
		.when('/nutrition_detail',
		{
			controller: 'NutritionDetailController',
			templateUrl: '/views/template/default.html',
			resolve: {
				viewName: function() {
					return '/views/nutrition_detail.html'	
				}
			}
		})
		.otherwise({redirectTo: '/'});
});
