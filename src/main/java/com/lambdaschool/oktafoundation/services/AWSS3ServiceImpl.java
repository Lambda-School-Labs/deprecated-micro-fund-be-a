package com.lambdaschool.oktafoundation.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;


@Service(value = "AWSS3Service")
public class AWSS3ServiceImpl implements AWSS3Service
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3ServiceImpl.class);

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    @Async
    public void uploadFile(final
                           MultipartFile multipartFile){
        LOGGER.info("File Upload in Progress.");
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            uploadFileToS3Bucket(bucketName, file);
            LOGGER.info("File upload is completed.");
            file.delete();
        } catch (final AmazonServiceException ex){
            LOGGER.info("File upload has Failed.");
            LOGGER.error("Error converting the multi-part file to file = ", ex.getMessage());
        }
    }
    private File convertMultiPartFileToFile(final MultipartFile multipartFile){
        final File file = new File(multipartFile.getOriginalFilename());
        try(final
            FileOutputStream outputStream = new FileOutputStream(file)){
            outputStream.write(multipartFile.getBytes());

        }catch (final IOException ex){
            LOGGER.error("Error converting the multi-part file to file = ", ex.getMessage());
        }
        return file;
    }
    private  void uploadFileToS3Bucket(final String bucketName, final  File file){
        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
        LOGGER.info("Uploading file with name = " + uniqueFileName);
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        amazonS3.putObject(putObjectRequest);
    }

}
