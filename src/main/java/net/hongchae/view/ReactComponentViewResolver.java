package net.hongchae.view;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

public class ReactComponentViewResolver extends UrlBasedViewResolver {

    public ReactComponentViewResolver() {
        setViewClass(requiredViewClass());
    }

    @Override
    protected Class<?> requiredViewClass() {
        return ReactComponentView.class;
    }

}