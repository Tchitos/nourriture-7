var nourritureApp = angular.module('nourritureApp', ['ngRoute']);

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
			controller: 'RecipiesController',
			templateUrl: '/views/recipes.html'
		})
		.otherwise({redirectTo: '/'});
});
