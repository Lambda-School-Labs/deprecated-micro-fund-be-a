package com.lambdaschool.oktafoundation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/s3")
public class AWSS3Controller
{
    @Autowired
    private com.lambdaschool.oktafoundation.services.AWSS3Service AWSS3Service;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") final
                                        MultipartFile multipartFile){
        AWSS3Service.uploadFile(multipartFile);
        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
