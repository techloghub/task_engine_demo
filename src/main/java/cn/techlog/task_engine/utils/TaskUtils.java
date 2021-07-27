package cn.techlog.task_engine.utils;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;

import java.util.HashMap;
import java.util.Map;

public class TaskUtils {
    public static Map<String, Object> getParameters(JSONObject inputs, Map<String, Object> params) {
        Map<String, String> request = (Map<String, String>) params.get("request");
        Map<String, JSONObject> taskResults = (Map<String, JSONObject>) params.get("taskResults");

        Map<String, Object> parameters = new HashMap<>();
        for (Map.Entry<String, Object> inputEntry: inputs.entrySet()) {
            String value = (String) inputEntry.getValue();
            Object parameter = null;
            if (inputEntry.getKey() == null || "".equals(inputEntry.getKey())) {
                continue;
            }
            if (value == null || value.length() == 0) {
                parameter = "";
            } else if (value.startsWith("#")) {
                parameter = request.get(value.substring(1));
            } else if (value.startsWith("$")) {
                String[] infos = value.split("\\.");
                String taskAlias = infos[0].substring(1);
                JSONObject paramObj = taskResults.get(taskAlias);
                String jsonPath = "$" + value.substring(taskAlias.length() + 1);
                parameter = JsonPath.read(paramObj.toJSONString(), jsonPath);
            }
            parameters.put(inputEntry.getKey(), parameter);
        }
        return parameters;
    }
}
