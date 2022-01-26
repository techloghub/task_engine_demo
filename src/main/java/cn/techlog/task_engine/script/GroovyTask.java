package cn.techlog.task_engine.script;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface GroovyTask {
    JSONObject execute(Map<String, Object> args) throws Exception;
}
