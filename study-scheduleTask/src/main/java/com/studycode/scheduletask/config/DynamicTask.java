package com.studycode.scheduletask.config;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.PeriodicTrigger;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Configuration
public class DynamicTask implements SchedulingConfigurer {
    private static Logger LOGGER = LoggerFactory.getLogger(DynamicTask.class);

    private static final ExecutorService es = new ThreadPoolExecutor(10, 20,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(10),
            new DynamicTaskConsumeThreadFactory());


    private volatile ScheduledTaskRegistrar registrar;

    private final ConcurrentHashMap<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CronTask> cronTasks = new ConcurrentHashMap<>();

    // 这个list就是定时任务的配置，可以从数据库查出来
    private volatile List<TaskConstant> taskConstants = Lists.newArrayList();


    public ConcurrentHashMap<String, ScheduledFuture<?>> getScheduledFutures() {
        return scheduledFutures;
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        this.registrar = registrar;
        System.out.println("=============out==================");
        this.registrar.addTriggerTask(() -> {
                    System.out.println("coming configureTasks");
                    if (!CollectionUtils.isEmpty(taskConstants)) {
                        LOGGER.info("检测动态定时任务列表...");
                        List<TimingTask> tts = new ArrayList<>();
                        taskConstants
                                .forEach(taskConstant -> {
                                    TimingTask tt = new TimingTask();
                                    tt.setExpression(taskConstant.getCron());
                                    tt.setTaskId(taskConstant.getTaskId());
                                    tts.add(tt);
                                });
                        this.refreshTasks(tts);
                    } else {
                        // 为空  取消所有的定时任务
                        ConcurrentHashMap.KeySetView<String, ScheduledFuture<?>> strings = scheduledFutures.keySet();
                        for (String string : strings) {
                            cancel(string);
                        }
                    }
                }
                // triggerContext 是用来定时调用 addTriggerTask 这个方法的
                , triggerContext -> new PeriodicTrigger(5L, TimeUnit.SECONDS).nextExecutionTime(triggerContext));


    }

    public List<TaskConstant> getTaskConstants() {
        return taskConstants;
    }

    private void refreshTasks(List<TimingTask> tasks) {
        //取消已经删除的策略任务
        Set<String> taskIds = scheduledFutures.keySet();
        for (String taskId : taskIds) {
            if (!exists(tasks, taskId)) {
                LOGGER.info("cancel schedule taskId: " + taskId);
                scheduledFutures.get(taskId).cancel(true);
            }
        }
        for (TimingTask tt : tasks) {
            String expression = tt.getExpression();
            if (StringUtils.isBlank(expression) || !CronSequenceGenerator.isValidExpression(expression)) {
                LOGGER.error("定时任务DynamicTask cron表达式不合法: " + expression);
                continue;
            }
            //如果配置一致，则不需要重新创建定时任务
            if (scheduledFutures.containsKey(tt.getTaskId())
                    && cronTasks.get(tt.getTaskId()).getExpression().equals(expression)) {
                continue;
            }
            //如果策略执行时间发生了变化，则取消当前策略的任务
            if (scheduledFutures.containsKey(tt.getTaskId())) {
                scheduledFutures.remove(tt.getTaskId()).cancel(false);
                cronTasks.remove(tt.getTaskId());
            }
            CronTask task = new CronTask(tt, expression);
            ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
            cronTasks.put(tt.getTaskId(), task);
            scheduledFutures.put(tt.getTaskId(), future);
        }
    }


    /**
     * 取消
     *
     * @param taskId 任务id
     */
    public void cancel(String taskId) {
        if (scheduledFutures.containsKey(taskId)) {
            LOGGER.info("cancel schedule taskId: " + taskId);
            scheduledFutures.get(taskId).cancel(true);
            scheduledFutures.remove(taskId);
        }
    }

    private boolean exists(List<TimingTask> tasks, String taskId) {
        for (TimingTask task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }

    @PreDestroy
    public void destroy() {
        this.registrar.destroy();
    }


    /**
     * 具体执行的业务逻辑线程，根据实际情况修改run
     *
     * @author dell
     * @date 2022/11/22
     */
    private class TimingTask implements Runnable {
        private String expression;

        private String taskId;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            //设置队列大小10
            LOGGER.info("当前CronTask: " + this);

            es.submit(() -> {
                // do something
                System.out.println("只执行了任务taskId: " + taskId + " corn: " + expression);
            });

        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this
                    , ToStringStyle.JSON_STYLE
                    , false
                    , false
                    , TimingTask.class);
        }

    }


}