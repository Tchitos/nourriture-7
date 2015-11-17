var nourritureApp = angular.module('nourritureApp', ['ngRoute']);

nourritureApp.config(function ($routeProvider) {

	$routeProvider
		.when('/',
		{
			controller: 'WelcomeController',
			templateUrl: '/views/Welcome.html'
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
		.otherwise({redirectTo: '/'});
});