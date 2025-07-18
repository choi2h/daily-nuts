package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${image.path}")
    private String imagePath;

    @Override
    public void createFiles(List<MultipartFile> files) {
        log.info("UploadFiles: {}", files.size());
        for(MultipartFile file : files) {
            createFile(file);
        }
    }

    @Override
    public String createFile(MultipartFile file) {
        log.info("UploadFile: {}", file.getOriginalFilename());
        String fullPath = imagePath + file.getOriginalFilename();
        log.debug("Full path: {}", fullPath);

        try {
            file.transferTo(new File(fullPath));
        } catch (IOException e) {
            throw new CustomException(CustomErrorCode.FILE_SAVE_FAIL);
        }

        return fullPath;
    }
}
