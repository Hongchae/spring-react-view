function isFile(path) {
    return fileSystem.isFile(path);
}
function getDirectory(path) {
    return fileSystem.getDirectory(path);
}
function getFilename(path) {
    return fileSystem.getFilename(path);
}
function readFile(path) {
    return fileSystem.readFile(path);
}

function resolve(base, id) {
    var resolvedPath;
    if (id[0] === '.' || id[0] === '/') {
        var path = base + '/' + require.stack[0] + '/' + id;
        if (isFile(path)) {
            resolvedPath = path;
        } else if (isFile(path + '.js')) {
            resolvedPath = path + '.js';
        }
    }

    if (resolvedPath !== undefined) {
        resolvedPath = resolvedPath.replace('/./', '/');
    }
    return resolvedPath;
}

transform = function(content) {
     return (Babel) ? Babel.transform(content, {presets: ['react']}).code : content;
};

require = function(id) {
    var uri;
    for (var i = 0, n = require.paths.length; typeof uri === 'undefined' && i < n; i++) {
        uri = resolve(require.paths[i], id);
        if (typeof uri !== 'undefined') {
            uri = {uri: uri, base: require.paths[i]};
        }
    }
    if (typeof uri !== 'undefined') {
        var cached = require.cache[uri.uri];

        if (cached !== undefined && useCache) {
        //if (cached !== undefined && cached.timestamp > new Date() - 3000) {
            return cached;
        }

        var content = readFile(uri.uri);
        var f = new Function('require', 'exports', 'module', '__filename', '__dirname', transform(content));
        var exports = {};
        var module = {id: id, uri: uri.uri, exports: exports};

        var stackPath = uri.uri
            .replace(new RegExp('^' + uri.base), '')
            .replace(/[^\/]+$/, '')
            .replace(/\/\//g, '/')
            .replace(/^\//, '')
            .replace(/\/$/, '');
        require.stack.unshift(stackPath);
        var __dirname = getDirectory(uri.uri);
        var __filename = getFilename(uri.uri);
        f.call({}, require, exports, module, __filename, __dirname);
        exports = module.exports || exports;
        exports.timestamp = new Date();
        require.cache[uri.uri] = exports;
        require.stack.shift();

        return exports;
    }
    throw 'Unable to resolve: ' + id;
};

require.paths = [];
require.stack = ['.'];
require.cache = {};