package net.hongchae.view;

import org.springframework.web.servlet.view.script.ScriptTemplateConfigurer;

import java.util.Arrays;

public class ReactComponentConfigurer extends ScriptTemplateConfigurer {
    private boolean componentCache = false;

    public boolean isComponentCache() {
        return componentCache;
    }

    public void setComponentCache(boolean componentCache) {
        this.componentCache = componentCache;
    }

    public void addScript(String script) {
        if(this.getScripts() == null) {
            this.setScripts(script);
        } else {
            final int index = this.getScripts().length;
            String[] scripts = Arrays.copyOf(this.getScripts(), index + 1);
            scripts[index] = script;
            this.setScripts(scripts);
        }
    }
}
