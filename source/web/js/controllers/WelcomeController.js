nourritureApp.controller('WelcomeController', ['$scope', 'viewName', 'loginService', function ($scope, viewName, loginService) {

	$scope.view = viewName;
	$scope.tab = 'home';
	$scope.user = loginService.getUser();
	$scope.viewScope = {};

	$scope.viewScope.dataSauterelles = [
		{
			'name': 'Jimmy',
			'color': 'green'
		},
		{
			'name': 'James',
			'color': 'bleue'
		},
	];

	$scope.logout = function() {

		loginService.logout();
		$scope.user = null;
	}

}]);