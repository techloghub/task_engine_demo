package cn.techlog.task_engine.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDef {
    private String name;
    private String description;
    private List<WorkflowTask> tasks = new LinkedList<>();
    private Map<String, Object> inputs = new HashMap<>();
    private Map<String, Object> outputs = new HashMap<>();
    private Map<String, DataMeta> outputsMeta;
}
