package com.study.mongodb.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Case")
public class Case {

    @Id
    private String id;
    private String name;
    private String creator;
    private String creatorName;
    private Date createTime;
    private String description;


    private Date closeTime;
    private String category;
    private String categoryName;


    private boolean isClue;

}