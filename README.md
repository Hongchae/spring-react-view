# spring-react-view
Server-side Spring Boot & [React](https://facebook.github.io/react/) template rendering example inspired by [jreact](https://github.com/KnisterPeter/jreact).

Extends ScriptTemplateView to render React component based on script engine(Nashorn).


#### build
```
gradle build
```

#### start development server
```
gradle bootRun
```

#### properties
```
#development
react.component.path=/templates/
react.component.cache=false
react.jsx.transform=true

#production
react.component.path=/transformed/
react.component.cache=true
react.jsx.transform=false
```
