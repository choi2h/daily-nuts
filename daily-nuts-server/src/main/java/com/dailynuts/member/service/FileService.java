package com.dailynuts.member.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void createFiles(List<MultipartFile> files);
    String createFile(MultipartFile file);
}
