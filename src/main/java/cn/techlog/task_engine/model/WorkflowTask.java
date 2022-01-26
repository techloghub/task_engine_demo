package cn.techlog.task_engine.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowTask {
    private String name;
    private String description;
    private String method;
    private String className;
    private String serviceName;
    private Map<String, Object> inputs;
    private Map<String, DataMeta> inputsMeta;
    private Map<String, Object> transfer;
    private String taskType;
    private Integer retryCount;
    private Integer timeout;
    private String url;
    private String referenceId;
    private Boolean ignoreException;
    private JSONObject defaultResult;
    private String when;

    private List<List<WorkflowTask>> forkTasks = new LinkedList<>();

    private List<JoinOnParameter> joinOn = new LinkedList<>();
}
