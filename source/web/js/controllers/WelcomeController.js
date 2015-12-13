nourritureApp.controller('WelcomeController', ['$scope', 'viewName', function ($scope, viewName) {

	$scope.view = viewName;
	$scope.tab = 'home';

}]);