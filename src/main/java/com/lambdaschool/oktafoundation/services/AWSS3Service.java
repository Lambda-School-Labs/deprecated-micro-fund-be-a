package com.lambdaschool.oktafoundation.services;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service
{
    void uploadFile(MultipartFile multipartFile);
}
