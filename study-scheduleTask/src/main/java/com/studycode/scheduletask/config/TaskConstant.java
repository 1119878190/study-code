package com.studycode.scheduletask.config;

/**
 * 定时任务配置，后续可根据情况添加字段，从数据库中查出来
 *
 * @author dell
 * @date 2022/11/22
 */
public class TaskConstant {
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 任务id
     */
    private String taskId;

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}