package com.studycode.scheduletask.controller;

import com.studycode.scheduletask.config.DynamicTask;
import com.studycode.scheduletask.config.TaskConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * @Author: lx
 * @Date: 2022/11/22
 * @Description:
 */
@RequestMapping("/schedule")
@RestController
public class ScheduleController {


    @Autowired
    private DynamicTask dynamicTask;

    /**
     * 添加或更新cron
     *
     * @param taskConstant 任务不变
     * @return {@link String}
     */
    @PostMapping("/add")
    public String addOrUpdateSchedule(@RequestBody TaskConstant taskConstant) {

        // 可以从数据库中查询
        List<TaskConstant> taskConstants = dynamicTask.getTaskConstants();
        taskConstants.add(taskConstant);

        return "success";
    }


    /**
     * 停止
     *
     * @param taskId 任务id
     * @return {@link String}
     */
    @GetMapping("/stop")
    public String stop(@RequestParam("taskId") String taskId) {

        // 这里模拟从数据库中删除
        List<TaskConstant> taskConstants = dynamicTask.getTaskConstants();
        Iterator<TaskConstant> taskConstantIterator = taskConstants.iterator();
        while (taskConstantIterator.hasNext()) {
            TaskConstant taskConstant = taskConstantIterator.next();
            if (taskConstant.getTaskId().equals(taskId)) {
                taskConstantIterator.remove();
            }
        }

        //dynamicTask.cancel(taskId);
        return "success";
    }

}
