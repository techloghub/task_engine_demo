package cn.techlog.task_engine.task_resolver;

import cn.techlog.task_engine.groovy_task.GroovyTask;
import cn.techlog.task_engine.utils.FileUtil;
import cn.techlog.task_engine.utils.TaskUtils;
import com.alibaba.fastjson.JSONObject;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;

public class GroovyTaskResolver implements TaskResolver {
    private final static CompilerConfiguration DEFAULT_CONFIG = new CompilerConfiguration(CompilerConfiguration.DEFAULT);

    @Override
    public JSONObject resolve(JSONObject task, Map<String, Object> params) {
        Map<String, String> request = (Map<String, String>) params.get("request");
        Map<String, Object> parameters = TaskUtils.getParameters(task.getJSONObject("inputs"), params);

        Map<String, Object> groovyArgs = new HashMap<>();
        groovyArgs.put("request", request);
        groovyArgs.put("parameters", parameters);
        GroovyTask groovyTask = getGroovyTask(task.getString("scriptId"));
        if (groovyTask == null) {
            return null;
        }
        return groovyTask.groovyTask(groovyArgs);
    }

    public GroovyTask getGroovyTask(String scriptId) {
        try {
            // TODO: get groovy script from db
            String script = FileUtil.getFileString("groovy/DemoGroovyTask.groovy");
            GroovyClassLoader loader = AccessController.doPrivileged((PrivilegedAction<GroovyClassLoader>) () ->
                    new GroovyClassLoader(GroovyTaskResolver.class.getClassLoader(), DEFAULT_CONFIG));
            GroovyCodeSource codeSource = new GroovyCodeSource(script, "GroovyTask", "/groovy");
            Class<GroovyTask> clazz = loader.parseClass(codeSource, false);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
