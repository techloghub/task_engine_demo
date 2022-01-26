package cn.techlog.task_engine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinOnParameter {
    private String task;
    private Long timeout;
    private Boolean ignoreException;
}
