package com.example.task_flow.config;

import com.example.task_flow.service.ITaskService;
import com.example.task_flow.service.ITokenDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    private final ITaskService taskService;
    private final ITokenDemandService tokenDemandService;

    @Autowired
    public SchedulingConfig(ITaskService taskService, ITokenDemandService tokenDemandService) {
        this.taskService = taskService;
        this.tokenDemandService = tokenDemandService;
    }

    //@Scheduled(fixedRate = 60 * 1000)
    public void updateOverdueTasks() {
        taskService.updateOverdueTasksStatus();
    }

    //@Scheduled(fixedRate = 60 * 1000)
    public void checkPendingTokenRequests() {
        tokenDemandService.processPendingTokenRequests();
    }
}
