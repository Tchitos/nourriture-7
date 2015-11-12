var nourritureApp = angular.module('nourritureApp', ['ngRoute']);

nourritureApp.config(function ($routeProvider) {

	$routeProvider
		.when('/',
		{
			controller: 'WelcomeController',
			templateUrl: '/views/Welcome.html'
		})
		.otherwise({redirectTo: '/'});
});