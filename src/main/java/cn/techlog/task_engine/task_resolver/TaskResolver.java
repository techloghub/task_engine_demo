package cn.techlog.task_engine.task_resolver;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface TaskResolver {
    JSONObject resolve(JSONObject task, Map<String, Object> params);
}
