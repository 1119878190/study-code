package com.studycode.studyslice.upload.concurrent;


import com.studycode.studyslice.upload.dto.FileUploadDTO;
import com.studycode.studyslice.upload.dto.FileUploadRequestDTO;
import com.studycode.studyslice.upload.strategy.context.UploadContext;
import com.studycode.studyslice.upload.strategy.enu.UploadModeEnum;

import java.util.concurrent.Callable;

public class FileCallable implements Callable<FileUploadDTO> {

  private UploadModeEnum mode;

  private FileUploadRequestDTO param;

  public FileCallable(UploadModeEnum mode,
      FileUploadRequestDTO param) {

    this.mode = mode;
    this.param = param;
  }

  @Override
  public FileUploadDTO call() throws Exception {

    FileUploadDTO fileUploadDTO = UploadContext.INSTANCE.getInstance(mode).sliceUpload(param);
    return fileUploadDTO;
  }
}
