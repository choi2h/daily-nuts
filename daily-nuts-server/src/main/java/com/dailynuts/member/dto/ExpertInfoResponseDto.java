package com.dailynuts.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExpertInfoResponseDto {
    public String description;
    public List<ImageInfo> images;

    public ExpertInfoResponseDto(String description) {
        this.description = description;
        this.images = new ArrayList<>();
    }

    public void addImage(String name, String url) {
        images.add(new ImageInfo(name, url));
    }

    @Getter
    @AllArgsConstructor
    public static class ImageInfo {
        private String name;
        private String url;
    }
}
