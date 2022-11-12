package com.studycode.studyslice.upload.util;


import com.studycode.studyslice.common.util.SystemUtil;
import com.studycode.studyslice.upload.constant.FileConstant;
import com.studycode.studyslice.upload.dto.FileUploadRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FilePathUtil implements ApplicationRunner {

  @Value("${upload.root.dir}")
  private String uploadRootDir;

  @Value("${upload.window.root}")
  private String uploadWindowRoot;





  @Override
  public void run(ApplicationArguments args) throws Exception {
            createUploadRootDir();
  }


  private void createUploadRootDir(){
    String path = getBasePath();
    File file = new File(path);
    if(!file.mkdirs()){
      file.mkdirs();
    }
  }



  public String getPath(){
    return uploadRootDir;
  }

  public String getBasePath(){
    String path = uploadRootDir;
    if(SystemUtil.isWinOs()){
      path = uploadWindowRoot + uploadRootDir;
    }

    return path;
  }


  public String getPath(FileUploadRequestDTO param){
    String path = this.getBasePath() + FileConstant.FILE_SEPARATORCHAR + param.getPath() + FileConstant.FILE_SEPARATORCHAR + param.getMd5();
    return path;
  }



}
