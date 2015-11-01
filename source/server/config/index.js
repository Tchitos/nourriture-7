var yaml	= require('js-yaml'),
	fs		= require('fs');

module.exports = {
    acl: yaml.safeLoad(fs.readFileSync('./config/acl.yml', 'utf8')),
    server: yaml.safeLoad(fs.readFileSync('./config/server.yml', 'utf8')),
};