package net.hongchae.view;

import net.hongchae.view.helper.FileSystemHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.scripting.support.StandardScriptEvalException;
import org.springframework.web.servlet.view.script.ScriptTemplateView;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ReactComponentView extends ScriptTemplateView {
    private static FileSystemHelper fileSystemHelper = new FileSystemHelper();
    private boolean useComponentCache = true;
    private String renderFunction;

    @Override
    protected void initApplicationContext(ApplicationContext context) {
        super.initApplicationContext(context);
        ReactComponentConfigurer viewConfig = (ReactComponentConfigurer) autodetectViewConfig();
        this.useComponentCache = viewConfig.isComponentCache();
        this.renderFunction = viewConfig.getRenderFunction();

    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ScriptEngine engine = getEngine();
            engine.put("fileSystem", fileSystemHelper);
            engine.put("useCache", useComponentCache);

            Invocable invocable = (Invocable) engine;
            String url = getUrl();

            int lastPathIndex = url.lastIndexOf('/');
            String componentName = url.substring(lastPathIndex + 1);
            String requirePath = url.substring(1, lastPathIndex);

            Object html = invocable.invokeFunction(renderFunction, requirePath, componentName, model);

            response.getWriter().write(String.valueOf(html));
        }
        catch (ScriptException ex) {
            throw new ServletException("Failed to render script template", new StandardScriptEvalException(ex));
        }
    }
}