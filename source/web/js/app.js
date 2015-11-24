var nourritureApp = angular.module('nourritureApp', ['ngRoute', 'ngCookies']);

nourritureApp.config(function ($routeProvider) {

	$routeProvider
		.when('/',
		{
			controller: 'WelcomeController',
			templateUrl: '/views/welcome.html'
		})
		.when('/login',
		{
			controller: 'LoginController',
			templateUrl: '/views/login.html'
		})
		.when('/register',
		{
			controller: 'RegistrationController',
			templateUrl: '/views/register.html'
		})
		.when('/recipes',
		{
			controller: 'RecipesController',
			templateUrl: '/views/recipes.html'
		})
		.when('/ingredients',
		{
			controller: 'IngredientsController',
			templateUrl: '/views/ingredients.html'
		})
		.when('/recipes_detail',
		{
			controller: 'RecipeDetailController',
			templateUrl: '/views/recipe_detail.html'
		})
		.when('/ingredient_detail',
		{
			controller: 'IngredientDetailController',
			templateUrl: '/views/ingredient_detail.html'
		})
		.otherwise({redirectTo: '/'});
});
