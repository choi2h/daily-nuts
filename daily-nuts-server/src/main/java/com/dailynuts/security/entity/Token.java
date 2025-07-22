package com.dailynuts.security.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "token")
public class Token {

    private Long id;

    private String jdi;

}
