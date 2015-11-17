nourritureApp.controller('LoginController', ['$scope', '$cookies', function($scope, $cookies) {
	
	$scope.login = function() {

		console.log($scope.password);
	}

	//$cookies.put('token', '123456');

}]);