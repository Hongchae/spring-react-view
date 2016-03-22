package net.hongchae;

import net.hongchae.view.ReactComponentConfigurer;
import net.hongchae.view.ReactComponentViewResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
public class SpringReactViewApplication {

    @Value("${react.component.path}")
    private String componentPath;

    @Value("${react.component.cache}")
    private boolean componentCache;

    @Value("${react.jsx.transform}")
    private boolean jsxTransform;

    @Bean
    ViewResolver viewResolver() {
        ReactComponentViewResolver viewResolver = new ReactComponentViewResolver();
        viewResolver.setOrder(1);
        viewResolver.setPrefix(componentPath);
        viewResolver.setSuffix(".js");

        return viewResolver;
    }

    @Bean
    ReactComponentConfigurer configurer() {
        ReactComponentConfigurer configurer = new ReactComponentConfigurer();
        configurer.setEngineName("nashorn");
        configurer.setSharedEngine(false);
        configurer.setRenderFunction("render");
        configurer.setComponentCache(componentCache);
        configurer.addScript("viewHelper/polyfill.js");
        if (jsxTransform) configurer.addScript("viewHelper/lib/babel.min.js");
        configurer.addScript("viewHelper/require.js");
        configurer.addScript("/META-INF/resources/webjars/react/0.14.7/react.js");
        configurer.addScript("/META-INF/resources/webjars/react/0.14.7/react-dom-server.js");
        configurer.addScript("viewHelper/render.js");

        return configurer;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringReactViewApplication.class, args);
    }
}
