package groovy

import cn.techlog.task_engine.script.GroovyTask
import com.alibaba.fastjson.JSONObject
import groovy.transform.CompileStatic

@CompileStatic
class DemoGroovyTask implements GroovyTask {
    @Override
    JSONObject execute(Map<String, Object> args) {
        Map<String, String> params = args.get("parameters") as Map<String, String>
        String stringValue = params.get("string_value")
        String[] sortedValues = stringValue.split("").sort()
        JSONObject jsonObject = new JSONObject()
        jsonObject.put("queryid", sortedValues.join(""))
        return jsonObject
    }
}
