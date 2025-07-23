package com.dailynuts.member.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String FILE_NAME_FORMAT = "%s_%s_%s";
    private static final String FILE_FULL_PATH_FORMAT = "%s%s";

    @Value("${image.path}")
    private String imagePath;

    @Override
    public void createFiles(String loginId, List<MultipartFile> files) {
        log.info("UploadFiles: {}", files.size());
        for(MultipartFile file : files) {
            createFile(loginId, file);
        }
    }

    @Override
    public String[] createFile(String loginId, MultipartFile file) {
        log.info("UploadFile: {}", file.getOriginalFilename());
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String fileName = String.format(FILE_NAME_FORMAT, loginId, date, file.getOriginalFilename());
        String fullPath = String.format(FILE_FULL_PATH_FORMAT, imagePath, fileName);
        log.debug("Full path: {}", fullPath);

        try {
            file.transferTo(new File(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(CustomErrorCode.FILE_SAVE_FAIL);
        }

        return new String[]{fullPath, fileName};
    }
}
