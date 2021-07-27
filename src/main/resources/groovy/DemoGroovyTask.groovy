package groovy

import cn.techlog.task_engine.groovy_task.GroovyTask
import com.alibaba.fastjson.JSONObject
import groovy.transform.CompileStatic

@CompileStatic
class DemoGroovyTask implements GroovyTask {
    @Override
    JSONObject groovyTask(Map<String, Object> args) {
        Map<String, String> params = args.get("parameters") as Map<String, String>
        String stringValue = params.get("string_value");
        String[] values = stringValue.split("")
        String[] sortedValues = values.sort()
        JSONObject jsonObject = new JSONObject()
        jsonObject.put("queryid", sortedValues.join(""))
        return jsonObject
    }
}
