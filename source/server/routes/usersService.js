var mongo = require('mongodb');
var commonService = require('./commonService');
var BSON = mongo.BSONPure;
var db = commonService.db;
var ObjectId = require('mongodb').ObjectID;
var TYPE = 'mongodb';
var model = require('../model/' + TYPE);

exports.logintest = function(req, res) {
//	console.log("username:"+req.body.username+" password:"+req.body.password);
	console.log("什么鬼啊");
	myuser = {
			'username': "haha",
			'email': "haha",
			'password': "haha",
		};
    res.send(JSON.stringify(myuser));
};

exports.login = function(req, res, next) {

    // Already logged in
    if (req.session.authorized)
        return res.status(200).send('Already logged');
    if (!req.body.username || !req.body.password)
        return res.send('Missing parameters');

    model.user.fetchByUsername(req.body.username, function(err, user) {

        if (err || !user)
            return res.status(401).send('User does not exits');

        model.user.checkPassword(user, req.body.password, function(err, valid) {

            if (err)
                return res.status(500).send('An error occured.');
            else if (!valid)
                return res.status(401).send('Login failure.');

            if (err)
                return res.status(500).send('An error occured.');
            delete user.password;
            req.session.user = user;
            req.session.authorized = true;
            res.send(JSON.stringify(user));
        });
    });
};

exports.register = function(req, res, next) {

    if (!req.body.username || !req.body.email || !req.body.password)
        return res.send('Missing parameters');

    var username = req.body.username;
    var email = req.body.email;
    var password = req.body.password;

    model.user.fetchByUsername(username, function(err, user) {

        if (err || user)
            return res.status(401).send('User already exists');

        model.user.fetchByEmail(email, function(err, user) {

            if (err || user)
                return res.status(401).send('Email already exists');

            model.user.add(username, email, password, function(err, user) {

                if (err)
                    return res.status(500).send('An error occured.');

                delete user.password;
                req.session.user = user;
                req.session.authorized = true;
                res.send(JSON.stringify(user));
            });
            
        });

    });
};

exports.stilllogged = function(req, res, next) {
    
    if (req.session.authorized)
        res.send(JSON.stringify(req.session.user));
    else
        res.send('no');
};

exports.logout = function(req, res, next) {
    
    req.session.authorized = null;
    req.session.user = null;
    res.send('OK');
};

exports.findUserById = function(req, res) {
    var id = req.params.id;
    console.log('Get a user: ' + id);
    db.collection('users', function(err, collection) {
        collection.findOne({'_id':new BSON.ObjectID(id)}, function(err, item) {
            res.send(item);
        });
    });
};

exports.findAllUsers = function(req, res) {
    
    db.collection('users', function(err, collection) {
        collection.find().toArray(function(err, items) {
            res.send(items);
        });
    });
};

exports.addUser = function(req, res) {
    var user = req.body;
    console.log('Add user: ' + JSON.stringify(user));
    db.collection('users', function(err, collection) {
        collection.insert(user, {safe:true}, function(err, result) {
            if (err) {
                res.send({'error':'An error has occurred'});
            } else {
                console.log('Success: ' + JSON.stringify(result[0]));
                res.send(result[0]);
            }
        });
    });
}

exports.deleteUser = function(req, res) {
    var id = req.params.id;
    console.log('Deleting user: ' + id);
    // db.collection('users', function(err, collection) {
    //     collection.remove({'_id':new BSON.ObjectID(id)}, {safe:true}, function(err, result) {
    //         if (err) {
    //             res.send({'error':'An error has occurred - ' + err});
    //         } else {
    //             console.log('' + result + ' document(s) deleted');
    //             res.send(req.body);
    //         }
    //     });
    // });
}

var populateDB = function() {

    //User collection (right 1: consumers 2: gastronomist 3: supplier)
    var users = [
        {
            _id: new mongo.ObjectID("4e54ed9f48dc5922c0094a30"),
            username: "Foo",
            password: "bar",
            right: 1,
            recipes: [
                ObjectId("4e54ed9f48dc5922c0094a40"),
            ]
        },
        {
            _id: new mongo.ObjectID("4e54ed9f48dc5922c0094a31"),
            username: "Titi",
            password: "titipwd",
            right: 2,
            recipes: [
            ]
        },
        {
            _id: new mongo.ObjectID("4e54ed9f48dc5922c0094a32"),
            username: "toto",
            password: "totopwd",
            right: 3,
            recipes: [
            ]
        }
    ];
    db.collection('users', function(err, collection) {
        collection.insert(users, {safe:true}, function(err, result) {});
    });

};

commonService.prepareCheckCollection('users', populateDB);