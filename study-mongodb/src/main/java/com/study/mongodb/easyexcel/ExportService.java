package com.study.mongodb.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.study.mongodb.vo.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lx
 * @Date: 2023/03/08
 * @Description:
 */
@Service
public class ExportService {


    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String  rootpath = "D:\\tmp\\";

    @SneakyThrows
    public void test() {

        User account = mongoTemplate.findOne(Query.query(Criteria.where("account").is("223")), User.class);

        List list = new ArrayList();
        UserOnlineDuration userOnlineDuration = new UserOnlineDuration();
        userOnlineDuration.setAccount(account.getAccount());
        userOnlineDuration.setOnlineDuration(200L);


        //ByteArrayInputStream stream = null;
        //try {
        //    BASE64Decoder decoder = new BASE64Decoder();
        //    byte[] bytes1 = decoder.decodeBuffer(account.getHeader());
        //    stream = new ByteArrayInputStream(bytes1);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        list.add(userOnlineDuration);

        String header = account.getHeader();
        String replace = header.replace("data:image/jpeg;base64,", "");
        replace = replace.replace("data:image/png;base64,", "");

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes1 = decoder.decodeBuffer(replace);
        userOnlineDuration.setImg(bytes1);


        // 写法1
        String fileName = rootpath +  "test1.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, UserOnlineDuration.class).sheet("模板").doWrite(list);
        ExcelWriter excelWriter = EasyExcel.write(fileName).excelType(ExcelTypeEnum.XLSX).build();

        WriteSheet sheet1 = EasyExcel.writerSheet("sheet1").head(UserOnlineDuration.class).build();
        excelWriter.write(list,sheet1);
        excelWriter.finish();



    }


}
