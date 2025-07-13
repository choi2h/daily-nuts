package com.dailynuts.member.entity.type;

public enum ImageType {
    PROFILE, CERTIFICATION;

    public static ImageType fromString(String text) {
        for(ImageType type : ImageType.values()) {
            if(type.name().equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
