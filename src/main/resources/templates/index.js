var Section = require('./section.js');

var Index = React.createClass({

render: function() {
    return (
        <html>
        <head>
            <title>Index</title>
            <meta content="text/html" />
            <meta property="og:title" content="Open Graph Title"/>
            <link rel="stylesheet" type="text/css" href="/css/common.css" />
        </head>
        <body>
        <div className="test">
            <span>Index</span>
        </div>
        <Section value={this.props.value} />
        </body>
        </html>
        );
    }
});

module.exports = Index;
