package com.studycode.scheduletask;


import com.studycode.scheduletask.config.DynamicTask;
import com.studycode.scheduletask.config.TaskConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleTaskApplication.class)
public class DynamicTaskTest {

    @Autowired
    private DynamicTask dynamicTask;


    @Test
    public void init(){
        System.out.println("--------------------");
    }


    @Test
    public void test()  {
        List<TaskConstant> taskConstans = dynamicTask.getTaskConstants();
        TaskConstant taskConstant = new TaskConstant();
        taskConstant.setCron("0/5 * * * * ?");
        taskConstant.setTaskId("test1");
        taskConstans.add(taskConstant);


        //dynamicTask.add();


        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        TaskConstant taskConstant2 = new TaskConstant();
        taskConstant2.setCron("0/1 * * * * ?");
        taskConstant2.setTaskId("test2");
        taskConstans.add(taskConstant2);


        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void test2() {
        List<TaskConstant> taskConstans = dynamicTask.getTaskConstants();
        TaskConstant taskConstant = new TaskConstant();
        taskConstant.setCron("0/2 * * * * ?");
        taskConstant.setTaskId("test3");
        taskConstans.add(taskConstant);

    }
}