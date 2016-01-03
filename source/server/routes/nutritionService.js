var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);

exports.findNutritionByName = function(req, res, next) {

	if (req.body.name === undefined) {
		res.status(401).send('No name given.');
		return;
	}

	var name = req.body.name;

	console.log('Get a nutrition: ' + name);


	model.nutrition.fetchByName(name, function(err, nutrition) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (nutrition == null)
			res.status(201).send('No nutrition found.');
		else
			res.send(nutrition);
	});
};

exports.findAllNutritions = function(req, res) {
	
	console.log('GetNutritions');
	model.nutrition.fetchAll(0, 0, function(err, nutritions) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else if (nutritions.length == 0)
			res.status(201).send('No nutritions found.');
		else
			res.send(nutritions);
	});
};

exports.countAllNutritions = function(req, res) {
	
	console.log('Get Nutritions Count');

	model.nutrition.countAll(function(err, nbNutritions) {
		if (err != null)
			res.status(401).send('An error occured during the search.');
		else
			res.status(200).send(nbNutritions+'');
	});
};

var populateDB = function() {

		var defaultNutrition = [
			{
				"name": "Vitamin A",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "The prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseases；",
					1: "Have respiratory infections",
					2: "Help the immune system function normally； ",
					3: "Sick will recover soon； ",
					4: "To maintain a tissue or organ table of health； ",
					5: "Help to remove age spots； ",
					6: "Promote the development, strong bones, safeguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： Often allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin B1",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin A performance： Often allergic, symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin B2",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin B3",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin B5",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin B6",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin C",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin D",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin E",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin H",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin K",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
			{
				"name": "Vitamin P",
				"type": "nutrient",
				"completeName": "Vitamin A(retinol)",
				"description": " Vitamin A is A fat-soluble substances, it needs the digestion and absorption of the participation of minerals and fat, can be stored in the body, don't need daily supplies.Vitamin A has two kinds: one kind is vitamin A alcohol, is the first form of vitamin A, only exists in animal food.Another kind is carotene, in the body into vitamin A into material, are available from plant and animal food intake. ",
				"effects": {
					0: "the prevention and treatment of night blindness and vision loss, helps in the treatment of a variety of eye diseasEs；",
					1: "have respiratory iNfections",
					2: "help the immune system fuNction normally； ",
					3: "sick will recover soon； ",
					4: "to maintain a Tissue or organ table of healtH； ",
					5: "help to remove age spots； ",
					6: "promote the development, strong bones, safEguarding the health of skin, hair, teeth, gums； ",
					7: "Help of emphysema, the treatment of hyperthyroidism。",
				},
				"dailyIntake": "As for the general adult male，5000IU(International unit)(1IU=0.3mg)To prevent deficiency；Women need 4000IU。the latest recommended intake during pregnancy does not suggest increase;But if you're a nursing mom, in the first six months but for an additional 2500 iu, while after 6 months to consume an extra 2000 iu.",
				"supplementCycle": "Excess vitamin a performance： OFten allergic, Symptoms such as fever, diarrhea, and the symptoms will take 6 hours in excess.",
				"origin": "Liver, yellow and green vegetables such as carrots, white radish, yellow fruit, eggs, milk, dairy products, fish liver oil, etc.",
				"crowd": "Winnowing fat malabsorption, if have disease of digestive tract, gastric bowel resection, often leads to A lack of vitamin A.This situation often happens to children under the age of five, mainly because of inadequate dietary intake. Vitamin A for long-term wear contact lenses or have to look at A computer screen for A long time, but also important nutrients. Pregnant women and breast-feeding women vitamin A is also need.",
				"deficiency": "A severe lack of vitamin A for A long time, can lead to dry eyes of night blindness, and even can lead to blindness."
			},
		];


	db.collection('nutritions', function(err, collection) {
		collection.insert(defaultNutrition, {safe:true}, function(err, result) {});
	});

};

commonService.prepareCheckCollection('nutritions', populateDB);