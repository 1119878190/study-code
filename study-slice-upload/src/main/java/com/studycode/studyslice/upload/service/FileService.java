package com.studycode.studyslice.upload.service;


import com.studycode.studyslice.upload.dto.FileUploadDTO;
import com.studycode.studyslice.upload.dto.FileUploadRequestDTO;

import java.io.IOException;

public interface FileService {

  FileUploadDTO upload(FileUploadRequestDTO fileUploadRequestDTO)throws IOException;

  FileUploadDTO sliceUpload(FileUploadRequestDTO fileUploadRequestDTO);

  FileUploadDTO checkFileMd5(FileUploadRequestDTO fileUploadRequestDTO)throws IOException;

}
