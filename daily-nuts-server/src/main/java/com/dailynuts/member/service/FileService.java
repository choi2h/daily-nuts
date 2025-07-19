package com.dailynuts.member.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void createFiles(String loginId, List<MultipartFile> files);
    String createFile(String loginId, MultipartFile file);
}
