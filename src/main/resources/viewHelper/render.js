function render(requirePath, component, model) {
    require.paths.push(requirePath);

    var Component = require('./' + component);
    var props = {};
    for (var k in model) {
        if(model[k] instanceof Java.type('java.lang.Iterable')) {
            props[k] = Java.from(model[k]);
        } else {
            props[k] = model[k];
        }
    }

    return DOCTYPE + ReactDOMServer.renderToStaticMarkup(React.createElement(Component, props));
}

var DOCTYPE = "<!DOCTYPE html>";