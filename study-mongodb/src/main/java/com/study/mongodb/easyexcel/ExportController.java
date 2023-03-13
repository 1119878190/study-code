package com.study.mongodb.easyexcel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: lx
 * @Date: 2023/03/08
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class ExportController {

    @Resource
    private ExportService exportService;

    @GetMapping("/export")
    public void testExport(){
        exportService.test();
    }


}
