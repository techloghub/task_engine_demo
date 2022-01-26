package cn.techlog.task_engine.main;

import cn.techlog.task_engine.resolver.GroovyTaskResolver;
import cn.techlog.task_engine.resolver.HttpTaskResolver;
import cn.techlog.task_engine.resolver.TaskResolver;
import cn.techlog.task_engine.utils.FileUtil;
import cn.techlog.task_engine.utils.TaskUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TaskEngineDemo {
    private final static Map<String, TaskResolver> taskResolverMap;

    static {
        taskResolverMap = new HashMap<>();
        taskResolverMap.put("http", new HttpTaskResolver());
        taskResolverMap.put("groovy", new GroovyTaskResolver());
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> request = new HashMap<>();

        Map<String, Object> params = new HashMap<>();
        params.put("request", request);
        params.put("taskResults", new HashMap<>());

        String dslString = FileUtil.getFileString("dsl/demo.dsl.json");
        JSONObject jsonObject = JSON.parseObject(dslString);
        JSONArray tasks = jsonObject.getJSONArray("tasks");
        for (int i = 0; i < tasks.size(); ++i) {
            JSONObject task = tasks.getJSONObject(i);
            TaskResolver resolver = taskResolverMap.get(task.getString("taskType"));
            JSONObject taskResult = resolver.resolve(task, params);
            Map<String, JSONObject> taskResults = (Map<String, JSONObject>) params.get("taskResults");
            taskResults.put(task.getString("alias"), taskResult);
        }

        Map<String, Object> outputs = TaskUtils.getParameters(jsonObject.getJSONObject("outputs"), params);
        System.out.println(outputs);
    }
}
