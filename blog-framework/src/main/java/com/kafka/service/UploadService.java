package com.kafka.service;

import com.kafka.domain.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult<?> uploadImg(MultipartFile img);
}
