package cn.techlog.task_engine.groovy_task;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface GroovyTask {
    JSONObject groovyTask(Map<String, Object> args);
}
