package com.studycode.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.studycode.entity.DemoData;

/**
 * @Author: lx
 * @Date: 2022/08/29
 * @Description:
 */
public class MainTest {

    public static void main(String[] args) {

        String filePath = "D:\\developCode\\other\\study-code\\file_read\\src\\main\\resources\\demo.xlsx";


        // 简单读
        //simpleRead(filePath);

        // 不创建对象读
        //noEntityRead(filePath);

        // 读多个或者全部sheet,读取指定sheet
        repeatedRead(filePath);

    }


    /**
     * 简单读
     *
     * @param filePath
     */
    public static void simpleRead(String filePath) {
        EasyExcel.read(filePath, DemoData.class, new DemoDataListener()).sheet().doRead();
    }

    /**
     * 没有实体读
     *
     * @param filePath 文件路径
     */
    public static void noEntityRead(String filePath) {
        EasyExcel.read(filePath, new NoModelDataListener()).sheet().doRead();
    }

    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     *
     * @param filePath
     */
    public static void repeatedRead(String filePath) {
        // 写法1
        try (ExcelReader excelReader = EasyExcel.read(filePath).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            //ReadSheet readSheet1 =
            //        EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            //excelReader.read(readSheet1, readSheet2);
            excelReader.read(readSheet2);
        }
    }


}
