nourritureApp.factory('recipeService', ['$http', 'httpService', '$location', 'Upload', function($http, httpService, $location, Upload) {

	var recipeServiceInstance = {

		'addRecipe': addRecipeFunction,
		'deleteRecipe': deleteRecipeFunction,
		'getRecipes': getRecipesFunction,
		'getMyRecipes': getMyRecipesFunction,
		'getRecipeByName': getRecipeByNameFunction,
		'getRecipesBySubtype': getRecipesBySubtypeFunction
	};

	function addRecipeFunction(recipeName, recipePhoto, recipeDesc, recipeTips, equipements, ingredients, steps, stepsPhoto, cb) {

		var data = {
			'recipeName': recipeName,
			'recipePhoto': recipePhoto,
			'recipeDesc': recipeDesc,
			'recipeTips': recipeTips,
			'equipements': equipements,
			'ingredients': ingredients,
			'stepsPhoto': stepsPhoto,
			'steps': steps,
		};
		var url = httpService.makeUrl('/recipe/add');

		var upload = Upload.upload({
		  url: url,
		  data: data,
		});

		upload.then(function(response) {

			cb(response);
		}, httpService.httpError);

		// $http.post(url, data).then(function(response) {

		// 	cb(response);
		// }, httpService.httpError);
	}

	function deleteRecipeFunction(name, cb) {

		var url = httpService.makeUrl('/recipe/delete');
		
		var data = {
			'name': name
		};

		$http.post(url, data).then(function(response) {

			cb(response);
		}, httpService.httpError);
	}

	function getRecipesFunction(cb) {

		var url = httpService.makeUrl('/getRecipes');

		$http.get(url).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function getMyRecipesFunction(cb) {

		var url = httpService.makeUrl('/getMyRecipes');

		$http.get(url).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function getRecipeByNameFunction(name, cb) {

		var data = {
			'name': name
		};
		var url = httpService.makeUrl('/getRecipeByName');

		$http.post(url, data).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	function getRecipesBySubtypeFunction(subtype, cb) {

		var data = {
			'search': subtype
		};
		var url = httpService.makeUrl('/getRecipesBySubtype');

		$http.post(url, data).then(function(response) {

			cb(response.data);
		}, httpService.httpError);
	}

	return recipeServiceInstance;
}]);