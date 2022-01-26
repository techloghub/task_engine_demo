package cn.techlog.task_engine.resolver;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface TaskResolver {
    JSONObject resolve(JSONObject task, Map<String, Object> params) throws Exception;
}
