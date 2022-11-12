package com.studycode.studyslice.upload.strategy;


import com.studycode.studyslice.upload.dto.FileUploadDTO;
import com.studycode.studyslice.upload.dto.FileUploadRequestDTO;

public interface SliceUploadStrategy {

  FileUploadDTO sliceUpload(FileUploadRequestDTO param);
}
