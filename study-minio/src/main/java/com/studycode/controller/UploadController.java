package com.studycode.controller;

import com.studycode.config.MinioEntity;
import com.studycode.util.MinioUtil;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
public class UploadController {

    @Resource
    private MinioEntity minioEntity;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file) {

        try {

            // 新建一个 Bucket 就是在文件路径新建一个目录，默认上传的文件都在该 Bucket 的根目录，如果要存储多级目录，可以指定在文件名中指定目录结构，如：img/avatar.jpg
            MinioUtil.uploadFile(minioEntity.getBucketName(), file, "dir/"+ file.getOriginalFilename(), file.getContentType());
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse res){
        try {

            InputStream inputStream = MinioUtil.getObject(minioEntity.getBucketName(), filename);

            byte[] buf = new byte[1024];

            int len;

            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){

                while ((len=inputStream.read(buf))!=-1){

                    os.write(buf,0,len);

                }
                os.flush();

                byte[] bytes = os.toByteArray();

                res.setCharacterEncoding("utf-8");
                res.setContentType("application/force-download");// 设置强制下载不打开
                res.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename,"UTF8"));
                try ( ServletOutputStream stream = res.getOutputStream()){
                    stream.write(bytes);
                    stream.flush();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}